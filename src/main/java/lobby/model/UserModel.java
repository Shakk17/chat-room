package lobby.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String userName;

    public UserModel(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
