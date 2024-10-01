package Baloot.Api;

import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Database.Database;
import Baloot.Database.DatabaseFactory;
import Baloot.Domain.*;
import Baloot.Exceptions.BadRequestException;
import Baloot.Exceptions.DuplicateItemException;
import Baloot.Exceptions.NotFoundException;
import Baloot.Utils.Errors;
import Baloot.Utils.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class APIImpl implements API {
    Database database;

    public APIImpl() {
        this.database = DatabaseFactory.getInstance();
    }

    public AddUserResponse addUser(AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        User user = new User(request.username, request.email, request.password, request.address, request.birthDate, request.credit);
        if (!Validator.isUsernameValid(request.username)) {
            response.success = false;
            response.errorMessage = Errors.INVALID_USERNAME;
            return response;
        }
        database.upsertUser(user);
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
        Commodity commodity = new Commodity(request.id, request.name, request.providerId, request.price, request.categories, request.rating, request.inStock, request.image);
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
        List<Commodity> commodities;
        if (!Objects.equals(request.searchedPhrase, "")) {
            commodities = database.getSortedCommodities(request.searchedPhrase);
        } else {
            commodities = database.getCommoditiesByName(request.name);
        }

        List<GetCommoditiesListResponse.Commodity> resCommodities = new ArrayList<>();
        for (Commodity c : commodities) {
            resCommodities.add(new GetCommoditiesListResponse.Commodity(
                    c.getId(), c.getName(), c.getProviderId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock(), c.getImage()
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
        float totalPrice = 0.0F;
        for (Commodity c : user.getBuyList()) {
            buyList.add(new GetBuyListResponse.Commodity(
                    c.getId(), c.getName(), c.getProviderId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock())
            );
            totalPrice += c.getPrice();
        }

        float discountPrice = totalPrice;
        if (user.getCurrentDiscountCode() != null) {
            discountPrice = totalPrice - totalPrice * user.getCurrentDiscountCode().getDiscount() / 100;
        }

        response.setDiscountPrice(discountPrice);
        response.setTotalPrice(totalPrice);
        response.setCommoditiesList(buyList);
        response.success = true;

        return response;
    }

    public GetCommodityByIdResponse getCommodityById(GetCommodityByIdRequest request) {
        GetCommodityByIdResponse response = new GetCommodityByIdResponse();

        try {
            Commodity commodity = database.getCommodityById(request.id);
            response.setFields(commodity);
            try {
                response.setComments(this.getCommodityComments(commodity));
                response.setSuggestedCommodities(this.getSuggestedCommodities(commodity));
            } catch (NotFoundException e) {
                response.success = false;
                response.errorMessage = Errors.USER_NOT_FOUND;
                return response;
            }
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }

        response.success = true;
        return response;
    }

    private List<GetCommodityByIdResponse.Comment> getCommodityComments(Commodity commodity) throws NotFoundException {
        List<GetCommodityByIdResponse.Comment> comments = new ArrayList<>();
        for (Comment comment : commodity.getComments()) {
            String username = database.getUserByEmail(comment.getUserEmail()).getUsername();
            comments.add(new GetCommodityByIdResponse.Comment(
                    username,
                    comment.getUserEmail(),
                    comment.getText(),
                    comment.getDate(),
                    comment.getLikes(),
                    comment.getDislikes()
            ));
        }
        return comments;
    }

    private List<GetCommodityByIdResponse.SuggestedCommodity> getSuggestedCommodities(Commodity commodity) {
        List<GetCommodityByIdResponse.SuggestedCommodity> suggestedCommodities = new ArrayList<>();
        List<Commodity> commodities = database.getCommodities();
        commodities.sort((c1, c2) -> {
            float c1Score = c1.getRating();
            float c2Score = c2.getRating();
            if (!Collections.disjoint(c1.getCategories(), commodity.getCategories())) {
                c1Score += 11;
            }
            if (!Collections.disjoint(c2.getCategories(), commodity.getCategories())) {
                c2Score += 11;
            }
            return (int) (c2Score - c1Score);
        });
        commodities.remove(commodity);
        commodities = commodities.stream().limit(5).collect(Collectors.toList());
        for (Commodity c : commodities) {
            suggestedCommodities.add(new GetCommodityByIdResponse.SuggestedCommodity(
                    c.getId(),
                    c.getName(),
                    c.getProviderId(),
                    c.getPrice(),
                    c.getCategories(),
                    c.getRating(),
                    c.getInStock()
            ));
        }
        return suggestedCommodities;
    }

    public GetCommoditiesByCategoryResponse getCommoditiesByCategory(GetCommoditiesByCategoryRequest request) {
        GetCommoditiesByCategoryResponse response = new GetCommoditiesByCategoryResponse();

        List<Commodity> commodities = database.getCommoditiesByCategory(request.category);

        List<GetCommoditiesByCategoryResponse.Commodity> resCommodities = new ArrayList<>();
        for (Commodity c : commodities) {
            resCommodities.add(new GetCommoditiesByCategoryResponse.Commodity(
                    c.getId(), c.getName(), c.getProviderId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock(), c.getImage()
            ));
        }
        response.setCommoditiesList(resCommodities);
        response.success = true;
        return response;
    }

    public AddCommentResponse addComment(AddCommentRequest request) {
        AddCommentResponse response = new AddCommentResponse();
        Comment comment = new Comment(request.userEmail, request.commodityId, request.text, request.date);
        try {
            database.addComment(comment);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
        response.success = true;
        return response;
    }

    public LoginUserResponse loginUser(LoginUserRequest request) {
        LoginUserResponse response = new LoginUserResponse();

        try {
            User user = database.getUserByUsername(request.username);
            if (!Objects.equals(user.getPassword(), request.password)) {
                response.success = false;
                response.errorMessage = Errors.WRONG_PASSWORD;
                return response;
            }
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        response.success = true;
        return response;
    }

    public AddCreditResponse addCredit(AddCreditRequest request) {
        AddCreditResponse response = new AddCreditResponse();

        if (!Validator.isCreditValid(request.credit)) {
            response.success = false;
            response.errorMessage = Errors.INVALID_CREDIT;
            return response;
        }

        try {
            User user = database.getUserByUsername(request.username);
            user.addCredit(request.credit);
            database.upsertUser(user);
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        response.success = true;
        return response;
    }

    public GetUserResponse getUser(GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        try {
            User user = database.getUserByUsername(request.username);
            response.credit = user.getCredit();
            response.username = user.getUsername();
            response.email = user.getEmail();
            response.birthDate = user.getBirthDate();
            response.address = user.getAddress();
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        response.success = true;
        return response;
    }

    public VoteCommentResponse voteComment(VoteCommentRequest request) {
        VoteCommentResponse response = new VoteCommentResponse();

        try {
            Commodity commodity = database.getCommodityById(request.commodityId);
            commodity.voteComment(request.commentEmail, request.voterUsername, request.like);

        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        } catch (DuplicateItemException e) {
            response.success = false;
            response.errorMessage = Errors.DUPLICATE_VOTE;
            return response;
        }

        response.success = true;
        return response;
    }

    public PayBuyListResponse payBuyList(PayBuyListRequest request) {
        PayBuyListResponse response = new PayBuyListResponse();

        try {
            User user = database.getUserByUsername(request.username);
            user.payBuyList();
        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        } catch (BadRequestException e) {
            response.success = false;
            response.errorMessage = Errors.INSUFFICIENT_CREDIT;
            return response;
        }

        response.success = true;
        return response;
    }

    public ApplyDiscountCodeResponse applyDiscountCode(ApplyDiscountCodeRequest request) {
        ApplyDiscountCodeResponse response = new ApplyDiscountCodeResponse();

        try {
            DiscountCode discountCode = database.getDiscountCode(request.discountCode);
            User user = database.getUserByUsername(request.username);

            user.applyDiscountCode(discountCode);

        } catch (NotFoundException e) {
            response.success = false;
            response.errorMessage = Errors.DISCOUNT_CODE_NOT_FOUND;
            return response;
        } catch (BadRequestException e) {
            response.success = false;
            response.errorMessage = Errors.ALREADY_USED_DISCOUNT_CODE;
            return response;
        }

        response.success = true;
        return response;
    }

    public AddDiscountCodeResponse addDiscountCode(AddDiscountCodeRequest request) {
        AddDiscountCodeResponse response = new AddDiscountCodeResponse();

        database.addDiscountCode(new DiscountCode(
                request.discountCode,
                request.discount
        ));

        response.success = true;
        return response;
    }

    public GetProviderResponse getProvider(GetProviderRequest request) throws NotFoundException {
        GetProviderResponse response = new GetProviderResponse();
        Provider provider = database.getProviderById(request.providerId);
        List<Commodity> commodities = database.getCommodities();
        List<Commodity> providerCommodities = new ArrayList<>();
        for (int i = 0; i < commodities.size(); i++) {
            if (Objects.equals(commodities.get(i).getProviderId(), request.providerId)) {
                providerCommodities.add(commodities.get(i));
            }
        }

        response.providerCommoditis = providerCommodities;
        response.setName(provider.getName());
        response.setRegistryDate(provider.getRegistryDate());
        response.setId(provider.getId());
        return response;
    }
}
