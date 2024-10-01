package Baloot.Database;

import Baloot.Domain.Comment;
import Baloot.Domain.Commodity;
import Baloot.Domain.Provider;
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

    int commentIdCounter = 0;


    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void addProvider(Provider provider) {
        providers.put(provider.getId(), provider);
    }

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
