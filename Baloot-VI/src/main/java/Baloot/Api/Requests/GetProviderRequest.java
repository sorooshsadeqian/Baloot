package Baloot.Api.Requests;

public class GetProviderRequest {

    GetProviderRequest() {}

    public  GetProviderRequest(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer providerId;
}
