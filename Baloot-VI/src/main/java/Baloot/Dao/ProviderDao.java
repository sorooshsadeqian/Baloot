package Baloot.Dao;

import Baloot.Entities.ProviderEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProviderDao {

    public List<ProviderEntity> getProviders() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from ProviderEntity ", ProviderEntity.class).list();
        }
    }

    public ProviderEntity getProviderById(int provider_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ProviderEntity.class, provider_id);
        }
    }

    public void saveProvider(ProviderEntity provider) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(provider);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
