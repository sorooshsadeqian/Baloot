package Baloot.Api.Requests;

public class GetCommoditiesListRequest {

    public String name;
    public String searchedPhrase;


    public GetCommoditiesListRequest(String name, String searchedPhrase) {
        this.name = name;
        this.searchedPhrase = searchedPhrase;
    }
}
