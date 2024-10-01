package Baloot.Api.Requests;

public class ApplyDiscountCodeRequest {

    ApplyDiscountCodeRequest(){}

    public ApplyDiscountCodeRequest(String username, String discountCode) {
        this.username = username;
        this.discountCode = discountCode;
    }

    public String username;
    public String discountCode;
}
