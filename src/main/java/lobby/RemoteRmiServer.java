package lobby;

import lobby.messages.actions.Action;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRmiServer extends Remote {
    void executeAction(Action action) throws RemoteException;
}
