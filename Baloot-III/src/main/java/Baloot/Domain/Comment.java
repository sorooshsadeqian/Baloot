package Baloot.Domain;

import Baloot.Exceptions.DuplicateItemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comment {
    public Comment(String userEmail, int commodityId, String text, String date) {
        this.userEmail = userEmail;
        this.commodityId = commodityId;
        this.text = text;
        this.date = date;
        this.likesUsernames = new ArrayList<>();
        this.dislikesUsernames = new ArrayList<>();
    }

    public int getCommodityId() {
        return this.commodityId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    public int getLikes() { return this.likesUsernames.size(); }
    public int getDislikes() { return this.dislikesUsernames.size(); }
    public String getText() { return this.text; }

    public String getDate() { return date; }

    public void addLike(String username) throws DuplicateItemException {
        if (likesUsernames.contains(username)) {
            throw new DuplicateItemException();
        }
        likesUsernames.add(username);
        dislikesUsernames.remove(username);
    }

    public void addDislike(String username) throws DuplicateItemException {
        if (dislikesUsernames.contains(username)) {
            throw new DuplicateItemException();
        }
        dislikesUsernames.add(username);
        likesUsernames.remove(username);
    }

    int id;
    String userEmail;
    int commodityId;
    String text;
    String date;
    List<String> likesUsernames;
    List<String> dislikesUsernames;

}
