package Baloot.Api.Responses;

import java.util.List;

public class GetCommoditiesByCategoryResponse extends BaseResponse {

    public void setCommoditiesList(List<GetCommoditiesByCategoryResponse.Commodity> commoditiesList) {
        this.commoditiesListByCategory = commoditiesList;
    }

    public List<GetCommoditiesByCategoryResponse.Commodity> commoditiesListByCategory;

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
