package lobby.client;

import lobby.Console;
import lobby.messages.client.LoginMessage;
import lobby.messages.client.LogoutMessage;
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

    private void readChoice() {
        while (genericClient.isConnected()) {
            String input = Console.readString();
            switch (input) {
                case "login":
                    login();
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
        if (genericClient.isLogged()) {
            Console.write("You are already logged in!");
            return;
        }
        Console.write("Insert your username to enter the lobby: ");
        LoginMessage loginMessage = new LoginMessage(Console.readString());
        try {
            genericClient.sendMessage(loginMessage);
            genericClient.setLogged(genericClient.receiveBoolean());
        } catch(IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Failure while establishing socket connection.", e);
        }
        if (genericClient.isLogged()) {
            genericClient.setUsername(loginMessage.getUserName());
            Console.writeGreen("Login successful!");
        }
        else
            Console.writeRed("This username is already taken. Try again.");
    }

    public void logout() {
        if (! genericClient.isLogged()) {
            Console.writeRed("You're not even logged in -.-");
            return;
        }
        LogoutMessage logoutMessage = new LogoutMessage(genericClient.getUsername());
        try {
            genericClient.sendMessage(logoutMessage);
            boolean logoutSuccessful = genericClient.receiveBoolean();
            genericClient.setLogged(! logoutSuccessful);
            if (logoutSuccessful)
                Console.write("You just logged out!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
