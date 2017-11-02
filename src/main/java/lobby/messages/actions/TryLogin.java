package lobby.actions;

import lobby.model.LobbyModel;

public class TryLogin extends Action {

    public TryLogin(String userName) {
        super(userName);
    }

    @Override
    public void execute(LobbyModel lobbyModel) {
        lobbyModel.addUser(this.getUserName());
    }
}
