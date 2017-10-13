package lobby.networking.server.rmi;

import lobby.networking.RemoteRmiServer;
import lobby.networking.RemoteRmiClient;
import lobby.networking.server.GenericServer;
import lobby.networking.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RmiServer extends GenericServer implements RemoteRmiServer {
    private static final long serialVersionUID = 8645497989151621062L;

    static final long PING_INTERVAL_SECS = 2;

    private RemoteRmiClient remoteRmiClient;
    //private transient GameController gameController;
    //private transient ServerGeneralInterfaceImpl playerInterface;
    //private transient ObserversNetworkHandler observersNetworkHandler;

    private transient Timer timer;

    RmiServer(RemoteRmiClient stub) {
        this.remoteRmiClient = stub;
        //playerInterface = new ServerGeneralInterfaceImpl();
        this.timer = new Timer();
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during registry binding", e);
        }
    }

    @Override
    public void tryLogin(String userName) {
        boolean loginResult = Server.getServerInstance().addUser(userName, this);
        try {
            remoteRmiClient.setLogged(loginResult);
            if (loginResult) {
                remoteRmiClient.write("Login successful!");
                remoteRmiClient.setUserName(userName);
                setLogged(true);
                setUserName(userName);
            }
            else
                remoteRmiClient.write("Username already taken.");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote connection",e);
        }
    }

    @Override
    public void tryLogout(String userName) {
        boolean logoutResult = Server.getServerInstance().removeUser(userName);
        try {
            remoteRmiClient.setLogged(! logoutResult);
            if (logoutResult) {
                remoteRmiClient.write("Logout successful!");
                setLogged(false);
            }
            else
                remoteRmiClient.write("Something went wrong! Still logged.");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote connection", e);
        }
    }
}
