package lobby.messages.changes;

import lobby.model.UserModel;
import lobby.view.LobbyView;
import lobby.view.UserView;

public class AddUserChange extends ModelChange {
    private final UserModel userModel;

    public AddUserChange(UserModel userModel) {
        super(userModel.getUserName());
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public void execute(LobbyView lobbyView) {
        UserView userView = new UserView(userModel.getUserName());
        lobbyView.getUsers().put(userModel.getUserName(), userView);

        if (userModel.getUserName().equals(lobbyView.getUserName()))
            lobbyView.setLogged(true);
    }
}
