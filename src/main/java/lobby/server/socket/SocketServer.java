package lobby.server.socket;

import lobby.SocketStream;
import lobby.messages.AskLoginMessage;
import lobby.messages.LoginMessage;
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
     * Socket implementation of GeneralInterface who will handle the player's methods calls
     */
    private SocketUserInterface socketUserInterface;

    /**
     * Will handle the player's view updates.
     */
    private ObserversNetworkHandler observersNetworkHandler;

    /**
     * @param socket Will handle the player connectivity.
     * @throws IOException if not able to create the ObjectStreams relative to this socket.
     */
    SocketServer(Socket socket) throws IOException {
        this.socketStream = new SocketStream(socket);
        this.socketUserInterface = new SocketUserInterface(this);
        setUserName("not logged user");
    }

    /**
     * Handles the player's login or the player reconnection.
     */
    public void run(){
        loginHandler();
    }

    /**
     * Handles the login of a client, this method won't end until the client is authenticated or disconnects.
     */
    private void loginHandler() {
        socketStream.sendObject(new AskLoginMessage());
        while (!isLogged()) {
            LoginMessage loginMessage = (LoginMessage)socketStream.receiveObject();

            setLogged(Server.getServerInstance().addUser(loginMessage.getUsername(), this));
            socketStream.sendBoolean(isLogged());
            if (isLogged())
                setUserName(loginMessage.getUsername());
        }
    }
}
