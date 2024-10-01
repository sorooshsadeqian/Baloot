package Baloot;


import Baloot.Api.API;
import Baloot.Api.APIImpl;
import Baloot.Database.Database;
import Baloot.Database.InMemoryDatabase;
import Baloot.Server.Initializer;
import Baloot.Server.Server;

public class Main {
    public static void main(String[] args) {
        Database database = new InMemoryDatabase();
        API api = new APIImpl(database);
        Initializer initializer = new Initializer(api);
        initializer.initServerData();
        Server server = new Server(api);
        server.runServer();
    }
}