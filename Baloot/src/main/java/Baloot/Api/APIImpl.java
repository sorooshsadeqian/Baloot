package Baloot.Api;

import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Database.Database;
import Baloot.Domain.Commodity;
import Baloot.Domain.Provider;
import Baloot.Domain.Rating;
import Baloot.Domain.User;
import Baloot.Exceptions.DuplicateItemException;
import Baloot.Exceptions.NotFoundException;
import Baloot.Utils.Errors;
import Baloot.Utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class APIImpl implements API {
    Database database;
    public APIImpl(Database database) {
        this.database = database;
    }

    public AddUserResponse addUser(AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        User user = new User(request.username,request.email, request.password, request.address, request.birthDate, request.credit);
        if (!Validator.isUsernameValid(request.username)) {
            response.success = false;
            response.errorMessage = Errors.INVALID_USERNAME;
            return response;
        }
        database.addUser(user);
        response.success = true;
        return response;
    }

    public AddProviderResponse addProvider(AddProviderRequest request) {
        AddProviderResponse response = new AddProviderResponse();
        Provider provider = new Provider(request.id, request.name, request.registryDate);
        database.addProvider(provider);
        response.success = true;
        return response;
    }

    public AddCommodityResponse addCommodity(AddCommodityRequest request) {
        AddCommodityResponse response = new AddCommodityResponse();
        Commodity commodity = new Commodity(request.id, request.name, request.providerId, request.price, request.categories, request.rating, request.inStock);
        try {
            database.addCommodity(commodity);
        } catch (NotFoundException e) {
            response.errorMessage = Errors.PROVIDER_NOT_FOUND;
            response.success = false;
            return response;
        }
        response.success = true;
        return response;
    }

    public GetCommoditiesListResponse getCommoditiesList(GetCommoditiesListRequest request) {
        GetCommoditiesListResponse response = new GetCommoditiesListResponse();
        List<Commodity> commodities = database.getCommodities();
        List<GetCommoditiesListResponse.Commodity> resCommodities = new ArrayList<>();
        for (Commodity c: commodities) {
            resCommodities.add(new GetCommoditiesListResponse.Commodity(
                c.getId(), c.getName(), c.getProviderId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock()
            ));
        }
        response.setCommoditiesList(resCommodities);
        response.success = true;
        return response;
    }

    public RateCommodityResponse rateCommodity(RateCommodityRequest request) {
      RateCommodityResponse response = new RateCommodityResponse();
      if (!Validator.isScoreValid(request.score)) {
          response.success = false;
          response.errorMessage = Errors.INVALID_SCORE;
          return response;
      }
      Rating rating = new Rating(request.username, request.commodityId, request.score);
        try {
            Commodity commodity = database.getCommodityById(rating.getCommodityId());
            commodity.addRating(rating);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
      response.success = true;
      return response;
    }

    public AddToBuyListResponse addToBuyList(AddToBuyListRequest request) {
        AddToBuyListResponse response = new AddToBuyListResponse();
        Commodity commodity;
        try {
            commodity = database.getCommodityById(request.commodityId);
            if (commodity.getInStock() < 1) {
                response.success = false;
                response.errorMessage = Errors.INSUFFICIENT_STOCK;
            }
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
        try {
            User user = database.getUserByUsername(request.username);
            user.addToBuyList(commodity);
            commodity.decreaseStock();
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        } catch (DuplicateItemException e) {
            response.success = false;
            response.errorMessage = Errors.DUPLICATE_ITEM_IN_BUY_LIST;
            return response;
        }
        response.success = true;
        return response;
    }

    public RemoveFromBuyListResponse removeFromBuyList(RemoveFromBuyListRequest request) {
        RemoveFromBuyListResponse response = new RemoveFromBuyListResponse();
        User user;
        try {
            user = database.getUserByUsername(request.username);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        try {
            user.removeFromBuyList(request.commodityId);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.ITEM_NOT_IN_BUY_LIST;
            return response;
        }

        try {
            Commodity commodity = database.getCommodityById(request.commodityId);
            commodity.increaseStock();
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }

        response.success = true;
        return response;
    }

    public GetBuyListResponse getBuyList(GetBuyListRequest request) {
        GetBuyListResponse response = new GetBuyListResponse();
        User user;
        try {
            user = database.getUserByUsername(request.usernname);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        List<GetBuyListResponse.Commodity> buyList = new ArrayList<>();
        for (Commodity c: user.getBuyList()) {
            buyList.add(new GetBuyListResponse.Commodity(
                    c.getId(), c.getName(), c.getProviderId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock())
            );
        }

        response.setCommoditiesList(buyList);
        response.success = true;

        return response;
    }

    public GetCommodityByIdResponse getCommodityById(GetCommodityByIdRequest request) {
        GetCommodityByIdResponse response = new GetCommodityByIdResponse();

        try {
            Commodity commodity = database.getCommodityById(request.id);
            response.setFields(commodity);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }

        response.success = true;
        return  response;
    }

    public GetCommoditiesByCategoryResponse getCommoditiesByCategory(GetCommoditiesByCategoryRequest request) {
        GetCommoditiesByCategoryResponse response = new GetCommoditiesByCategoryResponse();

        List<Commodity> commodities = database.getCommoditiesByCategory(request.category);

        List<GetCommoditiesByCategoryResponse.Commodity> resCommodities = new ArrayList<>();
        for (Commodity c: commodities) {
            resCommodities.add(new GetCommoditiesByCategoryResponse.Commodity(
                    c.getId(), c.getName(), c.getProviderId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock()
            ));
        }
        response.setCommoditiesList(resCommodities);
        response.success = true;
        return response;
    }
}
