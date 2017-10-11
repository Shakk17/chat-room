package lobby.server.rmi;

import lobby.client.rmi.RemoteRmiClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMainRmiServer extends Remote {
    RemoteRmiServer connect(String userName, RemoteRmiClient remoteRmiClient) throws RemoteException;
}
