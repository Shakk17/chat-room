package lobby.messages.client;

import lobby.networking.server.Server;
import lobby.networking.server.socket.SocketServer;

import java.io.IOException;

public class LogoutMessage implements ClientMessage {
    private String userName;

    public LogoutMessage(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Implements a Visitor pattern to execute all possible messages.
     *
     * @throws IOException if a socket connectivity occurs.
     */
    @Override
    public void execute(SocketServer socketServer) throws IOException {
        boolean userRemoved = Server.getServerInstance().removeUser(getUserName());
        socketServer.setLogged(!userRemoved);
        socketServer.getSocketStream().sendBoolean(!socketServer.isLogged());
    }
}
