package DAO;

import config.SessionFactoryUtil;
import entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAO {

    public static void saveOrUpdateEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        }
    }

    public static Employee getEmployeeById(long id) {
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employee = session.byId(Employee.class).getReference(id);
            transaction.commit();
        }
        return employee;
    }

    public static List<Employee> getEmployees() {
        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            employees = session.createQuery("FROM entity.Employee", Employee.class).list();
        }
        return employees;
    }

    public static void deleteEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        }
    }
}
