package lobby.server.rmi;

import lobby.RemoteRmiServer;
import lobby.RemoteRmiClient;
import lobby.messages.actions.Action;
import lobby.messages.changes.ModelChange;
import lobby.server.GenericServer;
import lobby.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RmiServer extends GenericServer implements RemoteRmiServer {

    private RemoteRmiClient remoteRmiClient;

    RmiServer(RemoteRmiClient remoteRmiClient) {
        this.remoteRmiClient = remoteRmiClient;
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry binding", e);
        }

        try {
            remoteRmiClient.setID(getID());
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during ID settings", e);
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(Server.getServerInstance().getLobbyModel());
    }

    @Override
    public void sendModelChange(ModelChange modelChange) {
        try {
            remoteRmiClient.executeModelChange(modelChange);
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote connection", e);
        }
    }
}
