package Baloot.Api.Responses;

import Baloot.Domain.Commodity;

import java.util.List;

public class GetProviderResponse {

    public List<Commodity> providerCommoditis;
    int id;
    String name;
    String registryDate;

    public GetProviderResponse() {
    }

    ;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public GetProviderResponse(
            List<Commodity> providerCommoditis,
            String name,
            String registryDate,
            Integer id
    ) {
        this.name = name;
        this.id = id;
        this.registryDate = registryDate;
        this.providerCommoditis = providerCommoditis;
    }

}
