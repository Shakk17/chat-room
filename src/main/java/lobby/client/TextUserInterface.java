package lobby.client;

import lobby.Console;

public class TextUserInterface implements UserInterface {

    private GenericClient genericClient;

    public TextUserInterface(GenericClient genericClient) {
        this.genericClient = genericClient;
        readChoice();
    }

    private void readChoice() {
        while (genericClient.isConnected()) {
            Console.writeBlue("Type something.");
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

    @Override
    public void tryLogin() {
        if (genericClient.getLobbyView().isLogged()) {
            Console.writeRed("You are already logged in!");
            return;
        }
        Console.write("Insert your username to enter the lobby: ");
        genericClient.tryLogin(Console.readString());
    }

    @Override
    public void tryLogout() {
        if (! genericClient.getLobbyView().isLogged()) {
            Console.writeRed("You're not even logged in -.-");
            return;
        }
        genericClient.tryLogout();
    }
}
