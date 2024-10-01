package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "discount_code")
public class DiscountCodeEntity {

    public DiscountCodeEntity() {}

    public DiscountCodeEntity(String code, float discount) {
        this.code = code;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;

    @Column(name = "discount")
    float discount;

}
