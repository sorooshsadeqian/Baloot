package Baloot.CLI;

import Baloot.Api.API;
import Baloot.Api.APIImpl;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.AddUserResponse;
import Baloot.Api.Responses.BaseResponse;
import Baloot.Api.Responses.GetCommoditiesListResponse;
import Baloot.Utils.Errors;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import static java.util.Map.entry;

public class CommandLine {
    public CommandLine(API api) {
        this.api = api;
    }
    API api;

    class Output {
        public Output(boolean success, String data) {
            this.success = success;
            this.data = data;
        }
        boolean success;

        String data;
    }

    public String runCommand(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        String command = tokenizer.nextToken();
        Gson gson = new Gson();
        String data = "";
        try {
            data = tokenizer.nextToken("");
        }
        catch (NoSuchElementException ignored) {}

        BaseResponse response = new BaseResponse();
        switch (command) {
            case "addUser" -> {
                AddUserRequest addUserRequest = gson.fromJson(data, AddUserRequest.class);
                response = api.addUser(addUserRequest);
            }
            case "addProvider" -> {
                AddProviderRequest addProviderRequest = gson.fromJson(data, AddProviderRequest.class);
                response = api.addProvider(addProviderRequest);
            }
            case "addCommodity" -> {
                AddCommodityRequest addCommodityRequest = gson.fromJson(data, AddCommodityRequest.class);
                response = api.addCommodity(addCommodityRequest);
            }
            case "getCommoditiesList" -> {
                GetCommoditiesListRequest getCommoditiesListRequest = new GetCommoditiesListRequest();
                response = api.getCommoditiesList(getCommoditiesListRequest);
            }
            case "rateCommodity" -> {
                RateCommodityRequest rateCommodityRequest = gson.fromJson(data, RateCommodityRequest.class);
                response = api.rateCommodity(rateCommodityRequest);
            }
            case "addToBuyList" -> {
                AddToBuyListRequest addToBuyListRequest = gson.fromJson(data, AddToBuyListRequest.class);
                response = api.addToBuyList(addToBuyListRequest);
            }
            case "removeFromBuyList" -> {
                RemoveFromBuyListRequest removeFromBuyListRequest = gson.fromJson(data, RemoveFromBuyListRequest.class);
                response = api.removeFromBuyList(removeFromBuyListRequest);
            }
            case "getCommodityById" -> {
                GetCommodityByIdRequest getComoodityByIdRequest = gson.fromJson(data, GetCommodityByIdRequest.class);
                response = api.getCommodityById(getComoodityByIdRequest);
            }
            case "getCommoditiesByCategory" -> {
                GetCommoditiesByCategoryRequest getCommoditiesByCategoryRequest = gson.fromJson(data, GetCommoditiesByCategoryRequest.class);
                response = api.getCommoditiesByCategory(getCommoditiesByCategoryRequest);
            }
            default -> {
                response.success = false;
                response.errorMessage = Errors.INVALID_COMMAND;
            }
        }

        Output output;
        if (response.success) {
            output = new Output(true, gson.toJson(response));
        }
        else {
            output = new Output(false, response.errorMessage);
        }
        return gson.toJson(output);
    }
}

