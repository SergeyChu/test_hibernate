package DAO;

import hibernateEntities.Transacs;
import hibernateEntities.Accounts;
import hibernateEntities.Users;
import hibernateEntities.Accounts_;
import hibernateEntities.Transacs_;
import hibernateEntities.Users_;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;


import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import main.SessionPool;

public class UsersDao extends Dao<Users> {


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

    public List<Accounts> getAccoutsByIdusers(int pIdusers, Session pSession) {
        try {
            CriteriaBuilder cb = pSession.getCriteriaBuilder();
            CriteriaQuery<Accounts> cq = cb.createQuery(Accounts.class);
            Root<Accounts> accountsRoot = cq.from(Accounts.class);
            Predicate predicate = cb.equal(accountsRoot.get(Accounts_.IDUSERS), pIdusers);
            cq.select(accountsRoot).where(predicate);
            TypedQuery<Accounts> query = pSession.createQuery(cq);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Double join is not really required here, but as an example of how to do it
    public List<Transacs> getTransacsByIdusers(int pIdusers, Session pSession) {

        try {
            CriteriaBuilder cb = pSession.getCriteriaBuilder();
            CriteriaQuery<Transacs> cq1 = cb.createQuery(Transacs.class);
            Root<Transacs> transacsRoot = cq1.from(Transacs.class);
            Join<Transacs, Accounts> transacsAccounts = transacsRoot.join(Transacs_.IDACCOUNTS);
            Join<Accounts, Users> accountsUsers = transacsAccounts.join(Accounts_.IDUSERS);
            Predicate predicate = cb.equal(accountsUsers.get(Users_.IDUSERS), pIdusers);
            cq1.select(transacsRoot).where(predicate);
            TypedQuery<Transacs> query = pSession.createQuery(cq1);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }


}
