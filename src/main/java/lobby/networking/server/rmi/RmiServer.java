package lobby.server.rmi;

import lobby.Console;
import lobby.client.rmi.RemoteRmiClient;
import lobby.client.rmi.RmiClient;
import lobby.server.GenericServer;
import lobby.server.Server;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RmiServer extends GenericServer implements RemoteRmiServer {
    private static final long serialVersionUID = 8645497989151621062L;

    static final long PING_INTERVAL_SECS = 2;

    private transient RemoteRmiClient remoteRmiClient;
    private String username = "unknown";
    //private transient GameController gameController;
    //private transient ServerGeneralInterfaceImpl playerInterface;
    //private transient ObserversNetworkHandler observersNetworkHandler;

    private transient Timer timer;

    RmiServer(RemoteRmiClient stub) throws RemoteException {
        this.remoteRmiClient = stub;
        //playerInterface = new ServerGeneralInterfaceImpl();
        this.timer = new Timer();
    }

    public void tryLogin(String userName) {
        boolean loginResult = Server.getServerInstance().addUser(userName, this);
        try {
            remoteRmiClient.setLogged(loginResult);
            if (loginResult)
                remoteRmiClient.write("Login successful!");
            else
                remoteRmiClient.write("Username already taken.");
        } catch (RemoteException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure during remote connection",e);
        }
    }
}
