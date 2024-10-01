package Baloot.Domain;

public class Comment {
    public Comment(String userEmail, int commodityId, String text, String date) {
        this.userEmail = userEmail;
        this.commodityId = commodityId;
        this.text = text;
        this.date = date;
    }

    public int getCommodityId() {
        return this.commodityId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }
    public String getText() { return this.text; }

    public String getDate() { return date; }

    int id;
    String userEmail;
    int commodityId;
    String text;
    String date;

}
