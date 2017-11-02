package lobby.launchers;

import lobby.ConnectionType;
import lobby.Console;
import lobby.UserInterfaceType;
import lobby.client.GenericClient;
import lobby.client.rmi.RmiClient;
import lobby.client.socket.SocketClient;

public class ClientLauncher {

    public static void main(String[] args) {
        Console.write("What type of connection do you want to use?");
        Console.writeBlue("Type 1 for socket, 2 for RMI: ");
        ConnectionType connectionType = Console.readInt(1, 2) == 1 ? ConnectionType.SOCKET : ConnectionType.RMI;

        Console.write("What type of interface do you want to use?");
        Console.writeBlue("Type 1 for text, 2 for graphic: ");
        UserInterfaceType userInterfaceType = Console.readInt(1, 2) == 1 ? UserInterfaceType.TEXT: UserInterfaceType.GRAPHIC;

        GenericClient genericClient = connectionType == ConnectionType.SOCKET ? new SocketClient(userInterfaceType) : new RmiClient(userInterfaceType);
        genericClient.launchClient();
    }
}
