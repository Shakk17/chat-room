package lobby.messages;

import lobby.client.socket.SocketClient;

import java.io.IOException;

public class AskLoginMessage extends Message {
    /**
     * Implements a Visitor pattern to execute all possible messages.
     *
     * @param socketClient will handle the message forwarding and eventual returned values.
     * @throws IOException if a socket connectivity occurs.
     */
    public void execute(SocketClient socketClient) throws IOException {
        socketClient.getUserInterface().login();
    }
}
