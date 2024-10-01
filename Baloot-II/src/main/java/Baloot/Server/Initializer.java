package Baloot.Server;

import Baloot.Api.API;
import Baloot.Api.Requests.AddCommentRequest;
import Baloot.Api.Requests.AddCommodityRequest;
import Baloot.Api.Requests.AddProviderRequest;
import Baloot.Api.Requests.AddUserRequest;
import Baloot.Exceptions.NotFoundException;
import Baloot.Utils.Consts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Initializer {

    API api;

    public Initializer(API api) {
        this.api = api;
    }

    public void initServerData() {
        try {
            initUsers();
            initProviders();
            initCommodities();
            initComments();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void initUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Consts.BALOOT_USERS_URL))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        List<AddUserRequest> users = new Gson().fromJson(response.body(),
                new TypeToken<List<AddUserRequest>>(){}.getType());
        for (AddUserRequest user: users) {
            api.addUser(user);
        }
    }

    void initProviders() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Consts.BALOOT_PROVIDERS_URL))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        List<AddProviderRequest> providers = new Gson().fromJson(response.body(),
                new TypeToken<List<AddProviderRequest>>(){}.getType());
        for (AddProviderRequest provider: providers) {
            api.addProvider(provider);
        }
    }

    void initCommodities() throws IOException, InterruptedException, NotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Consts.BALOOT_COMMODITIES_URL))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        List<AddCommodityRequest> commodities = new Gson().fromJson(response.body(),
                new TypeToken<List<AddCommodityRequest>>(){}.getType());
        for (AddCommodityRequest commodity: commodities) {
            api.addCommodity(commodity);
        }
    }

    void initComments() throws IOException, InterruptedException, NotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Consts.BALOOT_COMMENTS_URL))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        List<AddCommentRequest> comments = new Gson().fromJson(response.body(), new TypeToken<List<AddCommentRequest>>(){}.getType());
        for (AddCommentRequest comment: comments) {
            api.addComment(comment);
        }
    }

}
