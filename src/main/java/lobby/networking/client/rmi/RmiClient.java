package lobby.networking.client.rmi;

import lobby.Console;
import lobby.UserInterfaceType;
import lobby.networking.RemoteRmiClient;
import lobby.networking.client.Client;
import lobby.networking.client.GenericClient;
import lobby.networking.client.TextUserInterface;
import lobby.networking.server.Server;
import lobby.networking.server.rmi.MainRmiServer;
import lobby.networking.RemoteMainRmiServer;
import lobby.networking.RemoteRmiServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

    private Registry registry;
    private RemoteMainRmiServer remoteMainRmiServer;
    private RemoteRmiServer remoteRmiServer;

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
            remoteRmiServer = remoteMainRmiServer.connect(this);
            Console.write("Server remote object loaded!");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry binding",e);
        } catch (NotBoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry lookup",e);
        }

        if (client.getUserInterfaceType() == UserInterfaceType.TEXT)
            setUserInterface(new TextUserInterface(this));
    }

    @Override
    public void login(String userName) {
        try {
            remoteRmiServer.tryLogin(userName);
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote connection",e);
        }
    }

    @Override
    public void logout() {
        try {
            remoteRmiServer.tryLogout(getUserName());
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote connection",e);
        }
    }

    @Override
    public void write(String action) {
        Console.write(action);
    }
}
