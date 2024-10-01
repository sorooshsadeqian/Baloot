package Baloot.Server.Handlers;

import Baloot.Api.API;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class BaseHandler implements Handler {
    API api;
    public BaseHandler(API api) { this.api = api; }


    @Override
    public void handle(@NotNull Context context) throws Exception {
        return;
    }
}
