package Baloot.Api.Requests;

import java.util.List;

public class AddCommodityRequest {

    AddCommodityRequest(){}

    public AddCommodityRequest(Integer id, String name, Integer providerId, Integer price,
                               List<String> categories, float rating, Integer inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public Integer id;
    public String name;
    public Integer providerId;
    public Integer price;
    public List<String> categories;
    public float rating;
    public Integer inStock;
}
