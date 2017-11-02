package lobby.messages.changes;

import lobby.model.UserModel;
import lobby.view.LobbyView;

public class RemoveUserChange extends ModelChange {

    private final UserModel userModel;

    public RemoveUserChange(UserModel userModel) {
        super(userModel.getUserName());
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public void execute(LobbyView lobbyView) {
        lobbyView.getUsers().remove(getUserName());

        if (userModel.getUserName().equals(lobbyView.getUserName()))
            lobbyView.setLogged(false);
    }
}
