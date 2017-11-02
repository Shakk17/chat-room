package lobby.messages.actions;

import lobby.model.LobbyModel;

public class TryLogout extends Action {

    public TryLogout(Integer ID) {
        super(ID);
    }

    @Override
    public void execute(LobbyModel lobbyModel) {
        lobbyModel.removeUser(this.getID());
    }
}
