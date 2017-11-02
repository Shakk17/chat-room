package lobby.messages.actions;

import lobby.model.LobbyModel;

public class TryLogin extends Action {

    private String userName;

    public TryLogin(Integer ID, String userName) {
        super(ID);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void execute(LobbyModel lobbyModel) {
        lobbyModel.addUser(getID(), getUserName());
    }
}
