package lobby.server;

import lobby.Console;
import lobby.controller.LobbyServerController;
import lobby.model.LobbyModel;
import lobby.server.rmi.MainRmiServer;
import lobby.server.socket.MainSocketServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private List<GenericServer> usersGenericServers;
    private Map<Integer, String> userNames;

    private static Server serverInstance;
    private boolean serverActive;

    private LobbyModel lobbyModel;
    private LobbyServerController lobbyServerController;

    /**
     * Set of executors used to provide new threads for new games.
     */
    private ExecutorService executor;

    private Server() {
        this.usersGenericServers = new ArrayList<>();
        this.userNames = new HashMap<>();
        this.serverActive = true;
        this.lobbyModel = new LobbyModel();
        this.lobbyServerController = new LobbyServerController();

        lobbyModel.registerObserver(lobbyServerController);
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
                    lobbyModel.printUsers();
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

    public Integer getIdbyUserName(String userName) {
        for (Integer ID : getUserNames().keySet())
            if (userName.equals(userNames.get(ID)))
                return ID;
        return null;

    }

    public List<GenericServer> getUsersGenericServers() {
        return usersGenericServers;
    }

    public Map<Integer, String> getUserNames() {
        return userNames;
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

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }

    public LobbyServerController getLobbyServerController() {
        return lobbyServerController;
    }
}