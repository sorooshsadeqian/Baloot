package Baloot.Api.Responses;

import Baloot.Domain.Commodity;

import java.util.List;

public class GetCommodityByIdResponse extends BaseResponse {

    public void setFields(Commodity commodity) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.price = commodity.getPrice();
        this.inStock = commodity.getInStock();
        this.categories = commodity.getCategories();
        this.rating = commodity.getRating();
        this.providerId = commodity.getProviderId();
    }

    public int id;

    public String name;
    public int providerId;
    public float price;
    public List<String> categories;
    public float rating;
    public int inStock;


}
