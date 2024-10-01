package Baloot.Api;

import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Dao.*;
import Baloot.Entities.*;
import Baloot.Utils.Errors;
import Baloot.Utils.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class APIImpl implements API {
    UserDao userDao;
    ProviderDao providerDao;
    CommodityDao commodityDao;
    RatingDao ratingDao;
    BuyListCommodityDao buyListCommodityDao;

    CommentDao commentDao;
    CommentVoteDao commentVoteDao;
    PurchasedCommodityDao purchasedCommodityDao;
    UsedDiscountCodeDao usedDiscountCodeDao;
    DiscountCodeDao discountCodeDao;

    public APIImpl() {
        this.userDao = new UserDao();
        this.providerDao = new ProviderDao();
        this.commodityDao = new CommodityDao();
        this.ratingDao = new RatingDao();
        this.buyListCommodityDao = new BuyListCommodityDao();
        this.commentDao = new CommentDao();
        this.commentVoteDao = new CommentVoteDao();
        this.purchasedCommodityDao = new PurchasedCommodityDao();
        this.usedDiscountCodeDao = new UsedDiscountCodeDao();
        this.discountCodeDao = new DiscountCodeDao();
    }

    public AddUserResponse addUser(AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        UserEntity user = new UserEntity(request.username, request.email, request.password,
                request.address, request.birthDate, request.credit);
        if (!Validator.isUsernameValid(request.username)) {
            response.success = false;
            response.errorMessage = Errors.INVALID_USERNAME;
            return response;
        }
        userDao.saveUser(user);
        response.success = true;
        return response;
    }

    public AddProviderResponse addProvider(AddProviderRequest request) {
        AddProviderResponse response = new AddProviderResponse();
        ProviderEntity provider = new ProviderEntity(request.id, request.name, request.registryDate);
        providerDao.saveProvider(provider);
        response.success = true;
        return response;
    }

    public AddCommodityResponse addCommodity(AddCommodityRequest request) {
        AddCommodityResponse response = new AddCommodityResponse();
        ProviderEntity provider = null;
        provider = providerDao.getProviderById(request.providerId);
        if (provider == null) {
            response.errorMessage = Errors.PROVIDER_NOT_FOUND;
            response.success = false;
            return response;
        }
        CommodityEntity commodity = new CommodityEntity(request.id, request.name, provider, request.price,
                request.categories, request.rating, request.inStock, request.image);
        commodityDao.saveCommodity(commodity);
        response.success = true;
        return response;
    }

    public GetCommoditiesListResponse getCommoditiesList(GetCommoditiesListRequest request) {
        GetCommoditiesListResponse response = new GetCommoditiesListResponse();
        List<CommodityEntity> commodities;

        if (Objects.equals(request.searchField, "name")) {
            commodities = commodityDao.searchCommoditiesByName(request.query);
        } else if (Objects.equals(request.searchField, "category")) {
            commodities = commodityDao.searchCommoditiesByCategory(request.query);
        } else {
            commodities = commodityDao.getCommodites();
        }

        List<GetCommoditiesListResponse.Commodity> resCommodities = new ArrayList<>();
        for (CommodityEntity c : commodities) {
            resCommodities.add(new GetCommoditiesListResponse.Commodity(
                    c.getId(), c.getName(), c.getProvider().getId(), c.getProvider().getName() , c.getPrice(),
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
        CommodityEntity commodity = commodityDao.getCommodityById(request.commodityId);
        if (commodity == null) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
        UserEntity user = userDao.getUserByUsername(request.username);
        RatingEntity rating = ratingDao.getRating(user.getId(), commodity.getId());
        if (rating != null) {
            rating.setScore(request.score);
        }
        else {
            rating = new RatingEntity(user, commodity, request.score);
        }
        ratingDao.saveRating(rating);
        response.success = true;
        return response;
    }

    public AddToBuyListResponse addToBuyList(AddToBuyListRequest request) {
        AddToBuyListResponse response = new AddToBuyListResponse();
        CommodityEntity commodity = commodityDao.getCommodityById(request.commodityId);
        if (commodity == null) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
        if (commodity.getInStock() < 1) {
            response.success = false;
            response.errorMessage = Errors.INSUFFICIENT_STOCK;
            return response;
        }

        UserEntity user = userDao.getUserByUsername(request.username);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        BuyListCommodityEntity buyListCommodity = new BuyListCommodityEntity(user, commodity);
        if (buyListCommodityDao.getBuyListCommodity(user.getId(), commodity.getId()) != null) {
            response.success = false;
            response.errorMessage = Errors.DUPLICATE_ITEM_IN_BUY_LIST;
            return response;
        }
        buyListCommodityDao.saveBuyListCommodity(buyListCommodity);
        commodity.setInStock(commodity.getInStock() - 1);
        commodityDao.updateCommodity(commodity);
        response.success = true;
        return response;
    }

    public RemoveFromBuyListResponse removeFromBuyList(RemoveFromBuyListRequest request) {
        RemoveFromBuyListResponse response = new RemoveFromBuyListResponse();
        UserEntity user = userDao.getUserByUsername(request.username);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        BuyListCommodityEntity buyListCommodity = buyListCommodityDao.getBuyListCommodity(user.getId(), request.commodityId);
        if (buyListCommodity == null) {
            response.success = false;
            response.errorMessage = Errors.ITEM_NOT_IN_BUY_LIST;
            return response;
        }

        buyListCommodityDao.deleteBuyListCommodity(buyListCommodity);

        CommodityEntity commodity = commodityDao.getCommodityById(request.commodityId);
        if (commodity == null) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
        commodity.setInStock(commodity.getInStock() + 1);
        commodityDao.updateCommodity(commodity);

        response.success = true;
        return response;
    }

    public GetBuyListResponse getBuyList(GetBuyListRequest request) {
        GetBuyListResponse response = new GetBuyListResponse();

        UserEntity user = userDao.getUserByUsername(request.usernname);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        List<GetBuyListResponse.Commodity> buyList = new ArrayList<>();
        float totalPrice = 0.0F;

        List<BuyListCommodityEntity> buyListCommodities = buyListCommodityDao.getUserBuyList(user.getId());
        for (BuyListCommodityEntity b : buyListCommodities) {
            CommodityEntity c = b.getCommodity();
            buyList.add(new GetBuyListResponse.Commodity(
                    c.getId(), c.getName(), c.getProvider().getId(), c.getPrice(),
                    c.getCategories(), c.getRating(), c.getInStock(), b.getCount())
            );
            totalPrice += c.getPrice();
        }

        float discountPrice = totalPrice;

        DiscountCodeEntity currentDiscountCode = user.getCurrentDiscountCode();
        if (currentDiscountCode != null) {
            discountPrice = totalPrice - totalPrice * currentDiscountCode.getDiscount() / 100;
        }

        response.setDiscountPrice(discountPrice);
        response.setTotalPrice(totalPrice);
        response.setCommoditiesList(buyList);
        response.success = true;

        return response;
    }

    public GetCommodityByIdResponse getCommodityById(GetCommodityByIdRequest request) {
        GetCommodityByIdResponse response = new GetCommodityByIdResponse();

        CommodityEntity commodity = commodityDao.getCommodityById(request.id);
        if (commodity == null) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }
        response.setFields(commodity);

        List<CommentEntity> comments = commentDao.getCommodityComments(commodity.getId());
        List<GetCommodityByIdResponse.Comment> responseComments = new ArrayList<>();
        for (CommentEntity comment: comments) {
            responseComments.add(new GetCommodityByIdResponse.Comment(
                    comment.getUser().getUsername(),  comment.getUser().getEmail(), comment.getText(),
                    comment.getDate(), commentVoteDao.getCommentLikes(comment.getId()),
                    commentVoteDao.getCommentDislikes(comment.getId())
            ));
        }
        response.setComments(responseComments);

        response.setSuggestedCommodities(this.getSuggestedCommodities(commodity));

        response.success = true;
        return response;
    }


    private List<GetCommodityByIdResponse.SuggestedCommodity> getSuggestedCommodities(CommodityEntity commodity) {
        List<GetCommodityByIdResponse.SuggestedCommodity> suggestedCommodities = new ArrayList<>();
        List<CommodityEntity> commodities = commodityDao.getCommodites();
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
        for (CommodityEntity c : commodities) {
            suggestedCommodities.add(new GetCommodityByIdResponse.SuggestedCommodity(
                    c.getId(),
                    c.getName(),
                    c.getProvider().getId(),
                    c.getPrice(),
                    c.getCategories(),
                    c.getRating(),
                    c.getInStock()
            ));
        }
        return suggestedCommodities;
    }

    public AddCommentResponse addComment(AddCommentRequest request) {
        AddCommentResponse response = new AddCommentResponse();

        UserEntity user = userDao.getUserByEmail(request.userEmail);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        CommodityEntity commodity = commodityDao.getCommodityById(request.commodityId);
        if (commodity == null) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }

        CommentEntity comment = new CommentEntity(user, commodity, request.text, request.date);
        commentDao.saveComment(comment);

        response.success = true;
        return response;
    }

    public LoginUserResponse loginUser(LoginUserRequest request) {
        LoginUserResponse response = new LoginUserResponse();

        UserEntity user = userDao.getUserByUsername(request.username);

        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        if (!Objects.equals(user.getPassword(), request.password)) {
            response.success = false;
            response.errorMessage = Errors.WRONG_PASSWORD;
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

        UserEntity user = userDao.getUserByUsername(request.username);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        user.setCredit(user.getCredit() + request.credit);
        userDao.updateUser(user);

        response.success = true;
        return response;
    }

    public GetUserResponse getUser(GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        UserEntity user = userDao.getUserByUsername(request.username);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        response = new GetUserResponse(user.getUsername(), user.getEmail(), user.getCredit(), user.getAddress(), user.getBirthDate());

        response.success = true;
        return response;
    }

    public VoteCommentResponse voteComment(VoteCommentRequest request) {
        VoteCommentResponse response = new VoteCommentResponse();

        CommodityEntity commodity = commodityDao.getCommodityById(request.commodityId);
        if (commodity == null) {
            response.success = false;
            response.errorMessage = Errors.COMMODITY_NOT_FOUND;
            return response;
        }

        UserEntity commentUser = userDao.getUserByEmail(request.commentEmail);
        if (commentUser == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        CommentEntity comment = commentDao.getComment(commentUser.getId(), commodity.getId());
        if (comment == null) {
            response.success = false;
            response.errorMessage = Errors.COMMENT_NOT_FOUND;
            return response;
        }

        UserEntity voteUser = userDao.getUserByUsername(request.voterUsername);
        if (voteUser == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        CommentVoteEntity currentVote = commentVoteDao.getCommentVote(voteUser.getId(), comment.getId());
        int vote = 1;
        if (!request.like) {
            vote = -1;
        }
        if (currentVote != null) {
            if (currentVote.getVote() == vote) {
                response.success = false;
                response.errorMessage = Errors.DUPLICATE_VOTE;
                return response;
            }
            currentVote.setVote(vote);
            commentVoteDao.updateCommentVote(currentVote);
        }
        else {
            CommentVoteEntity newVote = new CommentVoteEntity(voteUser, comment, vote);
            commentVoteDao.saveCommentVote(newVote);
        }


        response.success = true;
        return response;
    }

    public PayBuyListResponse payBuyList(PayBuyListRequest request) {
        PayBuyListResponse response = new PayBuyListResponse();


        UserEntity user = userDao.getUserByUsername(request.username);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        List<BuyListCommodityEntity> buyListCommodities = buyListCommodityDao.getUserBuyList(user.getId());

        float totalPrice = 0.0F;
        for (BuyListCommodityEntity b : buyListCommodities) {
            CommodityEntity c = b.getCommodity();
            totalPrice += c.getPrice();
        }
        float discountPrice = totalPrice;
        DiscountCodeEntity currentDiscountCode = user.getCurrentDiscountCode();
        if (currentDiscountCode != null) {
            discountPrice = totalPrice - totalPrice * currentDiscountCode.getDiscount() / 100;
        }

        if (discountPrice > user.getCredit()) {
            response.success = false;
            response.errorMessage = Errors.INSUFFICIENT_CREDIT;
            return response;
        }

        if (currentDiscountCode != null) {
            user.setCurrentDiscountCode(null);
            UsedDiscountCodeEntity usedDiscountCode = new UsedDiscountCodeEntity(user, currentDiscountCode);
            usedDiscountCodeDao.saveUsedDiscountCode(usedDiscountCode);
        }

        user.setCredit(user.getCredit() - discountPrice);
        userDao.updateUser(user);

        for (BuyListCommodityEntity buyListCommodity: buyListCommodities) {
            PurchasedCommodityEntity purchasedCommodity = new PurchasedCommodityEntity(
                    buyListCommodity.getUser(), buyListCommodity.getCommodity(), buyListCommodity.getCount());
            purchasedCommodityDao.savePurchasedCommodity(purchasedCommodity);
        }

        buyListCommodityDao.deleteUserBuyList(user.getId());

        response.success = true;
        return response;
    }

    public ApplyDiscountCodeResponse applyDiscountCode(ApplyDiscountCodeRequest request) {
        ApplyDiscountCodeResponse response = new ApplyDiscountCodeResponse();

        UserEntity user = userDao.getUserByUsername(request.username);
        if (user == null) {
            response.success = false;
            response.errorMessage = Errors.USER_NOT_FOUND;
            return response;
        }

        DiscountCodeEntity discountCode = discountCodeDao.getDiscountCodeByCode(request.discountCode);
        if (discountCode == null) {
            response.success = false;
            response.errorMessage = Errors.DISCOUNT_CODE_NOT_FOUND;
            return response;
        }

        UsedDiscountCodeEntity usedDiscountCode = usedDiscountCodeDao.getUsedDiscountCode(user.getId(), discountCode.getId());
        if (usedDiscountCode != null) {
            response.success = false;
            response.errorMessage = Errors.ALREADY_USED_DISCOUNT_CODE;
            return response;
        }

        user.setCurrentDiscountCode(discountCode);
        userDao.updateUser(user);

        response.success = true;
        return response;
    }

    public AddDiscountCodeResponse addDiscountCode(AddDiscountCodeRequest request) {
        AddDiscountCodeResponse response = new AddDiscountCodeResponse();

        DiscountCodeEntity discountCode = new DiscountCodeEntity(request.discountCode, request.discount);
        discountCodeDao.saveDiscountCode(discountCode);

        response.success = true;
        return response;
    }

    public GetProviderResponse getProvider(GetProviderRequest request) {
        GetProviderResponse response = new GetProviderResponse();

        ProviderEntity provider = providerDao.getProviderById(request.providerId);
        if (provider == null) {
            response.success = false;
            response.errorMessage = Errors.PROVIDER_NOT_FOUND;
            return response;
        }

        List<CommodityEntity> providerCommodities = commodityDao.getProviderCommodities(provider.getId());

        List<GetProviderResponse.Commodity> responseCommodities = new ArrayList<>();

        for (CommodityEntity c: providerCommodities) {
            responseCommodities.add(new GetProviderResponse.Commodity(
                    c.getId(), c.getName(), c.getProvider().getId(),
                    c.getProvider().getName(), c.getPrice(), c.getCategories(),
                    c.getRating(), c.getInStock(), c.getImage()));
        }

        response.setProviderCommodities(responseCommodities);
        response.setName(provider.getName());
        response.setRegistryDate(provider.getRegistryDate());
        response.setId(provider.getId());

        response.success = true;
        return response;
    }
}
