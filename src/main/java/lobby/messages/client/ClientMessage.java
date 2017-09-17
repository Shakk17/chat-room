package lobby.messages.client;

import java.io.IOException;
import java.io.Serializable;

public interface ClientMessage extends Serializable {
    /**
     * Implements a Visitor pattern to execute all possible messages.
     * @throws IOException if a socket connectivity occurs.
     */
    void execute() throws IOException;
}
