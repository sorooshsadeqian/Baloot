package Baloot.Database;

import Baloot.Domain.Commodity;
import Baloot.Domain.Provider;
import Baloot.Domain.Rating;
import Baloot.Domain.User;
import Baloot.Exceptions.NotFoundException;

import java.util.List;
import java.util.Map;

public interface Database {
    void addUser(User user);

    void addProvider(Provider provider);

    void addCommodity(Commodity commodity) throws NotFoundException;

    List<Commodity> getCommodities();

    List<Commodity> getCommoditiesByCategory(String category);

    Commodity getCommodityById(int id) throws NotFoundException;
    User getUserByUsername(String username) throws NotFoundException;

    Provider getProviderById(int id) throws NotFoundException;

    void flush();
}
