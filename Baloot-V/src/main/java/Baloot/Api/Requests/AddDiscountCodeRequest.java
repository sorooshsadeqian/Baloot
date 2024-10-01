package Baloot.Api.Requests;

public class AddDiscountCodeRequest {

    AddDiscountCodeRequest(){}

    public AddDiscountCodeRequest(String discountCode, Float discounte) {
        this.discountCode = discountCode;
        this.discount = discounte;
    }

    public String discountCode;

    public Float discount;
}
