package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rating",
        uniqueConstraints= @UniqueConstraint(columnNames={"user_id", "commodity_id"})
)
public class RatingEntity {

    public RatingEntity() {}
    public RatingEntity(UserEntity user, CommodityEntity commodity, float score) {
        this.user = user;
        this.commodity = commodity;
        this.score = score;
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

    public CommodityEntity getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityEntity commodity) {
        this.commodity = commodity;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = CommodityEntity.class)
    private CommodityEntity commodity;
    @Column(name = "score")
    private float score;

}
