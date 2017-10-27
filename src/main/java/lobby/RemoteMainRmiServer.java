package lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMainRmiServer extends Remote {
    RemoteRmiServer connect(RemoteRmiClient remoteRmiClient) throws RemoteException;
}
