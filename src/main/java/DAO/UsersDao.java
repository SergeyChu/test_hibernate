package DAO;

import hibernateEntities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import main.SessionPool;

public class UsersDao extends DaoTest<Users>{

    public UsersDao(){

        super(Users.class, "idusers");
    }

    //To delete by username and lastname
    //When object is created but not persisted
    //and we want to remove persisted object with the same username and lastname
    public void delete(Users pUserToDelete) {

        try (Session session = SessionPool.getInstance().openSession()) {

            Transaction tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Users> cq = cb.createQuery(Users.class);
            Root<Users> root = cq.from(Users.class);
            Predicate finalPredicate = cb.and(cb.equal(root.get("username"), pUserToDelete.getUsername()),
                    cb.equal(root.get("userlastname"), pUserToDelete.getUserlastname()));

            cq.select(root).where(finalPredicate);
            Query<Users> query = session.createQuery(cq);

            Users tUserToDelete = query.uniqueResult();

            session.delete(tUserToDelete);
            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
