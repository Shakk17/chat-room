package lobby.server;

import lobby.messages.changes.ModelChange;

/**
 * Interface implemented by both RMI and Socket server. It'll handle the net-connectivity of each player.
 */
public abstract class GenericServer {

    private Integer ID;
    private boolean connected;

    public GenericServer() {
        setConnected(true);

        setID(Server.getServerInstance().getUsersGenericServers().size());
        Server.getServerInstance().getUsersGenericServers().add(this);
    }

    public abstract void sendModelChange(ModelChange modelChange);

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
