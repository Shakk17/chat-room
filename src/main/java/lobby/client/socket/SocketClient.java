package lobby.client.socket;

import lobby.Console;
import lobby.UserInterfaceType;
import lobby.client.Client;
import lobby.client.GenericClient;
import lobby.SocketStream;
import lobby.client.TextUserInterface;
import lobby.messages.client.LoginMessage;
import lobby.messages.client.LogoutMessage;
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
     * @param client contains specific of the client.
     */
    public SocketClient(Client client) {
        super(client);

        Socket socket;
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            socketStream = new SocketStream(socket);
            setConnected(true);
            Console.write("Socket connection established.");
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure while establishing socket connection.", e);
        }

        if (client.getUserInterfaceType() == UserInterfaceType.TEXT)
            setUserInterface(new TextUserInterface(this));

        new Thread(this).start();
    }

    public void run() {
        waitForMessage();
    }

    /**
     * Waits for messages from the server and executes their logic using a visitor pattern.
     */
    private void waitForMessage() {
        while (isConnected()) {
            ServerMessage serverMessage = (ServerMessage) getSocketStream().receiveObject();
            try {
                serverMessage.execute(this);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Socket connectivity error.", e);
            }
        }
    }

    @Override
    public void login(String userName) {
        LoginMessage loginMessage = new LoginMessage(userName);
        socketStream.sendObject(loginMessage);
        setLogged(socketStream.receiveBoolean());
        if (isLogged()) {
            setUsername(loginMessage.getUserName());
            Console.writeGreen("Login successful!");
        }
        else
            Console.writeRed("This username is already taken. Try again.");
    }

    @Override
    public void logout() {
        LogoutMessage logoutMessage = new LogoutMessage(getUsername());
        socketStream.sendObject(logoutMessage);
        boolean logoutSuccessful = socketStream.receiveBoolean();
        setLogged(! logoutSuccessful);
        if (logoutSuccessful)
            Console.write("You just logged out!");
    }

    public SocketStream getSocketStream() {
        return socketStream;
    }
}
