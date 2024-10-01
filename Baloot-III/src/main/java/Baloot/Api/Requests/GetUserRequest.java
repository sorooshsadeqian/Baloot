package Baloot.Api.Requests;

public class GetUserRequest {
    GetUserRequest(){}

    public GetUserRequest(String username) {
        this.username = username;
    }

    public String username;
}
