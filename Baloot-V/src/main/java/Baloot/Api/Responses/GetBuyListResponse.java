package Baloot.Api.Responses;

import java.util.List;

public class GetBuyListResponse extends BaseResponse {
    public GetBuyListResponse() {}

    public void setCommoditiesList(List<Commodity> commoditiesList) {
        this.commoditiesList = commoditiesList;
    }
    public List<Commodity> commoditiesList;

    public Float totalPrice;

    public Float discountPrice;
    public void setTotalPrice(Float totalPrice) { this.totalPrice = totalPrice; }
    public void setDiscountPrice(Float discountPrice) { this.discountPrice = discountPrice; }
    public static class Commodity {
        public Commodity(int id, String name, int providerId, float price, List<String> categories,
                         float rating, int inStock) {
            this.id = id;
            this.name = name;
            this.providerId = providerId;
            this.price = price;
            this.categories = categories;
            this.rating = rating;
            this.inStock = inStock;
        }
        public int id;
        public String name;
        public int providerId;
        public float price;
        public List<String> categories;
        public float rating;
        public int inStock;
    }

}
