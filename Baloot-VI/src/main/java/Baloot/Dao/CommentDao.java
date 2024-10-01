package Baloot.Dao;

import Baloot.Entities.CommentEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CommentDao {

    public List<CommentEntity> getComments() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CommentEntity ", CommentEntity.class).list();
        }
    }

    public List<CommentEntity> getCommodityComments(int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommentEntity r where r.commodity.id = :commodity_id";
            Query<CommentEntity> query = session.createQuery(hql, CommentEntity.class);
            query.setParameter("commodity_id", commodity_id);
            return query.list();
        }
    }

    public CommentEntity getCommentById(int comment_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(CommentEntity.class, comment_id);
        }
    }

    public CommentEntity getComment(int user_id, int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CommentEntity> query = session.createQuery(
                    "from CommentEntity c where c.user.id=:user_id and c.commodity.id=:commodity_id",
                    CommentEntity.class);
            query.setParameter("user_id", user_id);
            query.setParameter("commodity_id", commodity_id);
            return query.uniqueResult();
        }
    }

    public void saveComment(CommentEntity comment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(comment);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
