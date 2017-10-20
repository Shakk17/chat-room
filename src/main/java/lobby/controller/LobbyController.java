package lobby.controller;

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

    @Override
    public void update(Observable lobby, Object arg) {
        
    }
}
