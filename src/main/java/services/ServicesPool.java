package services;

import DAO.Dao;
import hibernateEntities.Identifiable;
import main.MainLogger;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ConcurrentHashMap;


public class ServicesPool {

    private static volatile ConcurrentHashMap<String, Service> services = new ConcurrentHashMap<>();

    public static Service getInstance(Class<? extends Dao<? extends Identifiable>> pDaoClass) throws InstantiationException, IllegalAccessException {

        Logger lg = MainLogger.getInstance();

        String tSimpleName = pDaoClass.getSimpleName();
        lg.debug("Simple name: " + tSimpleName);

        if (services.get(tSimpleName) == null) {
            synchronized (Service.class) {
                if (services.get(tSimpleName) == null) {
                    Service tService = new Service(pDaoClass);
                    services.put(tSimpleName, tService);
                    lg.debug("Created new service for: " + tSimpleName);
                }
            }
        }
        return services.get(pDaoClass.getSimpleName());
    }


}
