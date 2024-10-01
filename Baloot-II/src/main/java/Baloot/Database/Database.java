package Baloot.Database;

import Baloot.Domain.Comment;
import Baloot.Domain.Commodity;
import Baloot.Domain.Provider;
import Baloot.Domain.User;
import Baloot.Exceptions.NotFoundException;

import java.util.List;

public interface Database {
    void addUser(User user);

    void addProvider(Provider provider);

    void addCommodity(Commodity commodity) throws NotFoundException;

    void addComment(Comment comment) throws NotFoundException;

    List<Commodity> getCommodities();

    List<Commodity> getCommoditiesByCategory(String category);

    Commodity getCommodityById(int id) throws NotFoundException;
    User getUserByUsername(String username) throws NotFoundException;

    Provider getProviderById(int id) throws NotFoundException;

    void flush();
}
