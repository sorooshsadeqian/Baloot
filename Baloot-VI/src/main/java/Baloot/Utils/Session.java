package Baloot.Utils;

public class Session {
    private String username;
    private static Session instance;

    public static Session getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Session();
        return instance;
    }

    public void loginUser(String username) {
        this.username = username;
    }

    public void logoutUser() {
        this.username = null;
    }
    public String getLoggedInUsername() {
        return this.username;
    }

    public boolean userLoggedIn() {
        return username != null;
    }
}
