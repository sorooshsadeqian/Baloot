package Baloot.Api.Requests;

public class GetCommoditiesByCategoryRequest {
    GetCommoditiesByCategoryRequest() {}

    public GetCommoditiesByCategoryRequest(String category) {
        this.category = category;
    }

    public String category;
}
