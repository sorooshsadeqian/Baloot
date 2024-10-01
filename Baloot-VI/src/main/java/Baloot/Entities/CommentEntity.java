package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comment",
        uniqueConstraints= @UniqueConstraint(columnNames={"user_id", "commodity_id"})
)
public class CommentEntity {

    public CommentEntity() {}

    public CommentEntity(UserEntity user, CommodityEntity commodity, String text, String date) {
        this.user = user;
        this.commodity = commodity;
        this.text = text;
        this.date = date;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = CommodityEntity.class)
    private CommodityEntity commodity;
    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private String date;

}
