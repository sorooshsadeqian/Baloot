package Baloot.Api.Responses;

import java.util.List;

public class GetCommoditiesListResponse extends BaseResponse {
    public GetCommoditiesListResponse() {}

    public void setCommoditiesList(List<Commodity> commoditiesList) {
        this.commoditiesList = commoditiesList;
    }

    public List<Commodity> commoditiesList;

    public static class Commodity {
        public Commodity(int id, String name, int providerId, float price, List<String> categories,
                         float rating, int inStock, String image) {
            this.id = id;
            this.name = name;
            this.providerId = providerId;
            this.price = price;
            this.categories = categories;
            this.rating = rating;
            this.inStock = inStock;
            this.image = image;
        }
        public int id;
        public String name;
        public int providerId;
        public float price;
        public List<String> categories;
        public float rating;
        public int inStock;
        public String image;
    }

}
