package Baloot.Dao;

import Baloot.Entities.CommentEntity;
import Baloot.Entities.RatingEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RatingDao {

    public List<RatingEntity> getRatings() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from RatingEntity ", RatingEntity.class).list();
        }
    }

    public List<RatingEntity> getCommodityRatings(int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from RatingEntity r where r.commodity.id = :commodity_id";
            Query<RatingEntity> query = session.createQuery(hql, RatingEntity.class);
            query.setParameter("commodity_id", commodity_id);
            return query.list();
        }
    }

    public RatingEntity getRating(int user_id, int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<RatingEntity> query = session.createQuery(
                    "from RatingEntity r where r.user.id=:user_id and r.commodity.id=:commodity_id",
                    RatingEntity.class);
            query.setParameter("user_id", user_id);
            query.setParameter("commodity_id", commodity_id);
            return query.uniqueResult();
        }
    }

    public RatingEntity getRatingById(int rating_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(RatingEntity.class, rating_id);
        }
    }

    public void saveRating(RatingEntity rating) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(rating);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
