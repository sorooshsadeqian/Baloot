package Baloot.Dao;

import Baloot.Entities.CommentVoteEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CommentVoteDao {

    public List<CommentVoteEntity> getCommentVotes() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CommentVoteEntity ", CommentVoteEntity.class).list();
        }
    }

    public int getCommentLikes(int comment_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommentVoteEntity r where r.comment.id = :comment_id and r.vote = 1";
            Query<CommentVoteEntity> query = session.createQuery(hql, CommentVoteEntity.class);
            query.setParameter("comment_id", comment_id);
            return query.list().size();
        }
    }

    public int getCommentDislikes(int comment_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommentVoteEntity r where r.comment.id = :comment_id and r.vote = -1";
            Query<CommentVoteEntity> query = session.createQuery(hql, CommentVoteEntity.class);
            query.setParameter("comment_id", comment_id);
            return query.list().size();
        }
    }

    public CommentVoteEntity getCommentVote(int user_id, int comment_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CommentVoteEntity> query = session.createQuery(
                    "from CommentVoteEntity c where c.user.id=:user_id and c.comment.id=:comment_id",
                    CommentVoteEntity.class);
            query.setParameter("user_id", user_id);
            query.setParameter("comment_id", comment_id);
            return query.uniqueResult();
        }
    }

    public CommentVoteEntity getCommentVoteById(int comment_vote_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(CommentVoteEntity.class, comment_vote_id);
        }
    }

    public void saveCommentVote(CommentVoteEntity comment_vote) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(comment_vote);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateCommentVote(CommentVoteEntity comment_vote) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.update(comment_vote);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
