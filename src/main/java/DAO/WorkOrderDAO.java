package DAO;

import config.SessionFactoryUtil;
import entity.WorkOrder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class WorkOrderDAO {

    public static void saveOrUpdateWorkOrder(WorkOrder workOrder) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(workOrder);
            transaction.commit();
        }
    }

    public static List<WorkOrder> getWorkOrders() {
        List<WorkOrder> workOrders;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            workOrders = session.createQuery("FROM entity.WorkOrder", WorkOrder.class).list();
        }
        return workOrders;
    }

    public static List<WorkOrder> getWorkOrdersBy(long employeeId) {
        List<WorkOrder> workOrders;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WorkOrder> cr = cb.createQuery(WorkOrder.class);
            Root<WorkOrder> root = cr.from(WorkOrder.class);

            cr.select(root).where(cb.equal(root.get("employee"), employeeId));

            Query<WorkOrder> query = session.createQuery(cr);
            workOrders = query.getResultList();
        }
        return workOrders;
    }

    public static List<WorkOrder> getWorkOrdersWith(long partId) {
        List<WorkOrder> workOrders;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WorkOrder> cr = cb.createQuery(WorkOrder.class);
            Root<WorkOrder> root = cr.from(WorkOrder.class);

            cr.select(root).where(cb.equal(root.get("part"), partId));

            Query<WorkOrder> query = session.createQuery(cr);
            workOrders = query.getResultList();
        }
        return workOrders;
    }

    public static void deleteWorkOrder(WorkOrder workOrder) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(workOrder);
            transaction.commit();
        }
    }
}
