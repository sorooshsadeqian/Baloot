package Baloot.Dao;

import Baloot.Entities.DiscountCodeEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DiscountCodeDao {

    public List<DiscountCodeEntity> getDiscountCodes() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from DiscountCodeEntity ", DiscountCodeEntity.class).list();
        }
    }

    public DiscountCodeEntity getDiscountCodeById(int discountCode_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(DiscountCodeEntity.class, discountCode_id);
        }
    }

    public DiscountCodeEntity getDiscountCodeByCode(String code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiscountCodeEntity> query = session.createQuery("from DiscountCodeEntity d where d.code=:code", DiscountCodeEntity.class);
            query.setParameter("code", code);
            return query.uniqueResult();
        }
    }

    public void saveDiscountCode(DiscountCodeEntity discountCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(discountCode);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateDiscountCode(DiscountCodeEntity discountCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.update(discountCode);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
