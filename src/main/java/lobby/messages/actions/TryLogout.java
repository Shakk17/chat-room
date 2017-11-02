package lobby.actions;

import lobby.model.LobbyModel;

public class TryLogout extends Action {

    public TryLogout(String userName) {
        super(userName);
    }

    @Override
    public void execute(LobbyModel lobbyModel) {
        lobbyModel.removeUser(this.getUserName());
    }
}
