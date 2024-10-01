package Baloot.Utils;

import java.util.Properties;

import Baloot.Entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/Baloot?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "example");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(ProviderEntity.class);
                configuration.addAnnotatedClass(CommodityEntity.class);
                configuration.addAnnotatedClass(UserEntity.class);
                configuration.addAnnotatedClass(CommentEntity.class);
                configuration.addAnnotatedClass(RatingEntity.class);
                configuration.addAnnotatedClass(DiscountCodeEntity.class);
                configuration.addAnnotatedClass(BuyListCommodityEntity.class);
                configuration.addAnnotatedClass(PurchasedCommodityEntity.class);
                configuration.addAnnotatedClass(CommentVoteEntity.class);
                configuration.addAnnotatedClass(UsedDiscountCodeEntity.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}