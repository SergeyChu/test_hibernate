package main;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class SessionPool {

    private static volatile SessionFactory factory;

    public static SessionFactory getInstance() {

        if (factory == null) {
            synchronized (SessionPool.class) {
                if (factory == null) {
                    try {
                        factory = new Configuration().configure().buildSessionFactory();
                    } catch (HibernateException hEx) {
                        System.err.println("Failed to create session factory object" + hEx);
                        hEx.printStackTrace();
                    }
                }
            }
        }

        return factory;
    }

}
