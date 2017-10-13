package lobby.server.rmi;

import lobby.Console;
import lobby.client.rmi.RemoteRmiClient;
import lobby.server.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainRmiServer implements RemoteMainRmiServer {

    public static final String REMOTE_SERVER_NAME = "RMI_SERVER";

    private transient Registry registry;

    public MainRmiServer() throws RemoteException {
        this.registry = null;
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Starts server and waits for clients to connect.
     */
    public void start(){
        Server.write(Server.RMI_SERVER_NAME, "Binding main server to registry...");
        try {
            registry = LocateRegistry.createRegistry(Server.RMI_PORT);
            registry.bind(REMOTE_SERVER_NAME, this);
            Server.write(Server.RMI_SERVER_NAME, "Binding successful!");
        } catch (RemoteException | AlreadyBoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"[SERVER RMI]: Failure during registry binding", e);
        }
    }

    /**
     * creates a new PersonalServer (which implements ServerInterface) to interact specifically with one client
     * if the player has to reconnect, it starts DisconnectedHandler's reconnectPlayer, otherwise it puts the player in a new game waiting room
     * @param remoteRmiClient client's stub
     * @return RemoteServer stub
     */
    @Override
    public RemoteRmiServer connect(RemoteRmiClient remoteRmiClient) {
        Server.write(Server.RMI_SERVER_NAME, "Connection incoming.");
        try {
            RmiServer rmiServer = new RmiServer(remoteRmiClient);
            return rmiServer;
        } catch (RemoteException e) {
            Server.write(Server.RMI_SERVER_NAME, "Connection failed.");
        }
        return null;
    }
}
