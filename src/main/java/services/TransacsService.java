package services;

import DAO.TransacsDao;
import hibernateEntities.Transacs;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class TransacsService {

    private final ReadWriteLock lock;
    private final Cache<Transacs> mCache;
    private final TransacsDao mTransacsDao;
    private final Session mSession;
    private static Logger lg;

    private static volatile TransacsService service;

    private TransacsService() {
        mCache = new Cache<Transacs>(50);
        mTransacsDao = new TransacsDao();
        mSession = SessionPool.getInstance().openSession();
        lock = new ReentrantReadWriteLock();
    }


    public static TransacsService getInstance() {

        lg = MainLogger.getInstance();

        if (service == null) {
            synchronized (TransacsService.class) {
                if (service == null) {
                    try {
                        service = new TransacsService();
                    } catch (HibernateException hEx) {
                        lg.error("Failed to create session factory object" + hEx);
                        hEx.printStackTrace();
                    }
                }
            }
        }

        return service;
    }


    public Transacs get(int pId) {
        lock.readLock().lock();
        Transacs tTransacs = mCache.getByID(pId);

        if (tTransacs != null) {
            lg.debug("Getting Transacs object from cache");
            lock.readLock().unlock();
            return tTransacs;
        }
        else {
            lg.debug("Getting Transacs object from DB");
            lock.readLock().unlock();
            return mTransacsDao.get(pId, this.mSession);
        }

    }


    public void delete(int pId) {
        lock.writeLock().lock();
        mCache.delete(pId);
        mTransacsDao.delete(pId, mSession);
        lock.writeLock().unlock();
    }


    public void update(int pId, Transacs pEntity) {
        lock.writeLock().lock();
        mCache.update(pId, pEntity);
        mTransacsDao.update(pEntity, mSession);
        lock.writeLock().unlock();
    }

    public int add(Transacs pEntity) {
        lock.writeLock().lock();
        int tNewTranId = mTransacsDao.add(pEntity, mSession);
        if (pEntity.getId() == 0) {
            pEntity.setId(tNewTranId);
        }
        lg.debug("Size of the cache before add: " + mCache.getSize());
        mCache.add(pEntity);
        lg.debug("Size of the cache after add: " + mCache.getSize());
        lock.writeLock().unlock();
        return tNewTranId;
    }


}
