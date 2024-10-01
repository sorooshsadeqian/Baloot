package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "provider")
public class ProviderEntity {

    public ProviderEntity() {}

    public ProviderEntity(Integer id, String name, String registryDate) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegistryDate() {
        return registryDate;
    }
    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "registry_date")
    private String registryDate;

}
