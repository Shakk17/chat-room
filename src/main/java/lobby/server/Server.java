package lobby.server;

import lobby.Console;
import lobby.server.rmi.MainRmiServer;
import lobby.server.socket.MainSocketServer;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static final int SOCKET_PORT = 8080;
    public static final int RMI_PORT = 8000;
    public static final String IP_ADDRESS = "localhost";
    public static final String SOCKET_SERVER_NAME = "SOCKET SERVER";
    public static final String RMI_SERVER_NAME = "RMI SERVER";

    private Map<String, GenericServer> users;

    private static Server serverInstance;
    private boolean serverActive;

    /**
     * Set of executors used to provide new threads for new games.
     */
    private ExecutorService executor;

    private Server() {
        this.users = new ConcurrentHashMap<>();
        this.serverActive = true;
    }

    public static Server getServerInstance() {
        if (serverInstance == null)
            serverInstance = new Server();
        return serverInstance;
    }

    /**
     * Launch both RMI and Socket servers in different threads,
     * close them if "close" is typed into terminal
     */
    public void launchServers() {
        write(SOCKET_SERVER_NAME, "Launching...");
        MainSocketServer mainSocketServer = new MainSocketServer();
        mainSocketServer.start();
        write(SOCKET_SERVER_NAME, "Operative!");

        write(RMI_SERVER_NAME, "Launching...");
        MainRmiServer mainRmiServer;
        try {
            mainRmiServer = new MainRmiServer();
            mainRmiServer.start();
        } catch (RemoteException e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,"Failed to initialize RMI server!", e);
        }
        write(RMI_SERVER_NAME, "Operative!");

        executor = Executors.newCachedThreadPool();

        String command;
        while (serverActive) {
            command = Console.readString();
            switch (command) {
                case "users":
                    printUsers();
                    break;
                case "close":
                    serverActive = false;
                    break;
                default:
                    Console.writeRed("Wrong input.");
                    break;
            }
        }

        Console.write("Server terminated.");
        executor.shutdownNow();
        mainSocketServer.terminate();
        mainSocketServer.interrupt();
    }

    public boolean checkUserName(String userName) {
        for (String string : users.keySet())
            if (string.equals(userName))
                return false;
        return true;
    }

    /**
     * Tries to add a user to the map of connected users with correspondent interfaces.
     * @return true if successful, false if username is already taken.
     */
    public boolean addUser(String userName, GenericServer genericServer) {
        for (String string : users.keySet())
            if (string.equals(userName))
                return false;
        users.put(userName, genericServer);
        Console.writeGreen("The user " + userName + " just entered the lobby!");
        return true;
    }

    /**
     * Tries to remove a user from users already logged.
     * @param userName name of the entry to be removed.
     * @return true is successful, false otherwise.
     */
    public boolean removeUser(String userName) {
        boolean userRemoved = null != users.remove(userName);
        if (userRemoved)
            Console.writeGreen("The user " + userName + " just exited the lobby!");
        return userRemoved;
    }

    private void printUsers() {
        if (users.isEmpty()) {
            Console.writeBlue("The lobby is empty!");
            return;
        }
        Console.writeBlue(users.size() + " users logged in: ");
        StringBuilder userNames = new StringBuilder();
        for (String userName : users.keySet())
            userNames.append(userName).append(", ");
        userNames.delete(userNames.length() - 2, userNames.length());
        Console.write(userNames.toString());
    }

    public Map<String, GenericServer> getUsers() {
        return users;
    }

    public boolean isServerActive() {
        return serverActive;
    }

    public static void write(String name, String action) {
        if (name.equals(RMI_SERVER_NAME))
            Console.writeBlue("["+ name +"]: "+ action);
        else
            Console.writeGreen("["+ name +"]: "+ action);
    }
}