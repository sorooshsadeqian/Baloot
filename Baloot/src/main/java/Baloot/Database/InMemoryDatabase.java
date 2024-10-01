package Baloot.Database;

import Baloot.Domain.Commodity;
import Baloot.Domain.Provider;
import Baloot.Domain.Rating;
import Baloot.Domain.User;
import Baloot.Exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDatabase implements Database {
    Map<String, User> users = new HashMap<>();
    Map<Integer, Provider> providers = new HashMap<>();
    Map<Integer, Commodity> commodities = new HashMap<>();


    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void addProvider(Provider provider) {
        providers.put(provider.getId(), provider);
        System.out.println(providers);
    }

    public void addCommodity(Commodity commodity) throws NotFoundException {
        if (!this.providers.containsKey(commodity.getProviderId())) {
            throw new NotFoundException();
        }
        commodities.put(commodity.getId(), commodity);
        System.out.println(commodities);
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

    public List<Commodity> getCommodities() {
        List<Commodity> list = new ArrayList<>(commodities.values());
        return list;
    }

    public List<Commodity> getCommoditiesByCategory(String category) {
        List<Commodity> list = new ArrayList<>(commodities.values());
        return list.stream().filter(c -> c.getCategories().contains(category)).toList();
    }

    public Provider getProviderById(int id) throws NotFoundException {
        if (!providers.containsKey(id)) {
            throw new NotFoundException();
        }
        return providers.get(id);
    }

    public void flush() {
        users.clear();
        providers.clear();
        commodities.clear();
    }
}

//    addUser {"username": "soroosh", "password": "pass", "email": "email e man", "birthDate": "1379-10", "address": "Ad", "credit":100}
//addProvider {"id": 1, "name": "provider1", "registeryDate": "1345-59"}
//addCommodity {"id": 1, "name": "kala", "providerId": 4, "price": 3500, "categories":["apartment-rent", "apartment-sell"], "rating": 10, "inStock": 50}