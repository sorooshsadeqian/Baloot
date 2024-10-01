package Baloot.Api.Requests;

public class AddToBuyListRequest {
    AddToBuyListRequest(){}
    public AddToBuyListRequest(String username, Integer commodityId) {
        this.username = username;
        this.commodityId = commodityId;
    }
    public String username;

    public Integer commodityId;
}
