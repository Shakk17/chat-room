package lobby.launchers;

import lobby.networking.server.Server;

public class ServerLauncher {

    public static void main(String[] args) {
        Server serverInstance = Server.getServerInstance();
        serverInstance.launchServers();
    }
}
