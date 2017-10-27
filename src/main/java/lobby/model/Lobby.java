package lobby.model;

import lobby.Console;
import lobby.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby extends Observable {

    private static Lobby lobbyInstance;
    private Map<String, User> users;
    private List<Table> tables;

    private Lobby() {
        this.users = new HashMap<>();
        this.tables = new ArrayList<>();
    }

    public void addUser(String userName) {
        users.put(userName, new User(userName));
        this.notifyObservers();
    }

    public void removeUser(String userName) {
        users.remove(userName);
        this.notifyObservers();
    }

    public void printUsers() {
        if (users.isEmpty()) {
            Console.writeBlue("The lobby is empty!");
            return;
        }
        Console.writeBlue(users.size() + " users logged in: ");
        StringBuilder userNames = new StringBuilder();
        for (String userName : users.keySet())
            userNames.append(userName).append(", ");
        userNames.delete(userNames.length() - 2, userNames.length());
        Console.write(userNames.toString());
    }

    public static Lobby getLobbyInstance() {
        if (lobbyInstance == null)
            lobbyInstance = new Lobby();
        return lobbyInstance;
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
