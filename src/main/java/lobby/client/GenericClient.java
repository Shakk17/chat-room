package lobby.client;

import lobby.UserInterfaceType;
import lobby.controller.LobbyClientController;
import lobby.messages.actions.Action;
import lobby.messages.actions.TryLogin;
import lobby.messages.actions.TryLogout;
import lobby.view.LobbyView;

public abstract class GenericClient {

    private UserInterfaceType userInterfaceType;
    private UserInterface userInterface;
    private boolean connected;
    private Integer ID;

    private LobbyView lobbyView;
    private LobbyClientController lobbyClientController;

    public GenericClient(UserInterfaceType userInterfaceType) {
        this.userInterfaceType = userInterfaceType;

        this.connected = false;

        this.lobbyView = new LobbyView();
        this.lobbyClientController = new LobbyClientController(this);
        lobbyView.registerObserver(lobbyClientController);
    }

    public abstract void launchClient();

    public abstract void sendAction(Action action);

    void tryLogin(String userName) {
        getLobbyView().setUserName(userName);
        getLobbyView().notifyObservers(new TryLogin(getID(), userName));
    }

    void tryLogout() {
        getLobbyView().notifyObservers(new TryLogout(getID()));
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public LobbyClientController getLobbyClientController() {
        return lobbyClientController;
    }

    public UserInterfaceType getUserInterfaceType() {
        return userInterfaceType;
    }
}
