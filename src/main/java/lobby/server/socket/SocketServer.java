package lobby.server.socket;

import lobby.SocketStream;
import lobby.messages.client.ClientMessage;
import lobby.messages.client.LoginMessage;
import lobby.server.Server;
import lobby.server.GenericServer;
import lobby.server.networkObservers.ObserversNetworkHandler;

import java.io.IOException;
import java.net.Socket;

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
     * Handles the login of a client, this method won't end until the client is authenticated or disconnects.
     */

    //TODO: cambio e faccio con messaggi, non è server che chiede login, ma client
    private void waitForMessages() {
        while (isConnected()) {
            ClientMessage clientMessage = (ClientMessage)socketStream.receiveObject();
            try {
                clientMessage.execute(this);
            } catch (IOException e) {
                e.printStackTrace();
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
