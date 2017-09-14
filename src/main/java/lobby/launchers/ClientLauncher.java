package lobby.launchers;

import lobby.client.Client;

public class ClientLauncher {

    public static void main(String[] args) {
        Client client = new Client();
        client.launchClient();
    }
}
