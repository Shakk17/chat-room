package lobby.server;

/**
 * Interface implemented by both RMI and Socket server. It'll handle the net-connectivity of each player.
 */
public abstract class GenericServer {

    private boolean connected;
    private boolean logged;
    private String userName;

    public GenericServer() {
        this.connected = true;
        this.logged = false;
        this.userName = "not set yet";
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
