package services;

import DAO.AccountsDao;
import hibernateEntities.Accounts;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class AccountsService {

    private final ReadWriteLock lock;
    private final Cache<Accounts> mCache;
    private final AccountsDao mAccountsDao;
    private final Session mSession;
    private static Logger lg;

    private static volatile AccountsService service;

    private AccountsService() {
        mCache = new Cache<Accounts>(50);
        mAccountsDao = new AccountsDao();
        mSession = SessionPool.getInstance().openSession();
        lock = new ReentrantReadWriteLock();
    }


    public static AccountsService getInstance() {

        lg = MainLogger.getInstance();

        if (service == null) {
            synchronized (AccountsService.class) {
                if (service == null) {
                    try {
                        service = new AccountsService();
                    } catch (HibernateException hEx) {
                        lg.error("Failed to create session factory object" + hEx);
                        hEx.printStackTrace();
                    }
                }
            }
        }

        return service;
    }


    public Accounts get(int pId) {
        lock.readLock().lock();
        Accounts tAccounts = mCache.getByID(pId);

        if (tAccounts != null) {
            lg.debug("Getting Accounts object from cache");
            lock.readLock().unlock();
            return tAccounts;
        }
        else {
            lg.debug("Getting Accounts object from DB");
            lock.readLock().unlock();
            return mAccountsDao.get(pId, this.mSession);
        }

    }


    public void delete(int pId) {
        lock.writeLock().lock();
        mCache.delete(pId);
        mAccountsDao.delete(pId, mSession);
        lock.writeLock().unlock();
    }


    public void update(int pId, Accounts pEntity) {
        lock.writeLock().lock();
        mCache.update(pId, pEntity);
        mAccountsDao.update(pEntity, mSession);
        lock.writeLock().unlock();
    }

    public int add(Accounts pEntity) {
        lock.writeLock().lock();
        int tNewAccId = mAccountsDao.add(pEntity, mSession);
        if (pEntity.getId() == 0) {
            pEntity.setId(tNewAccId);
        }
        lg.debug("Size of the cache before add: " + mCache.getSize());
        mCache.add(pEntity);
        lg.debug("Size of the cache after add: " + mCache.getSize());
        lock.writeLock().unlock();
        return tNewAccId;
    }


}
