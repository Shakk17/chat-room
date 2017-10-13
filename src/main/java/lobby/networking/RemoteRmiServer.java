package lobby.networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRmiServer extends Remote {
    void tryLogin(String userName) throws RemoteException;
    void tryLogout(String userName) throws RemoteException;
}
