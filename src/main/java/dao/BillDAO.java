package dao;

import configuration.SessionFactoryUtil;
import entity.Bill;
import entity.Client;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class BillDAO {

    public static void saveBill(Bill bill) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(bill);
            transaction.commit();
        }
    }

    public static void payBill(Bill bill) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(bill);
            transaction.commit();
        }
    }

    public static void deleteBill(Bill employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        }
    }

    public static Set<Bill> getClientsBills(Client client) {
        Set<Bill> bills;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Client client1 = session.find(Client.class, client.getId());
            bills = client1.getBillList();
            Hibernate.initialize(bills);
        }
        return bills;
    }
}
