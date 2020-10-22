package main;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MainLogger {

    private static volatile Logger logger;

    public static Logger getInstance() {

        if (logger == null) {
            synchronized (SessionPool.class) {
                if (logger == null) {
                    logger = LogManager.getLogger(MainLogger.class);
                }
            }
        }

        return logger;
    }

}
