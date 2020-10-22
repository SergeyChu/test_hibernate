package main;

import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import main.MainLogger;


public class SessionPool {

    private static volatile SessionFactory factory;

    public static SessionFactory getInstance() {

        Logger lg = MainLogger.getInstance();

        if (factory == null) {
            synchronized (SessionPool.class) {
                if (factory == null) {
                    try {
                        factory = new Configuration().configure().buildSessionFactory();
                    } catch (HibernateException hEx) {
                        lg.error("Failed to create session factory object" + hEx);
                        hEx.printStackTrace();
                    }
                }
            }
        }

        return factory;
    }

}
