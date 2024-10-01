package Baloot.Domain;

import Baloot.Exceptions.DuplicateItemException;

import java.util.ArrayList;
import java.util.List;

public class DiscountCode {
    public DiscountCode(String code, float discount) {
        this.code = code;
        this.discount = discount;
    }

    public float getDiscount() { return discount; }

    public String getCode() { return code; }
    String code;

    float discount;

}
