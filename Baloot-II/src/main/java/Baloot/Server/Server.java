package Baloot.Server;

import Baloot.Api.API;
import Baloot.Server.Handlers.CommoditiesHandler;
import Baloot.Server.Handlers.CommodityHandler;
import io.javalin.Javalin;

public class Server {
    API api;

    public Server(API api) {
        this.api = api;
    }

    public void runServer() {
        Javalin app = Javalin.create().start(8080);
        app.get("/commodities", new CommoditiesHandler(api));
        app.get("/commodities/{commodity_id}", new CommodityHandler(api));
        app.post("/rateCommodity", context -> context.result(context.body()));
    }
}
