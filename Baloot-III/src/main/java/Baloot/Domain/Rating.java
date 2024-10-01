package Baloot.Domain;

public class Rating {
    public Rating(String username, int commodityId, float score) {
        this.username = username;
        this.commodityId = commodityId;
        this.score = score;
    }

    public int getCommodityId() {
        return this.commodityId;
    }

    public String getUsername() {
        return this.username;
    }

    public float getScore() {
        return this.score;
    }
    String username;
    int commodityId;
    float score;

}
