package Baloot.Api.Requests;

public class RemoveFromBuyListRequest {
    RemoveFromBuyListRequest(){}

    public RemoveFromBuyListRequest(String username, Integer commodityId) {
        this.username = username;
        this.commodityId = commodityId;
    }

    public String username;

    public Integer commodityId;
}
