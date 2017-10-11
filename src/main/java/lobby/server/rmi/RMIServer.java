package lobby.server.rmi;

import lobby.Console;
import lobby.client.rmi.RemoteRmiClient;
import lobby.server.GenericServer;

import java.rmi.RemoteException;
import java.util.Timer;

public class RmiServer extends GenericServer implements RemoteRmiServer {
    private static final long serialVersionUID = 8645497989151621062L;

    static final long PING_INTERVAL_SECS = 2;

    private transient RemoteRmiClient client;
    private String username;
    //private transient GameController gameController;
    //private transient ServerGeneralInterfaceImpl playerInterface;
    //private transient ObserversNetworkHandler observersNetworkHandler;

    private transient Timer timer;

    RmiServer(String username, RemoteRmiClient stub) throws RemoteException {
        this.client = stub;
        this.username = username;
        //playerInterface = new ServerGeneralInterfaceImpl();
        this.timer = new Timer();
    }

    void print(String s){
        Console.write("[CLIENT RMI " + this.username + "]: " + s);
    }
}
