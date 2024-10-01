package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "used_discount_code",
        uniqueConstraints= @UniqueConstraint(columnNames={"user_id", "discount_code_id"})
)
public class UsedDiscountCodeEntity {

    public UsedDiscountCodeEntity() {}
    public UsedDiscountCodeEntity(UserEntity user, DiscountCodeEntity discountCode) {
        this.user = user;
        this.discountCode = discountCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public DiscountCodeEntity getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCodeEntity discountCode) {
        this.discountCode = discountCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = DiscountCodeEntity.class)
    @JoinColumn(name="discount_code_id")
    private DiscountCodeEntity discountCode;

}
