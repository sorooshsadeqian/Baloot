package Baloot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comment_vote",
        uniqueConstraints= @UniqueConstraint(columnNames={"user_id", "comment_id"})
)
public class CommentVoteEntity {

    public CommentVoteEntity() {}

    public CommentVoteEntity(UserEntity user, CommentEntity comment, int vote) {
        this.user = user;
        this.comment = comment;
        this.vote = vote;
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

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = CommentEntity.class)
    private CommentEntity comment;
    @Column(name = "vote")  // like: 1, dislike: -1, neutral: 0
    private int vote;

}
