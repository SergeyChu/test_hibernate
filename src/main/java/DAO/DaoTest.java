package DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import main.SessionPool;


public abstract class DaoTest<T> {

    private final Class<T> currentClass;
    private final String idColumn;


    public DaoTest(Class<T> pCurrentClass, String pIdColumn){

        this.currentClass = pCurrentClass;
        this.idColumn = pIdColumn;
    }


    public T get(int id) {

        try(Session session = SessionPool.getInstance().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(currentClass);
            Root<T> root = cq.from(currentClass);
            cq.select(root).where(cb.equal(root.get(idColumn), id));

            Query<T> query = session.createQuery(cq);

            return query.uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<T> getAll(){

        try(Session session = SessionPool.getInstance().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(currentClass);
            Root<T> root = cq.from(currentClass);
            cq.select(root);

            Query<T> query = session.createQuery(cq);

            return query.getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;

    }


    public int add(T pEntity) {

        try (Session session = SessionPool.getInstance().openSession()) {

            Transaction tx = session.beginTransaction();
            int id = (int) session.save(pEntity);
            tx.commit();
            return id;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }


    public void update(T pUpdatedEntity) {

        try (Session session = SessionPool.getInstance().openSession()) {

            Transaction tx = session.beginTransaction();
            session.update(pUpdatedEntity);
            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Session session = SessionPool.getInstance().openSession()) {

            Transaction tx = session.beginTransaction();

            T tEntToDelete = get(id);
            session.delete(tEntToDelete);

            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
