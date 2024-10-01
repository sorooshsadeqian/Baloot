package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "buy_list_commodity",
        uniqueConstraints= @UniqueConstraint(columnNames={"user_id", "commodity_id"})
)
public class BuyListCommodityEntity {

    public BuyListCommodityEntity() {}

    public BuyListCommodityEntity(UserEntity user, CommodityEntity commodity) {
        this.user = user;
        this.commodity = commodity;
        this.count = 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CommodityEntity getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityEntity commodity) {
        this.commodity = commodity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = CommodityEntity.class)
    private CommodityEntity commodity;

    @Column(name = "count")
    private Integer count;

}
