package lobby.messages.actions;

import lobby.model.LobbyModel;

import java.io.Serializable;

public abstract class Action implements Serializable {

    private int ID;

    public Action(Integer ID) {
        this.ID = ID;
    }

    public abstract void execute(LobbyModel lobbyModel);

    public Integer getID() {
        return ID;
    }
}
