package Baloot.Database;

import Baloot.Domain.*;
import Baloot.Exceptions.NotFoundException;

import java.util.List;

public interface Database {
    void upsertUser(User user);

    void addProvider(Provider provider);

    void addCommodity(Commodity commodity) throws NotFoundException;

    void addComment(Comment comment) throws NotFoundException;

    void addDiscountCode(DiscountCode discountCode);

    List<Commodity> getCommodities();

    List<DiscountCode> getDiscountCodes();

    List<Commodity> getCommoditiesByCategory(String category);

    List<Commodity> getCommoditiesByName(String name);

    List<Commodity> getSortedCommodities(String phrase);

    Commodity getCommodityById(int id) throws NotFoundException;
    User getUserByUsername(String username) throws NotFoundException;

    User getUserByEmail(String email) throws NotFoundException;

    Provider getProviderById(int id) throws NotFoundException;

    DiscountCode getDiscountCode(String code) throws NotFoundException;

    void flush();
}
