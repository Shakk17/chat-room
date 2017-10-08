package lobby.server.socket;

import lobby.SocketStream;
import lobby.messages.client.ClientMessage;
import lobby.server.GenericServer;
import lobby.server.networkObservers.ObserversNetworkHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the socket connectivity of a single player.
 */
public class SocketServer extends GenericServer implements Runnable {

    /**
     * Input and output streams which will handle the socket connectivity.
     */
    private SocketStream socketStream;

    /**
     * Will handle the player's view updates.
     */
    private ObserversNetworkHandler observersNetworkHandler;

    /**
     * @param socket Will handle the player connectivity.
     * @throws IOException if not able to create the ObjectStreams relative to this socket.
     */
    SocketServer(Socket socket) throws IOException {
        super();
        this.socketStream = new SocketStream(socket);
    }

    /**
     * Handles the player's login or the player reconnection.
     */
    public void run(){
        waitForMessages();
    }

    /**
     * Waits for messages and executes them.
     */
    private void waitForMessages() {
        while (isConnected()) {
            ClientMessage clientMessage = (ClientMessage)socketStream.receiveObject();
            try {
                clientMessage.execute(this);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,"[SERVER SOCKET]: Failure while receiving messages!", e);
            }
        }
    }

    public SocketStream getSocketStream() {
        return socketStream;
    }

    public void setSocketStream(SocketStream socketStream) {
        this.socketStream = socketStream;
    }
}
