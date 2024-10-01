package Baloot.Dao;

import Baloot.Entities.CommodityEntity;
import Baloot.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CommodityDao {

    public List<CommodityEntity> getCommodites() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CommodityEntity ", CommodityEntity.class).list();
        }
    }

    public CommodityEntity getCommodityById(int commodity_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(CommodityEntity.class, commodity_id);
        }
    }

    public List<CommodityEntity> getCommoditiesSortedByName() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommodityEntity C order by C.name desc";
            Query<CommodityEntity> query = session.createQuery(hql, CommodityEntity.class);
            return query.list();
        }
    }

    public List<CommodityEntity> getCommoditiesSortedByPrice() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommodityEntity C order by C.price desc";
            Query<CommodityEntity> query = session.createQuery(hql, CommodityEntity.class);
            return query.list();
        }
    }

    public List<CommodityEntity> searchCommoditiesByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommodityEntity C where C.name like :name";
            Query<CommodityEntity> query = session.createQuery(hql, CommodityEntity.class);
            query.setParameter("name", "%"+name+"%");
            return query.list();
        }
    }

    public List<CommodityEntity> searchCommoditiesByCategory(String category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommodityEntity C join C.categories ca where ca = :category";
            Query<CommodityEntity> query = session.createQuery(hql, CommodityEntity.class);
            query.setParameter("category", category);
            return query.list();
        }
    }

    public List<CommodityEntity> getProviderCommodities(int provider_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CommodityEntity C where C.provider.id = :provider_id";
            Query<CommodityEntity> query = session.createQuery(hql, CommodityEntity.class);
            query.setParameter("provider_id", provider_id);
            return query.list();
        }
    }

    public void saveCommodity(CommodityEntity commodity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(commodity);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateCommodity(CommodityEntity commodity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.update(commodity);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
