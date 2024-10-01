package Baloot.Api.Responses;

import java.util.List;

public class GetUserResponse extends BaseResponse {
    public GetUserResponse() {}

    public GetUserResponse(String username, String email, Float credit, String address, String birthDate) {
        this.username = username;
        this.email = email;
        this.credit = credit;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String username;

    public String email;

    public Float credit;

    public String address;
    public String birthDate;

}
