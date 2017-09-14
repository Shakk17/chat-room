package lobby.messages;

import lobby.client.socket.SocketClient;

import java.io.IOException;

public class PrintActionMessage extends Message {

    private String action;

    public PrintActionMessage(String action) {
        this.action = action;
    }

    /**
     * Implements a Visitor pattern to handle all possible messages.
     *
     * @param socketClient will handle the message forwarding and eventual returned values.
     * @throws IOException if a socket connectivity occurs.
     */
    @Override
    public void handleMessage(SocketClient socketClient) throws IOException {

    }
}
