package dao;

import configuration.SessionFactoryUtil;
import entity.RealEstate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RealEstateDAO {

    public static void saveRealEstate(RealEstate employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
    }

    public static void saveOrUpdateRealEstate(RealEstate employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        }
    }

    public static void deleteRealEstate(RealEstate employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        }
    }

    public static List<RealEstate> getRealEstates() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM RealEstate", RealEstate.class).list();
        }
    }
}
