package DAO;

import config.SessionFactoryUtil;
import entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompanyDAO {

    public static Company getCompany(long id) {
        Transaction transaction;
        Company company;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            transaction.commit();
        }
        return company;
    }

    public static void saveOrUpdateCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
