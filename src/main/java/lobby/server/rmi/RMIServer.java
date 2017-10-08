package lobby.server.rmi;

import lobby.Console;
import lobby.messages.client.LoginMessage;
import lobby.server.GenericServer;
import lobby.server.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer extends GenericServer implements Remote {

    public static final String REMOTE_SERVER_NAME = "RMI_SERVER";

    private transient Registry registry;

    public RMIServer() throws RemoteException {
        this.registry = null;
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Starts server and waits for clients to connect.
     */
    public void start (){
        Console.write("[SERVER RMI]: Binding server implementation to registry...");
        try {
            registry = LocateRegistry.createRegistry(Server.RMI_PORT);
            registry.bind(REMOTE_SERVER_NAME, this);
            Console.write("[SERVER RMI]: Binding general server successful!");
        } catch (RemoteException | AlreadyBoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"[SERVER RMI]: Failure during registry binding", e);
        }
        Console.write("[SERVER RMI]: Waiting for clients to connect...");
    }

    /**
     * creates a new PersonalServer (which implements ServerInterface) to interact specifically with one client
     * if the player has to reconnect, it starts DisconnectedHandler's reconnectPlayer, otherwise it puts the player in a new game waiting room
     * @param username player's username
     * @param stub client's stub
     * @return RemoteServer stub
     */
    public RemoteServer connect (String username, RemoteClient stub, boolean advancedRules) {
        print("connecting...", username);
        try {
            PersonalServer personalServer = new PersonalServer(username, stub);
            // handle reconnection
            if (DisconnectedHandler.getInstance().hasToReconnect(username)){
                // has to reconnect
                personalServer.setGameOn(true);
                personalServer.setDisconnected(false);
                stub.forceAdvancedRules(advancedRules);
                DisconnectedHandler.getInstance().reconnectPlayer(username, personalServer);
            }
            else{
                personalServer.setAdvancedRules(advancedRules);
            }
            print("connected", username);
            return personalServer;
        } catch (RemoteException e){
            print("connection failed", username);
        }
        return null;
    }


    /**
     * login onto the server
     * @param loginMessage message with username and password
     * @return message LoginConfirmMessage, indicates login result
     */
    public boolean login(LoginMessage loginMessage) {
        print("logging in", loginMessage.getUserName());
        boolean rightUsername = Server.getServerInstance().existPlayer( loginMessage.getUsername() );
        boolean rightPassword = Server.getServerInstance().confirmPassword( loginMessage.getUsername(), loginMessage.getPassword() );

        return new LoginConfirmMessage(rightUsername,rightPassword);
    }

    /**
     * register new user if not already registered
     * @param loginMessage message with username and password
     * @return true if registration was done correctly, false if user is already registered
     */
    public boolean register(LoginMessage loginMessage) {
        print("registering", loginMessage.getUserName());
        if( ! Server.getServerInstance().existPlayer(loginMessage.getUsername())){
            Server.getServerInstance().addUser( loginMessage.getUsername(),loginMessage.getPassword() );
            return true;
        }
        return false;
    }

    private void print (String s, String username){
        Console.write("[CLIENT RMI "+ username +"]: "+ s);
    }
}
