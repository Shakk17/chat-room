package lobby.client.socket;

import lobby.Console;
import lobby.UserInterfaceType;
import lobby.client.Client;
import lobby.client.GenericClient;
import lobby.SocketStream;
import lobby.client.TextUserInterface;
import lobby.messages.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a client's network communications using socket.
 */
public class SocketClient extends GenericClient implements Runnable {

    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;

    private SocketStream socketStream;

    /**
     * Sets up the socket stream handling the connection.
     * @param client contains specific of the client.
     */
    public SocketClient(Client client) {
        setClient(client);
        setLogged(false);

        Socket socket;
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            socketStream = new SocketStream(socket);
            Console.write("Socket connection established.");
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure while establishing socket connection.", e);
        }

        if (client.getUserInterfaceType() == UserInterfaceType.TEXT)
            setUserInterface(new TextUserInterface(this));

        login();
        new Thread(this).start();
        getUserInterface().handleInput();
    }

    @Override
    public void run() {
        waitForMessage();
    }

    /**
     * Waits for messages from the server and executes their logic using a visitor pattern.
     */
    public void waitForMessage() {
        while (isLogged()) {
            Message message = (Message) receiveMessage();
            try {
                message.handleMessage(this);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Socket connectivity error.", e);
            }
        }
    }

    /**
     * Receives an Object from the server.
     */
    public Object receiveMessage() {
        return socketStream.receiveObject();
    }

    /**
     * Receives a String from the server.
     */
    @Override
    public String receiveString() {
        return socketStream.receiveString();
    }

    /**
     * Receives a boolean from the server.
     */
    @Override
    public boolean receiveBoolean() {
        return socketStream.receiveBoolean();
    }

    /**
     * Sends a message to the server.
     */
    @Override
    public void sendMessage(Object message) {
        socketStream.sendObject(message);
    }

    /**
     * Sends a boolean to the server.
     */
    @Override
    public void sendBoolean(boolean message) {
        socketStream.sendBoolean(message);
    }

    /**
     * Sends a String to the server.
     */
    @Override
    public void sendString(String message) throws IOException {
        socketStream.sendString(message);
    }
}
