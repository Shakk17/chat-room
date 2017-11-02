package lobby.controller;

import lobby.Observer;
import lobby.messages.changes.ModelChange;
import lobby.server.GenericServer;
import lobby.server.Server;

import java.util.Map;

public class LobbyServerController implements Observer<ModelChange> {

    @Override
    public void update(ModelChange modelChange) {
        String userName = modelChange.getUserName();
        Integer ID = Server.getServerInstance().getIdbyUserName(userName);
        Server.getServerInstance().getUsersGenericServers().get(ID).sendModelChange(modelChange);
    }
}
