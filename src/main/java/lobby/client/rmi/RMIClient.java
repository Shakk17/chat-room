package lobby.client.rmi;

import lobby.client.Client;
import lobby.client.GenericClient;

import java.io.IOException;

public class RMIClient extends GenericClient {

    public RMIClient(Client client) {
        setClient(client);
    }

    /**
     * Sends a message to the server.
     *
     * @param message message to be sent
     * @throws IOException if a socket connectivity occurs
     */
    @Override
    public void sendMessage(Object message) throws IOException {

    }

    /**
     * Sends a Boolean to the server.
     *
     * @param message boolean to be sent
     * @throws IOException if a socket connectivity occurs
     */
    @Override
    public void sendBoolean(boolean message) throws IOException {

    }

    /**
     * Sends a String to the server.
     *
     * @param message String to be sent
     * @throws IOException if a socket connectivity occurs
     */
    @Override
    public void sendString(String message) throws IOException {

    }

    /**
     * Receive an object from the server.
     *
     * @throws IOException            if there are socket connectivity problems.
     * @throws ClassNotFoundException if unexpected message.
     */
    @Override
    public Object receiveMessage() throws IOException, ClassNotFoundException {
        return null;
    }

    /**
     * Receive a String from the server.
     *
     * @throws IOException if there are socket connectivity problems.
     */
    @Override
    public String receiveString() throws IOException {
        return null;
    }

    /**
     * Receive a boolean from the server.
     *
     * @throws IOException if there are socket connectivity problems.
     */
    @Override
    public boolean receiveBoolean() {
        return false;
    }
}
