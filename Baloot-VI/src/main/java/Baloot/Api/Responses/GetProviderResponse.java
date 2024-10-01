package Baloot.Api.Responses;

import java.util.List;

public class GetProviderResponse extends BaseResponse{

    public List<Commodity> providerCommodities;
    int id;
    String name;
    String registryDate;

    public GetProviderResponse() {
    }

    public GetProviderResponse(
            List<Commodity> providerCommodities,
            String name,
            String registryDate,
            Integer id
    ) {
        this.name = name;
        this.id = id;
        this.registryDate = registryDate;
        this.providerCommodities = providerCommodities;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public void setProviderCommodities(List<Commodity> providerCommodities) { this.providerCommodities = providerCommodities; }

    public static class Commodity {
        public Commodity(int id, String name, int providerId, String providerName, float price, List<String> categories,
                         float rating, int inStock, String image) {
            this.id = id;
            this.name = name;
            this.providerId = providerId;
            this.providerName = providerName;
            this.price = price;
            this.categories = categories;
            this.rating = rating;
            this.inStock = inStock;
            this.image = image;
        }
        public int id;
        public String name;
        public int providerId;
        public String providerName;
        public float price;
        public List<String> categories;
        public float rating;
        public int inStock;
        public String image;
    }

}
