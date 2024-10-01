package Baloot.Dao;

import Baloot.Entities.PurchasedCommodityEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PurchasedCommodityDao {

    public List<PurchasedCommodityEntity> getUserPurchasedCommodities(int user_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from PurchasedCommodityEntity p where p.user.id = :user_id";
            Query<PurchasedCommodityEntity> query = session.createQuery(hql, PurchasedCommodityEntity.class);
            query.setParameter("user_id", user_id);
            return query.list();
        }
    }

    public PurchasedCommodityEntity getPurchasedCommodity(int user_id, int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<PurchasedCommodityEntity> query = session.createQuery(
                    "from PurchasedCommodityEntity p where p.user.id=:user_id and p.commodity.id=:commodity_id",
                    PurchasedCommodityEntity.class);
            query.setParameter("user_id", user_id);
            query.setParameter("commodity_id", commodity_id);
            return query.uniqueResult();
        }
    }

    public void deletePurchasedCommodity(PurchasedCommodityEntity purchasedCommodity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.delete(purchasedCommodity);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void savePurchasedCommodity(PurchasedCommodityEntity purchasedCommodity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(purchasedCommodity);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
