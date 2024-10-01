package Baloot.Api.Requests;

public class AddProviderRequest {
    AddProviderRequest(){}

    public AddProviderRequest(Integer id, String name, String registryDate) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public Integer id;

    public String name;

    public String registryDate;
}
