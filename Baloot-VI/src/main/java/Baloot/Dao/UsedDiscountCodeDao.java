package Baloot.Dao;

import Baloot.Entities.UsedDiscountCodeEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsedDiscountCodeDao {

    public List<UsedDiscountCodeEntity> getUserUsedDiscountCodes(int user_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from UsedDiscountCodeEntity p where p.user.id = :user_id";
            Query<UsedDiscountCodeEntity> query = session.createQuery(hql, UsedDiscountCodeEntity.class);
            query.setParameter("user_id", user_id);
            return query.list();
        }
    }

    public UsedDiscountCodeEntity getUsedDiscountCode(int user_id, int discount_code_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UsedDiscountCodeEntity> query = session.createQuery(
                    "from UsedDiscountCodeEntity u where u.user.id=:user_id and u.discountCode=:discount_code_id",
                    UsedDiscountCodeEntity.class);
            query.setParameter("user_id", user_id);
            query.setParameter("discount_code_id", discount_code_id);
            return query.uniqueResult();
        }
    }

    public void deleteUsedDiscountCode(UsedDiscountCodeEntity usedDiscountCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.delete(usedDiscountCode);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void saveUsedDiscountCode(UsedDiscountCodeEntity usedDiscountCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(usedDiscountCode);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
