package Baloot.Api.Requests;

public class VoteCommentRequest {

    VoteCommentRequest(){}

    public VoteCommentRequest(String commentEmail, String voterUsername, Integer commodityId, Boolean like) {
        this.commentEmail = commentEmail;
        this.voterUsername = voterUsername;
        this.commodityId = commodityId;
        this.like = like;
    }

    public String commentEmail;
    public String voterUsername;
    public Integer commodityId;
    public Boolean like;
}
