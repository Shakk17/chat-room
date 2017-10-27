package lobby.launchers;

import lobby.server.Server;

public class ServerLauncher {

    public static void main(String[] args) {
        Server serverInstance = Server.getServerInstance();
        serverInstance.launchServers();
    }
}
