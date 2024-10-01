package Baloot.Api.Responses;

import Baloot.Entities.CommodityEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCommodityByIdResponse extends BaseResponse {

    public void setFields(CommodityEntity commodity) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.price = commodity.getPrice();
        this.inStock = commodity.getInStock();
        this.categories = commodity.getCategories();
        this.rating = commodity.getRating();
        this.providerId = commodity.getProvider().getId();
        this.providerName = commodity.getProvider().getName();
        this.image = commodity.getImage();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public void setSuggestedCommodities(List<SuggestedCommodity> suggestedCommodities) { this.suggestedCommodities = suggestedCommodities;}

    public static class Comment {
        public Comment(String username, String email, String text, String date, Integer likes, Integer dislikes) {
            this.username = username;
            this.email = email;
            this.text = text;
            this.date = date;
            this.likes = likes;
            this.dislikes = dislikes;
        }
        public String username;
        public String email;
        public String text;
        public String date;
        public Integer likes;
        public Integer dislikes;
    }

    public static class SuggestedCommodity {
        public SuggestedCommodity(Integer id, String name, Integer providerId, Float price, List<String> categories, Float rating, Integer inStock)
        {
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
        public Float price;
        public List<String> categories;
        public Float rating;
        public Integer inStock;
    }

    public int id;

    public String name;
    public int providerId;
    public String providerName;
    public float price;
    public List<String> categories;
    public float rating;
    public int inStock;
    public List<Comment> comments;
    public List<SuggestedCommodity> suggestedCommodities;
    public String image;


}
