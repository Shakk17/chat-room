package lobby.messages;

import lobby.client.socket.SocketClient;

import java.io.IOException;
import java.io.Serializable;

/**
 * Main Message class, all messages sent through sockets connectivity have to implements this class
 */
public abstract class Message implements Serializable {

	/**
	 * Implements a Visitor pattern to execute all possible messages.
	 * @param socketClient will handle the message forwarding and eventual returned values.
	 * @throws IOException if a socket connectivity occurs.
	 */
	public abstract void execute(SocketClient socketClient) throws IOException;
}
