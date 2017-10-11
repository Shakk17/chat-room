package lobby.client;

import lobby.view.LobbyView;

public abstract class GenericClient {

    private Client client;
    private LobbyView lobbyView;
    private UserInterface userInterface;

    private boolean connected;
    private boolean logged;
    private String username;

    public GenericClient(Client client) {
        setClient(client);
        setConnected(false);
        setLogged(false);
    }

    abstract public void login(String userName);
    abstract public void logout();

    public Client getClient() {
        return client;
    }

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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
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
