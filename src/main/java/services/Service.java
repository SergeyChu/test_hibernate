package services;

import DAO.Dao;
import hibernateEntities.Identifiable;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Service {

    private final ReadWriteLock lock;
    private final Cache mCache;
    private final Dao<? extends Identifiable> mDao;
    private final Session mSession;
    private static Logger lg;

    protected Service(Class<? extends Dao<? extends Identifiable>> pDaoClass) throws IllegalAccessException, InstantiationException {
        mDao = pDaoClass.newInstance();
        mCache = new Cache(50);
        mSession = SessionPool.getInstance().openSession();
        lock = new ReentrantReadWriteLock();
        lg = MainLogger.getInstance();
    }

    public Identifiable get(int pId) {
        lock.readLock().lock();
        Identifiable tEntity = mCache.getByID(pId);

        if (tEntity != null) {
            lg.debug("Getting Transacs object from cache");
            lock.readLock().unlock();
            return tEntity;
        }
        else {
            lg.debug("Getting Transacs object from DB");
            lock.readLock().unlock();
            return mDao.get(pId, this.mSession);
        }
    }

    public void delete(int pId) {
        lock.writeLock().lock();
        mCache.delete(pId);
        mDao.delete(pId, mSession);
        lock.writeLock().unlock();
    }

    public void update(int pId, Identifiable pEntity) {
        lock.writeLock().lock();
        mCache.update(pId, pEntity);
        mDao.update(pEntity, mSession);
        lock.writeLock().unlock();
    }

    public int add(Identifiable pEntity) {
        lock.writeLock().lock();
        int tNewTranId = mDao.add(pEntity, mSession);
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
