package lobby.actions;

import lobby.model.LobbyModel;

public abstract class Action {

    private String userName;

    public Action(String userName) {
        this.userName = userName;
    }

    public abstract void execute(LobbyModel lobbyModel);

    public String getUserName() {
        return userName;
    }
}
