package lobby.messages.changes;

import lobby.view.LobbyView;

import java.io.Serializable;

public abstract class ModelChange implements Serializable {

    private String userName;

    public ModelChange(String userName) {
        this.userName = userName;
    }

    public abstract void execute(LobbyView lobbyView);

    public String getUserName() {
        return userName;
    }
}
