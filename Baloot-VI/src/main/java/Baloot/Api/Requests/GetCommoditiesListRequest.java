package Baloot.Api.Requests;

public class GetCommoditiesListRequest {

    public String searchField;
    public String query;
    public String sortKey;

    public GetCommoditiesListRequest(String query, String searchField, String sortKey) {
        this.searchField = searchField;
        this.query = query;
        this.sortKey = sortKey;
    }
}
