package services;

import DAO.UsersDao;
import hibernateEntities.Users;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class UsersService {

    private final ReadWriteLock lock;
    private final Cache<Users> mCache;
    private final UsersDao mUsersDao;
    private final Session mSession;
    private static Logger lg;

    private static volatile UsersService service;

    private UsersService() {
        mCache = new Cache<Users>(50);
        mUsersDao = new UsersDao();
        mSession = SessionPool.getInstance().openSession();
        lock = new ReentrantReadWriteLock();
    }


    public static UsersService getInstance() {

        lg = MainLogger.getInstance();

        if (service == null) {
            synchronized (UsersService.class) {
                if (service == null) {
                    try {
                        service = new UsersService();
                    } catch (HibernateException hEx) {
                        lg.error("Failed to create session factory object" + hEx);
                        hEx.printStackTrace();
                    }
                }
            }
        }

        return service;
    }


    public Users get(int pId) {
        lock.readLock().lock();
        Users tUsers = mCache.getByID(pId);

        if (tUsers != null) {
            lg.debug("Getting users object from cache");
            lock.readLock().unlock();
            return tUsers;
        }
        else {
            lg.debug("Getting users object from DB");
            lock.readLock().unlock();
            return mUsersDao.get(pId, this.mSession);
        }

    }


    public void delete(int pId) {
        lock.writeLock().lock();
        mCache.delete(pId);
        mUsersDao.delete(pId, mSession);
        lock.writeLock().unlock();
    }


    public void update(int pId, Users pEntity) {
        lock.writeLock().lock();
        mCache.update(pId, pEntity);
        mUsersDao.update(pEntity, mSession);
        lock.writeLock().unlock();
    }

    public int add(Users pEntity) {
        lock.writeLock().lock();
        int tNewUserId = mUsersDao.add(pEntity, mSession);
        if (pEntity.getId() == 0) {
            pEntity.setId(tNewUserId);
        }
        lg.debug("Size of the cache before add: " + mCache.getSize());
        mCache.add(pEntity);
        lg.debug("Size of the cache after add: " + mCache.getSize());
        lock.writeLock().unlock();
        return tNewUserId;
    }


}
