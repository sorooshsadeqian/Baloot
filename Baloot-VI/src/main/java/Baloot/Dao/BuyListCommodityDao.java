package Baloot.Dao;

import Baloot.Entities.BuyListCommodityEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BuyListCommodityDao {

    public List<BuyListCommodityEntity> getUserBuyList(int user_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from BuyListCommodityEntity b where b.user.id = :user_id";
            Query<BuyListCommodityEntity> query = session.createQuery(hql, BuyListCommodityEntity.class);
            query.setParameter("user_id", user_id);
            return query.list();
        }
    }

    public void deleteUserBuyList(int user_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "delete from BuyListCommodityEntity b where b.user.id = :user_id";
            Query<BuyListCommodityEntity> query = session.createQuery(hql, BuyListCommodityEntity.class);
            query.setParameter("user_id", user_id);
            query.executeUpdate();
        }
    }

    public BuyListCommodityEntity getBuyListCommodity(int user_id, int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BuyListCommodityEntity> query = session.createQuery(
                    "from BuyListCommodityEntity b where b.user.id=:user_id and b.commodity.id=:commodity_id",
                    BuyListCommodityEntity.class);
            query.setParameter("user_id", user_id);
            query.setParameter("commodity_id", commodity_id);
            return query.uniqueResult();
        }
    }

    public void deleteBuyListCommodity(BuyListCommodityEntity buyListCommodity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.delete(buyListCommodity);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void saveBuyListCommodity(BuyListCommodityEntity buyListCommodity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(buyListCommodity);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
