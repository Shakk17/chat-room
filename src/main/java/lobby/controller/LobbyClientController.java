package lobby.controller;

import lobby.Observer;
import lobby.client.GenericClient;
import lobby.messages.actions.Action;

public class LobbyClientController implements Observer<Action> {

    private GenericClient genericClient;

    public LobbyClientController(GenericClient genericClient) {
        this.genericClient = genericClient;
    }

    @Override
    public void update(Action action) {
        genericClient.sendAction(action);
    }
}
