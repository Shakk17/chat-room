package lobby;

import lobby.messages.changes.ModelChange;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRmiClient extends Remote {
    void setConnected(boolean connected) throws RemoteException;
    void setID(Integer ID) throws RemoteException;
    void write(String action) throws RemoteException;
    void executeModelChange(ModelChange modelChange) throws RemoteException;
}