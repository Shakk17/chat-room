package lobby.messages.server;

import lobby.client.socket.SocketClient;

import java.io.IOException;
import java.io.Serializable;

/**
 * Every message sent from the server through socket connectivity has to implement this class.
 */
public interface ServerMessage extends Serializable {

	/**
	 * Implements a Visitor pattern to execute all possible messages.
	 * @param socketClient will handle the message forwarding and eventual returned values.
	 * @throws IOException if a socket connectivity occurs.
	 */
	void execute(SocketClient socketClient) throws IOException;
}
