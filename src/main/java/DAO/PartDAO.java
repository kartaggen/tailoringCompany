package DAO;

import config.SessionFactoryUtil;
import entity.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PartDAO {

    public static void saveOrUpdatePart(Part part) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(part);
            transaction.commit();
        }
    }

    public static Part getPartById(long id) {
        Part part;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            part = session.byId(Part.class).getReference(id);
            transaction.commit();
        }
        return part;
    }

    public static List<Part> getParts() {
        List<Part> parts;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            parts = session.createQuery("FROM entity.Part", Part.class).list();
        }
        return parts;
    }

    public static List<Part> getPartsBy(long materialId) {
        List<Part> parts;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Part> cr = cb.createQuery(Part.class);
            Root<Part> root = cr.from(Part.class);

            cr.select(root).where(cb.equal(root.get("material"), materialId));

            Query<Part> query = session.createQuery(cr);
            parts = query.getResultList();
        }
        return parts;
    }

    public static void deletePart(Part part) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(part);
            transaction.commit();
        }
    }
}
