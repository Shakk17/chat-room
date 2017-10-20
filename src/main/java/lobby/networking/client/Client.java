package lobby.networking.client;

import lobby.ConnectionType;
import lobby.Console;
import lobby.UserInterfaceType;
import lobby.networking.client.rmi.RmiClient;
import lobby.networking.client.socket.SocketClient;
import lobby.view.LobbyView;

public class Client {

    private ConnectionType connectionType;
    private UserInterfaceType userInterfaceType;
    private GenericClient genericClient;
    private LobbyView lobbyView;

    public void launchClient() {
        Console.write("What type of connection do you want to use?");
        Console.writeBlue("Type 1 for socket, 2 for RMI: ");
        connectionType = Console.readInt(1, 2) == 1 ? ConnectionType.SOCKET : ConnectionType.RMI;

        Console.write("What type of interface do you want to use?");
        Console.writeBlue("Type 1 for text, 2 for graphic: ");
        userInterfaceType = Console.readInt(1, 2) == 1 ? UserInterfaceType.TEXT: UserInterfaceType.GRAPHIC;

        genericClient = connectionType == ConnectionType.SOCKET ? new SocketClient(this) : new RmiClient(this);
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public UserInterfaceType getUserInterfaceType() {
        return userInterfaceType;
    }

    public GenericClient getGenericClient() {
        return genericClient;
    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public void setLobbyView(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
    }
}
