package lobby.client;

import lobby.Console;
import lobby.view.LobbyView;

public class TextUserInterface implements UserInterface {

    private GenericClient genericClient;
    private LobbyView lobbyView;

    public TextUserInterface(GenericClient genericClient) {
        this.genericClient = genericClient;
        readChoice();
    }

    private void readChoice() {
        while (genericClient.isConnected()) {
            String input = Console.readString();
            switch (input) {
                case "tryLogin":
                    tryLogin();
                    break;
                case "tryLogout":
                    tryLogout();
                    break;
                default:
                    Console.writeRed("Wrong input! Try again.");
                    break;
            }
        }
    }

    public void tryLogin() {
        if (genericClient.isLogged()) {
            Console.write("You are already logged in!");
            return;
        }
        Console.write("Insert your username to enter the lobby: ");
        genericClient.login(Console.readString());
    }

    public void tryLogout() {
        if (! genericClient.isLogged()) {
            Console.writeRed("You're not even logged in -.-");
            return;
        }
        genericClient.logout();
    }
}
