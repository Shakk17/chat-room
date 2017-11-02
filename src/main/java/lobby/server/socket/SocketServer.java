package lobby.server.socket;

import lobby.SocketStream;
import lobby.messages.actions.Action;
import lobby.messages.changes.ModelChange;
import lobby.messages.server.IdMessage;
import lobby.server.GenericServer;
import lobby.server.Server;

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

    SocketServer(Socket socket) throws IOException {
        super();
        this.socketStream = new SocketStream(socket);
        socketStream.sendObject(new IdMessage(getID()));
    }

    public void run(){
        waitForMessages();
    }

    private void waitForMessages() {
        while (isConnected()) {
            Object clientMessage = socketStream.receiveObject();
            if (clientMessage instanceof Action)
                ((Action) clientMessage).execute(Server.getServerInstance().getLobbyModel());
        }
    }

    @Override
    public void sendModelChange(ModelChange modelChange) {
        socketStream.sendObject(modelChange);
    }

    public SocketStream getSocketStream() {
        return socketStream;
    }

    public void setSocketStream(SocketStream socketStream) {
        this.socketStream = socketStream;
    }
}
