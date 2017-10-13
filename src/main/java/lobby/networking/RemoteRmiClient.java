package lobby.networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRmiClient extends Remote {
    void setConnected(boolean connected) throws RemoteException;
    void setLogged(boolean logged) throws RemoteException;
    void setUserName(String userName) throws RemoteException;
    void write(String action) throws RemoteException;
}