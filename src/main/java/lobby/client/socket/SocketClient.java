package lobby.client.socket;

import lobby.Console;
import lobby.UserInterfaceType;
import lobby.SocketStream;
import lobby.messages.actions.Action;
import lobby.messages.changes.ModelChange;
import lobby.client.GenericClient;
import lobby.client.TextUserInterface;
import lobby.messages.server.IdMessage;
import lobby.messages.server.ServerMessage;

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
     */
    public SocketClient(UserInterfaceType userInterfaceType) {
        super(userInterfaceType);
    }

    @Override
    public void launchClient() {
        Socket socket;
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            socketStream = new SocketStream(socket);
            setConnected(true);
            Console.write("Socket connection established.");
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure while establishing socket connection.", e);
        }

        new Thread(this).start();

        if (getUserInterfaceType() == UserInterfaceType.TEXT)
            setUserInterface(new TextUserInterface(this));
    }

    @Override
    public void sendAction(Action action) {
        socketStream.sendObject(action);
    }

    public void run() {
        waitForMessage();
    }

    /**
     * Waits for messages from the server and executes their logic using a visitor pattern.
     */
    private void waitForMessage() {
        while (isConnected()) {
            Object message = getSocketStream().receiveObject();
            if (message instanceof ModelChange)
                ((ModelChange) message).execute(getLobbyView());
            else if (message instanceof ServerMessage)
                try {
                    ((ServerMessage) message).execute(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public SocketStream getSocketStream() {
        return socketStream;
    }
}
