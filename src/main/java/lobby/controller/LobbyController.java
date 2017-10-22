package lobby.controller;

import lobby.ModelChange;
import lobby.actions.client.ClientAction;
import lobby.model.Lobby;
import lobby.view.LobbyView;

import java.util.Observable;
import java.util.Observer;

public class LobbyController implements Observer {

    private Lobby lobby;
    private LobbyView lobbyView;

    public LobbyController(Lobby lobby) {
        this.lobby = lobby;
    }

    void update(ClientAction clientAction) throws IllegalAccessException {

    }

    void update(ModelChange modelChange) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
