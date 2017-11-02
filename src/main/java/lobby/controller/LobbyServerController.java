package lobby.controller;

import lobby.Observer;
import lobby.changes.ModelChange;
import lobby.model.LobbyModel;
import lobby.server.Server;
import lobby.view.LobbyView;

import java.io.Serializable;

public class LobbyController implements Observer<ModelChange>, Serializable {

    private Server server;

    private LobbyModel lobbyModel;
    private LobbyView lobbyView;

    public LobbyController(Server server, LobbyModel lobbyModel) {
        this.server = server;
        this.lobbyModel = lobbyModel;

        lobbyModel.registerObserver(this);
        this.lobbyView = new LobbyView(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(ModelChange change) {

    }
}
