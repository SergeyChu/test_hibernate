package DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public abstract class DaoTest<T> {

    private final Class<T> currentClass;
    private final String idColumn;


    public DaoTest(Class<T> pCurrentClass, String pIdColumn){

        this.currentClass = pCurrentClass;
        this.idColumn = pIdColumn;
    }


    public T get(int pId, Session pSession) {

        try {

            CriteriaBuilder cb = pSession.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(currentClass);
            Root<T> root = cq.from(currentClass);
            cq.select(root).where(cb.equal(root.get(idColumn), pId));

            Query<T> query = pSession.createQuery(cq);

            return query.uniqueResult();

        } catch (HibernateException e) {

            System.err.println(e.getMessage());
        }

        return null;
    }


    public List<T> getAll(Session pSession){

        try {

            CriteriaBuilder cb = pSession.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(currentClass);
            Root<T> root = cq.from(currentClass);
            cq.select(root);

            Query<T> query = pSession.createQuery(cq);

            return query.getResultList();

        } catch (HibernateException e) {

            System.err.println(e.getMessage());
        }

        return null;

    }


    public int add(T pEntity, Session pSession) {

        try {

            Transaction tx = pSession.beginTransaction();
            int id = (int) pSession.save(pEntity);
            tx.commit();
            return id;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }


    public void update(T pUpdatedEntity, Session pSession) {

        try {

            Transaction tx = pSession.beginTransaction();
            pSession.update(pUpdatedEntity);
            tx.commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
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
