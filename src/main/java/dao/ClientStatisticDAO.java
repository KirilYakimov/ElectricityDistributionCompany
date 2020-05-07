package dao;

import configuration.SessionFactoryUtil;
import entity.Client;
import entity.ClientStatistic;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public static double getClientTotalBillsPaid(Client client) {
        double TotalBillsPaid;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            TotalBillsPaid = (Double) session.createQuery("SELECT sum(price) FROM Bill WHERE client_id = " + client.getId() + " AND paid = 1").getSingleResult();
        }
        return TotalBillsPaid;
    }

    public static double getClientHighestBillPaid(Client client) {
        double highestBillPaid;

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            highestBillPaid = (Double) session.createQuery("SELECT max(price) FROM Bill WHERE client_id = " + client.getId() + " AND paid = 1").getSingleResult();
        }
        return highestBillPaid;
    }
}
