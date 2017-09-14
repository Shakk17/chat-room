package lobby.client;

import lobby.view.LobbyView;

import java.io.IOException;

public abstract class GenericClient {

    private Client client;
    private LobbyView lobbyView;
    private UserInterface userInterface;

    private boolean logged;
    private String username;

    /**
     * Tries to log into server, returns the server response.
     */
    public void login() {
        userInterface.login();
    }

    /**
     * Sends a message to the server.
     * @param message message to be sent
     * @throws IOException if a socket connectivity occurs
     */
    public abstract void sendMessage(Object message) throws IOException;

    /**
     * Sends a Boolean to the server.
     * @param message boolean to be sent
     * @throws IOException if a socket connectivity occurs
     */
    public abstract void sendBoolean(boolean message) throws IOException;

    /**
     * Sends a String to the server.
     * @param message String to be sent
     * @throws IOException if a socket connectivity occurs
     */
    public abstract void sendString(String message) throws IOException;

    /**
     * Receive an object from the server.
     * @throws IOException if there are socket connectivity problems.
     * @throws ClassNotFoundException if unexpected message.
     */
    public abstract Object receiveMessage() throws IOException, ClassNotFoundException;

    /**
     * Receive a String from the server.
     * @throws IOException if there are socket connectivity problems.
     */
    public abstract String receiveString() throws IOException;

    /**
     * Receive a boolean from the server.
     * @throws IOException if there are socket connectivity problems.
     */
    public abstract boolean receiveBoolean();

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLobbyView(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
