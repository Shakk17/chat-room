package lobby.client.rmi;

import lobby.*;
import lobby.messages.actions.Action;
import lobby.messages.changes.ModelChange;
import lobby.client.GenericClient;
import lobby.client.TextUserInterface;
import lobby.server.Server;
import lobby.server.rmi.MainRmiServer;

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

    private RemoteRmiServer remoteRmiServer;

    /**
     * It connect a client to RMI server, saves remote references
     */
    public RmiClient(UserInterfaceType userInterfaceType) {
        super(userInterfaceType);
    }

    @Override
    public void launchClient() {
        try {
            Console.write("Loading RMI registry...");
            Registry registry = LocateRegistry.getRegistry(IP, PORT);
            RemoteMainRmiServer remoteMainRmiServer = (RemoteMainRmiServer) registry.lookup(MainRmiServer.REMOTE_SERVER_NAME);
            UnicastRemoteObject.exportObject(this, 0);
            remoteRmiServer = remoteMainRmiServer.connect(this);
            setConnected(true);
            Console.write("Server remote object loaded!");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry binding", e);
        } catch (NotBoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry lookup", e);
        }

        if (getUserInterfaceType() == UserInterfaceType.TEXT)
            setUserInterface(new TextUserInterface(this));
    }

    @Override
    public void sendAction(Action action) {
        try {
            remoteRmiServer.executeAction(action);
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote method call", e);
        }
    }

    @Override
    public void executeModelChange(ModelChange modelChange) throws RemoteException {
        modelChange.execute(getLobbyView());
    }

    @Override
    public void write(String action) {
        Console.write(action);
    }
}
