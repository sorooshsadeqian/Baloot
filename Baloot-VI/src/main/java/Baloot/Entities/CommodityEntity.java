package Baloot.Entities;

import Baloot.Dao.RatingDao;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "commodity")
public class CommodityEntity {
    public CommodityEntity() {

    }


    @Id
    @Column(name = "id")
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public float getRating() {
        List<RatingEntity> ratings = new RatingDao().getCommodityRatings(this.id);
        float sum = initialRating;
        for (RatingEntity rating: ratings) {
            sum += rating.getScore();
        }
        return sum / (ratings.size() + 1);
    }

    public float getInitialRating() {
        return initialRating;
    }

    public void setInitialRating(float initialRating) {
        this.initialRating = initialRating;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CommodityEntity(int id, String name, ProviderEntity provider, float price, List<String> categories, float initialRating, int inStock, String image) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.price = price;
        this.categories = categories;
        this.initialRating = initialRating;
        this.inStock = inStock;
        this.image = image;
    }

    @Column(name = "name")
    String name;

    @ManyToOne(targetEntity = ProviderEntity.class, optional = false)
    ProviderEntity provider;

    @Column(name = "price")
    float price;

    @Column(name = "category")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "commodity_category")
    List<String> categories;

    @Column(name = "initial_rating")
    float initialRating;

    @Column(name = "in_stock")
    int inStock;

    @Column(name = "image")
    String image;

}
