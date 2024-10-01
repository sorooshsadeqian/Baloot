package Baloot.Api.Requests;

public class AddCreditRequest {
    AddCreditRequest() {}

    public AddCreditRequest(String username, Float credit) {
        this.username = username;
        this.credit = credit;
    }

    public String username;
    public Float credit;
}
