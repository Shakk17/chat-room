package lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles input and output socket streams.
 */
public class SocketStream {

    private static final String IO_ERROR_MESSAGE = "Socket connectivity error.";

    /**
     * Socket assigned to a client.
     */
    private Socket socket;

    /**
     * Writes data and objects on an OutputStream.
     */
    private ObjectOutputStream socketOut;

    /**
     * Reads data and objects from an OutputStream.
     */
    private ObjectInputStream socketIn;

    /**
     * @param socket socket instance to be handled.
     * @throws IOException if there are socket connectivity problems.
     */
    public SocketStream(Socket socket) throws IOException {
        this.socket = socket;
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        this.socketIn = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Sends an object through the net.
     */
    public void sendObject(Object object) {
        try {
            socketOut.writeObject(object);
            socketOut.flush();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, IO_ERROR_MESSAGE, e);
        }
    }

    /**
     * Sends a String through the net.
     */
    public void sendString(String message) {
        try {
            socketOut.writeUTF(message);
            socketOut.flush();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, IO_ERROR_MESSAGE, e);
        }

    }

    /**
     * Sends a boolean through the net.
     */
    public void sendBoolean(boolean message) {
        try {
            socketOut.writeBoolean(message);
            socketOut.flush();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, IO_ERROR_MESSAGE, e);
        }
    }

    /**
     * Receives an object through the socket's input stream.
     * @return the Object received.
     */
    public Object receiveObject() {
        Object message = null;
        try {
            message = socketIn.readObject();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, IO_ERROR_MESSAGE, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unexpected message.", e);
        }
        return message;
    }

    /**
     * Receives a String through the socket's input stream.
     * @return the String received.
     */
    public String receiveString() {
        String string = "";
        try {
            string = socketIn.readUTF();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, IO_ERROR_MESSAGE, e);
        }
        return string;
    }

    /**
     * Receives a Boolean through the socket's input stream.
     * @return the Boolean received.
     */
    public boolean receiveBoolean() {
        boolean booleanReceived = false;
        try {
            booleanReceived = socketIn.readBoolean();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, IO_ERROR_MESSAGE, e);
        }
        return booleanReceived;
    }

    /**
     * Set the player's move timeout as socket timeout
     * @param seconds timeout value to be set
     * @throws SocketException thrown to indicate that there is an error accessing the socket
     */
    public void setTimeout(int seconds) throws SocketException {
        socket.setSoTimeout(seconds*1000);
    }

    /**
     * Closes socket's input and output streams.
     * @throws IOException if an error occurred closing the socket.
     */
    public void closeStreams() throws IOException {
        if (!socket.isClosed())
            socket.close();
    }
}
