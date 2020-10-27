package DAO;

import hibernateEntities.Identifiable;
import main.MainLogger;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class Dao<T extends Identifiable> {

    private final Class<T> entityClass;
    private final String idColumn;
    Logger lg;

    public Dao(Class<T> pCurrentClass, String pIdColumn){
        this.lg = MainLogger.getInstance();
        this.entityClass = pCurrentClass;
        this.idColumn = pIdColumn;
    }

    public T get(int pId, Session pSession) {

        try {
            CriteriaBuilder cb = pSession.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root).where(cb.equal(root.get(idColumn), pId));
            Query<T> query = pSession.createQuery(cq);
            return query.uniqueResult();
        } catch (HibernateException e) {
            lg.error(e.getMessage());
        }

        return null;
    }

    public List<T> getAll(Session pSession){

        try {
            CriteriaBuilder cb = pSession.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            Query<T> query = pSession.createQuery(cq);
            return query.getResultList();
        } catch (HibernateException e) {
            lg.error(e.getMessage());
        }

        return null;

    }

    public int add(Identifiable pEntity, Session pSession) {

        try {
            Transaction tx = pSession.beginTransaction();
            int id = (int) pSession.save(pEntity);
            tx.commit();
            return id;
        } catch (HibernateException e) {
            lg.error(e.getMessage());
        }
        return 0;
    }

    public void update(Identifiable pUpdatedEntity, Session pSession) {

        try {
            Transaction tx = pSession.beginTransaction();
            pSession.update(pUpdatedEntity);
            tx.commit();
        } catch (HibernateException e) {
            lg.error(e.getMessage());
        }

    }

    public void delete(int pId, Session pSession) {

        try {
            Transaction tx = pSession.beginTransaction();
            T tEntToDelete = get(pId, pSession);
            pSession.delete(tEntToDelete);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}