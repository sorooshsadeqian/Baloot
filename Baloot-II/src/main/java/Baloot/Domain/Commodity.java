package Baloot.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commodity {

    public Commodity(int id, String name, int providerId, float price, List<String> categories, float rating, int inStock){
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.initialRating = rating;
        this.inStock = inStock;
        this.ratings = new HashMap<>();
        this.comments = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public int getProviderId() { return this.providerId; }

    public String getName() { return this.name; }
    public float getPrice() { return this.price; }
    public List<String> getCategories() { return this.categories; }

    public float getRating() { return this.rating; }
    public int getInStock() { return this.inStock; }

    public List<Comment> getComments() { return this.comments; }

    public void addRating(Rating rating) {
        this.ratings.put(rating.getUsername(), rating.getScore());
        float sum = this.ratings.values().stream().reduce((float) 0, Float::sum);
        sum += this.initialRating;
        this.rating = sum / this.ratings.size() + 1;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void decreaseStock() {
        this.inStock =- 1;
    }

    public void increaseStock() {
        this.inStock =+ 1;
    }

    int id;
    String name;
    int providerId;
    float price;
    List<String> categories;
    float rating;
    float initialRating;
    int inStock;

    Map<String, Float> ratings;

    List<Comment> comments;

}
