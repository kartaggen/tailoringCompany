package DAO;

import config.SessionFactoryUtil;
import entity.Material;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MaterialDAO {

    public static void saveOrUpdateMaterial(Material material) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(material);
            transaction.commit();
        }
    }

    public static Material getMaterialById(long id) {
        Material material;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            material = session.byId(Material.class).getReference(id);
            transaction.commit();
        }
        return material;
    }

    public static List<Material> getMaterials() {
        List<Material> materials;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            materials = session.createQuery("FROM entity.Material", Material.class).list();
        }
        return materials;
    }

    public static void deleteMaterial(Material material) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(material);
            transaction.commit();
        }
    }
}
