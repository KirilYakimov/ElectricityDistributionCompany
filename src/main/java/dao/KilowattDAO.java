package dao;

import configuration.SessionFactoryUtil;
import entity.KilowattPrice;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class KilowattDAO {

    public static void saveKilowattPrice(KilowattPrice kilowattPrice) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(kilowattPrice);
            transaction.commit();
        }
    }

    public static void saveOrUpdateKilowatt(KilowattPrice legalPrice, KilowattPrice privatePrice) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(legalPrice);
            session.saveOrUpdate(privatePrice);
            transaction.commit();
        }
    }

    public static List<KilowattPrice> getKilowattPrice() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM KilowattPrice", KilowattPrice.class).list();
        }
    }
}
