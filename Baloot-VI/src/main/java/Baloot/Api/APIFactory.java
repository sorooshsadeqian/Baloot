package Baloot.Api;

public class APIFactory {
    private static APIImpl instance;

    public static API getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new APIImpl();
        return instance;
    }
}
