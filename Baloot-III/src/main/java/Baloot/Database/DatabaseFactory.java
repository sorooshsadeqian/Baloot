package Baloot.Database;

public class DatabaseFactory {
    private static InMemoryDatabase instance;

    public static Database getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new InMemoryDatabase();
        return instance;
    }
}
