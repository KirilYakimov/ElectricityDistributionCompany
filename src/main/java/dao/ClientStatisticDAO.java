package dao;

import configuration.SessionFactoryUtil;
import entity.Client;
import entity.ClientStatistic;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class ClientStatisticDAO {


    public static void saveClientStatistic(ClientStatistic clientStatistic) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clientStatistic.getClient().setClientStatistics(clientStatistic);
            session.save(clientStatistic);
            transaction.commit();
        }
    }

    public static void saveOrUpdateClientStatistic(ClientStatistic clientStatistic) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clientStatistic.getClient().setClientStatistics(clientStatistic);
            session.saveOrUpdate(clientStatistic);
            transaction.commit();
        }
    }

    public static void deleteClientStatistic(ClientStatistic clientStatistic) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(clientStatistic);
            transaction.commit();
        }
    }

    public static List<Client> getClientStatistic() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createNativeQuery(
                    "SELECT * FROM client INNER JOIN client_statistic ON client.id = client_statistic.client_id", Client.class).getResultList();
        }
    }

    public static List<Client> ClientsWhoPaidEqualOrLess(BigDecimal num) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createNativeQuery(
                    "SELECT * FROM client " +
                            "WHERE EXISTS " +
                            "(SELECT * " +
                            "FROM bills " +
                            "WHERE client.id = bills.client_id AND bills.paid = 1 AND bills.price <= "+ num +")", Client.class).getResultList();
        }
    }

    public static BigDecimal getClientTotalBillsPaid(Client client) {
        BigDecimal TotalBillsPaid;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            TotalBillsPaid = (BigDecimal) session.createQuery("SELECT sum(price) FROM Bill WHERE client_id = " + client.getId() + " AND paid = 1").getSingleResult();
        }
        return TotalBillsPaid;
    }

    public static BigDecimal getClientHighestBillPaid(Client client) {
        BigDecimal highestBillPaid;

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            highestBillPaid = (BigDecimal) session.createQuery("SELECT max(price) FROM Bill WHERE client_id = " + client.getId() + " AND paid = 1").getSingleResult();
        }
        return highestBillPaid;
    }

    public static BigDecimal getIncome () {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return  (BigDecimal) session.createQuery("SELECT sum(price) FROM Bill WHERE paid = 1").getSingleResult();
        }
    }
    public static BigDecimal getExpenses () {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return  (BigDecimal) session.createQuery("SELECT sum(maintenance) FROM RealEstate").getSingleResult();
        }
    }
}
