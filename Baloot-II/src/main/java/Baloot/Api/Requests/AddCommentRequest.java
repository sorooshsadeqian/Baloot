package Baloot.Api.Requests;

import java.util.List;

public class AddCommentRequest {

    AddCommentRequest(){}

    public AddCommentRequest(String userEmail, Integer commodityId, String text, String date) {
        this.userEmail = userEmail;
        this.commodityId = commodityId;
        this.text = text;
        this.date = date;
    }

    public String userEmail;
    public Integer commodityId;
    public String text;
    public String date;
}
