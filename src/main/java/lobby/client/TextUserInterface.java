package lobby.client;

import lobby.Console;
import lobby.messages.client.LoginMessage;
import lobby.view.LobbyView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextUserInterface implements UserInterface {

    private GenericClient genericClient;
    private LobbyView lobbyView;

    public TextUserInterface(GenericClient genericClient) {
        this.genericClient = genericClient;
        readChoice();
    }

    public void readChoice() {
        Console.write("Write 'users' to see the users logged in the lobby.");
        while (genericClient.isLogged()) {
            String input = Console.readString();
            switch (input) {
                case "users":

                    break;
                case "logout":
                    logout();
                    break;
                default:
                    Console.writeRed("Wrong input! Try again.");
                    break;
            }
        }
    }

    public void login() {
        while (!genericClient.isLogged()) {
            Console.write("Insert your username to enter the lobby: ");
            LoginMessage loginMessage = new LoginMessage(Console.readString());
            try {
                genericClient.sendMessage(loginMessage);
                genericClient.setLogged(genericClient.receiveBoolean());
            } catch(IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure while establishing socket connection.", e);
            }
            if (!genericClient.isLogged())
                Console.writeRed("This username is already taken. Try again.");
            else
                genericClient.setUsername(loginMessage.getUsername());
        }
        Console.writeGreen("Login successful!");
    }

    public void logout() {

    }
}
