package Baloot.Api.Requests;

public class RateCommodityRequest {
    RateCommodityRequest() {}

    public RateCommodityRequest(String username, int commodityId, float score) {
        this.username = username;
        this.commodityId = commodityId;
        this.score = score;
    }

    public String username;
    public int commodityId;
    public float score;
}
