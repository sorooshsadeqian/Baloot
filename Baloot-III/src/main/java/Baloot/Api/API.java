package Baloot.Api;

import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;

public interface API {

    AddUserResponse addUser(AddUserRequest request);

    AddProviderResponse addProvider(AddProviderRequest request);
    AddCommodityResponse addCommodity(AddCommodityRequest request);

    GetCommoditiesListResponse getCommoditiesList(GetCommoditiesListRequest request);

    RateCommodityResponse rateCommodity(RateCommodityRequest request);

    AddToBuyListResponse addToBuyList(AddToBuyListRequest request);
    RemoveFromBuyListResponse removeFromBuyList(RemoveFromBuyListRequest request);

    GetBuyListResponse getBuyList(GetBuyListRequest request);

    GetCommodityByIdResponse getCommodityById(GetCommodityByIdRequest request);
    GetCommoditiesByCategoryResponse getCommoditiesByCategory(GetCommoditiesByCategoryRequest request);

    AddCommentResponse addComment(AddCommentRequest request);

    LoginUserResponse loginUser(LoginUserRequest request);

    AddCreditResponse addCredit(AddCreditRequest request);

    GetUserResponse getUser(GetUserRequest request);

    VoteCommentResponse voteComment(VoteCommentRequest request);

    PayBuyListResponse payBuyList(PayBuyListRequest request);

    ApplyDiscountCodeResponse applyDiscountCode(ApplyDiscountCodeRequest request);

    AddDiscountCodeResponse addDiscountCode(AddDiscountCodeRequest request);
}
