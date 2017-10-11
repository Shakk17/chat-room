package lobby.client.rmi;

import lobby.Console;
import lobby.client.Client;
import lobby.client.GenericClient;
import lobby.server.Server;
import lobby.server.rmi.MainRmiServer;
import lobby.server.rmi.RemoteMainRmiServer;
import lobby.server.rmi.RemoteRmiServer;
import lobby.server.rmi.RmiServer;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RmiClient extends GenericClient implements RemoteRmiClient {

    private static final String IP = Server.IP_ADDRESS;
    private static final int PORT = Server.RMI_PORT;

    /**
     * Value used to ping this client, always changed after a ping calls to avoid RMI caching problems
     */
    private int pingValue = 0;

    private RemoteMainRmiServer remoteMainRmiServer;

    /**
     * Server's class created to communicate with only a specific client
     */
    private transient RemoteRmiServer remoteRmiServer;

    private transient Registry registry;

    /**
     * It connect a client to RMI server, saves remote references
     * @param client contains specific of the client.
     * @throws RemoteException if an RMI connectivity problems occurs
     */
    public RmiClient(Client client) {
        super(client);

        try {
            Console.write("Loading RMI registry...");
            registry = LocateRegistry.getRegistry(IP, PORT);
            remoteMainRmiServer = (RemoteMainRmiServer) registry.lookup(MainRmiServer.REMOTE_SERVER_NAME);
            UnicastRemoteObject.exportObject(this, 0);
            remoteRmiServer = remoteMainRmiServer.connect("franco", this);
            Console.write("Server remote object loaded!");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry binding",e);
        } catch (NotBoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry lookup",e);
        }
    }

    @Override
    public void login(String userName) {

    }

    @Override
    public void logout() {

    }
}
