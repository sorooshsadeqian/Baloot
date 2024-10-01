package Baloot.Domain;

public class Provider {

    public Provider(int id, String name, String registryDate){
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    int id;
    String name;
    String registryDate;
}
