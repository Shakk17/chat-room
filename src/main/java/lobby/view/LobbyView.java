package lobby.view;

import lobby.Observable;
import lobby.messages.actions.Action;
import lobby.controller.LobbyClientController;

import java.util.HashMap;
import java.util.Map;

public class LobbyView extends Observable<Action> {

    private Map<String, UserView> users;
    private String userName;
    private boolean logged;

    public LobbyView() {
        this.users = new HashMap<>();
    }

    public Map<String, UserView> getUsers() {
        return users;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }
}
