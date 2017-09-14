package lobby.server;

/**
 * Interface implemented by both RMI and Socket server. It'll handle the net-connectivity of each player.
 */
public abstract class GenericServer {

    private boolean logged;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
