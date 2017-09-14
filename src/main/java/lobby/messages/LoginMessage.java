package lobby.messages;

import java.io.Serializable;

public class LoginMessage implements Serializable {
    private static final long serialVersionUID = -4520332386402808781L;

    private String username;

    public LoginMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
