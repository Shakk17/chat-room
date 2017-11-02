package lobby.model;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lobby.Console;
import lobby.messages.changes.AddUserChange;
import lobby.messages.changes.ModelChange;
import lobby.Observable;
import lobby.messages.changes.RemoveUserChange;
import lobby.server.Server;

import java.util.HashMap;
import java.util.Map;

public class LobbyModel extends Observable<ModelChange> {

    private Map<String, UserModel> users;

    public LobbyModel() {
        this.users = new HashMap<>();
    }

    public void addUser(Integer ID, String userName) {
        UserModel userModel = new UserModel(userName);
        users.put(userName, userModel);
        Server.getServerInstance().getUserNames().put(ID, userName);
        Console.writeGreen(userName +" just logged in!");
        this.notifyObservers(new AddUserChange(userModel));
    }

    public void removeUser(Integer ID) {
        String userName = Server.getServerInstance().getUserNames().get(ID);
        users.remove(userName);
        Console.writeGreen(userName +" just logged out!");
        this.notifyObservers(new RemoveUserChange(new UserModel(userName)));
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

    public Map<String, UserModel> getUsers() {
        return users;
    }
}
