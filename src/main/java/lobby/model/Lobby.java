package lobby.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    public static Lobby lobbyInstance;
    private List<User> users;
    private List<Table> tables;

    private Lobby() {
        this.users = new ArrayList<User>();
        this.tables = new ArrayList<Table>();
    }

    public static Lobby getLobbyInstance() {
        if (lobbyInstance == null)
            lobbyInstance = new Lobby();
        return lobbyInstance;
    }
}
