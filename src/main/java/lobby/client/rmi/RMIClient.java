package lobby.client.rmi;

import lobby.Console;
import lobby.client.Client;
import lobby.client.GenericClient;
import lobby.server.Server;
import lobby.server.rmi.RMIServer;

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

public class RMIClient extends GenericClient implements Remote {

    private static final String IP = Server.IP_ADDRESS;
    private static final int PORT = Server.RMI_PORT;

    /**
     * Value used to ping this client, always changed after a ping calls to avoid RMI caching problems
     */
    private int pingValue = 0;

    /**
     * Server's class created to communicate with only a specific client
     */
    private transient RemoteServer server;

    private transient Registry registry;

    /**
     * It connect a client to RMI server, saves remote references
     * @param client contains specific of the client.
     * @throws RemoteException if an RMI connectivity problems occurs
     */
    public RMIClient(Client client) throws RemoteException{
        super(client);
        UnicastRemoteObject.exportObject(this, 0);

        try {
            Console.write("Loading RMI registry...");
            registry = LocateRegistry.getRegistry(IP, PORT);
            generalServer = (RemoteGeneralServer) registry.lookup(RMIServer.REMOTE_SERVER_NAME);
            Console.write("Server remote object loaded!");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry binding",e);
        } catch (NotBoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry lookup",e);
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message message to be sent
     * @throws IOException if a socket connectivity occurs
     */
    @Override
    public void sendMessage(Object message) throws IOException {

    }

    /**
     * Sends a Boolean to the server.
     *
     * @param message boolean to be sent
     * @throws IOException if a socket connectivity occurs
     */
    @Override
    public void sendBoolean(boolean message) throws IOException {

    }

    /**
     * Sends a String to the server.
     *
     * @param message String to be sent
     * @throws IOException if a socket connectivity occurs
     */
    @Override
    public void sendString(String message) throws IOException {

    }

    /**
     * Receive an object from the server.
     *
     * @throws IOException            if there are socket connectivity problems.
     * @throws ClassNotFoundException if unexpected message.
     */
    @Override
    public Object receiveMessage() throws IOException, ClassNotFoundException {
        return null;
    }

    /**
     * Receive a String from the server.
     *
     * @throws IOException if there are socket connectivity problems.
     */
    @Override
    public String receiveString() throws IOException {
        return null;
    }

    /**
     * Receive a boolean from the server.
     *
     * @throws IOException if there are socket connectivity problems.
     */
    @Override
    public boolean receiveBoolean() {
        return false;
    }
}
