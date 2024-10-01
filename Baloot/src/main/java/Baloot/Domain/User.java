package Baloot.Domain;

import Baloot.Exceptions.DuplicateItemException;
import Baloot.Exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    public User(String username, String email, String password, String address, String birthDate, float credit) {
        this.username = username;
        this.address = address;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.credit = credit;
        this.buyList = new HashMap<>();
    }
    String username;
    String email;
    String password;
    String address;
    String birthDate;
    float credit;

    Map<Integer, Commodity> buyList;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public float getCredit() {
        return credit;
    }

    public void addToBuyList(Commodity commodity) throws DuplicateItemException {
        if (this.buyList.containsKey(commodity.getId())) {
            throw new DuplicateItemException();
        }
        this.buyList.put(commodity.getId(), commodity);
    }

    public void removeFromBuyList(int commodityId) throws NotFoundException {
        if (!this.buyList.containsKey(commodityId)) {
            throw new NotFoundException();
        }
        this.buyList.remove(commodityId);
    }

    public List<Commodity> getBuyList() {
        return new ArrayList<>(this.buyList.values());
    }

}
