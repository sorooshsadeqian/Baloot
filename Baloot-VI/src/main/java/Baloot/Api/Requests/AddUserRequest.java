package Baloot.Api.Requests;

public class AddUserRequest {
    AddUserRequest(){}

    public AddUserRequest(String username, String email, String password,
                          String address, String birthDate, float credit) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.birthDate = birthDate;
        this.credit = credit;
    }

    public String username;
    public String email;
    public String password;
    public String address;
    public String birthDate;
    public float credit;
}
