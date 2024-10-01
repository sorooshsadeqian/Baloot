package Baloot.Api.Requests;

public class GetBuyListRequest {
    GetBuyListRequest(){}

    public GetBuyListRequest(String usernname) {
        this.usernname = usernname;
    }

    public String usernname;
}
