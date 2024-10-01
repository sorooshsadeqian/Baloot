package Baloot.Database;

import Baloot.Domain.*;
import Baloot.Exceptions.NotFoundException;

import java.util.*;

public class InMemoryDatabase implements Database {
    Map<String, User> users = new HashMap<>();
    Map<Integer, Provider> providers = new HashMap<>();
    Map<Integer, Commodity> commodities = new HashMap<>();

    List<DiscountCode> discountCodes = new ArrayList<>();

    int commentIdCounter = 0;


    public void upsertUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void addProvider(Provider provider) {
        providers.put(provider.getId(), provider);
    }

    public void addDiscountCode(DiscountCode discountCode) { discountCodes.add(discountCode); }

    public void addCommodity(Commodity commodity) throws NotFoundException {
        if (!this.providers.containsKey(commodity.getProviderId())) {
            throw new NotFoundException();
        }
        commodities.put(commodity.getId(), commodity);
    }

    public void addComment(Comment comment) throws NotFoundException {
        comment.setId(this.commentIdCounter++);
        Commodity commodity = getCommodityById(comment.getCommodityId());
        commodity.addComment(comment);
    }

    public Commodity getCommodityById(int id) throws NotFoundException {
        if (!commodities.containsKey(id)) {
            throw new NotFoundException();
        }
        return commodities.get(id);
    }

    public User getUserByUsername(String username) throws NotFoundException {
        if (!users.containsKey(username)) {
            throw new NotFoundException();
        }
        return users.get(username);
    }

    public User getUserByEmail(String email) throws NotFoundException {
        for (User user: users.values()) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        throw new NotFoundException();
    }

    public List<Commodity> getCommodities() {
        List<Commodity> list = new ArrayList<>(commodities.values());
        return list;
    }

    public List<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public List<Commodity> getCommoditiesByCategory(String category) {
        List<Commodity> list = new ArrayList<>(commodities.values());
        return list.stream().filter(c -> c.getCategories().contains(category)).toList();
    }

    public List<Commodity> getCommoditiesByName(String name) {
        if (Objects.equals(name, "")) {
            return this.getCommodities();
        }
        List<Commodity> list = new ArrayList<>(commodities.values());
        return list.stream().filter(c -> c.getName().contains(name)).toList();
    }

    public List<Commodity> getSortedCommodities(String phrase) {
        if (Objects.equals(phrase, "Price")) {
            List<Commodity> list = new ArrayList<>(commodities.values());
            list.sort(Comparator.comparing(Commodity::getPrice).reversed());
            return list;
        } else {
            return this.getCommodities();
        }
    }


    public Provider getProviderById(int id) throws NotFoundException {
        if (!providers.containsKey(id)) {
            throw new NotFoundException();
        }
        return providers.get(id);
    }

    public DiscountCode getDiscountCode(String code) throws NotFoundException {
        for (DiscountCode discountCode: discountCodes) {
            if (Objects.equals(discountCode.getCode(), code)) {
                return discountCode;
            }
        }
        throw new NotFoundException();
    }

    public void flush() {
        users.clear();
        providers.clear();
        commodities.clear();
    }
}
