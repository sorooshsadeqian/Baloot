package Baloot.Api.Requests;

public class LoginUserRequest {
    LoginUserRequest(){}
    public LoginUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username;
    public String password;
}
