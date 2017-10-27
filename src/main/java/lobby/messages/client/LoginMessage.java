package lobby.messages.client;

import lobby.server.Server;
import lobby.server.socket.SocketServer;

import java.io.IOException;

public class LoginMessage implements ClientMessage {
    private static final long serialVersionUID = -4520332386402808781L;

    private String userName;

    public LoginMessage(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void execute(SocketServer socketServer) throws IOException {
        boolean loginResult = Server.getServerInstance().addUser(userName, socketServer);
        socketServer.getSocketStream().sendBoolean(loginResult);
        socketServer.setLogged(loginResult);
        if (loginResult)
            socketServer.setUserName(userName);
    }
}
