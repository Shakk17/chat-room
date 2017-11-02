package lobby.messages.server;

import lobby.client.socket.SocketClient;

import java.io.IOException;

public class IdMessage implements ServerMessage {

    private final int ID;

    public IdMessage(Integer ID) {
        this.ID = ID;
    }

    @Override
    public void execute(SocketClient socketClient) throws IOException {
        socketClient.setID(ID);
    }
}
