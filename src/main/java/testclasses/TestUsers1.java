package testclasses;

import DAO.AccountsDao;
import DAO.TransacsDao;
import DAO.UsersDao;
import hibernateEntities.Accounts;
import hibernateEntities.Transacs;
import hibernateEntities.Users;
import main.CommonUtils;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import main.MainLogger;



public class TestUsers1 {

    public TestUsers1 () {
        Session tSession = SessionPool.getInstance().openSession();
        Logger lg = MainLogger.getInstance();

        UsersDao tUsersDao = new UsersDao();
        Users user = new Users("Sergey", "Chuburov", "sdffd@mail.ru", "+782902393");
        Users user1 = new Users("Vasily", "Petrov", "dd@msdf.ru", "+34574333");
        Users user2 = new Users("IVan", "Fedorov", "peace@msdf.ru", "+8489304");
        Users user3 = new Users("Donald", "Duck", "quack_quack@msdf.ru", "+2346765");

        tUsersDao.add(user, tSession);
        tUsersDao.add(user1, tSession);
        tUsersDao.add(user2, tSession);
        tUsersDao.add(user3, tSession);

        AccountsDao tAccountsDao = new AccountsDao();
        Accounts acc1 = new Accounts(user1, "Investment portfolio");
        Accounts acc2 = new Accounts(user1, "For food");
        Accounts acc3 = new Accounts(user1, "For girls and drugs");
        Accounts acc4 = new Accounts(user3, "main kotleta");

        tAccountsDao.add(acc1, tSession);
        tAccountsDao.add(acc2, tSession);
        tAccountsDao.add(acc3, tSession);
        tAccountsDao.add(acc4, tSession);

        TransacsDao tTransacsDao = new TransacsDao();

        Transacs tran = new Transacs(acc1, CommonUtils.getCurrentDateTime(), "Buy Gazprom", 100, true );
        Transacs tran1 = new Transacs(acc1, CommonUtils.getCurrentDateTime(), "Buy Apple", 200, true );
        Transacs tran2 = new Transacs(acc1, CommonUtils.getCurrentDateTime(), "Short Tesla", 500, false );
        Transacs tran3 = new Transacs(acc2, CommonUtils.getCurrentDateTime(), "Short Tesla", 500, false );

        tTransacsDao.add(tran, tSession);
        tTransacsDao.add(tran1, tSession);
        tTransacsDao.add(tran2, tSession);
        tTransacsDao.add(tran3, tSession);

        lg.debug("The initial data was pushed into DB");

        lg.debug("Available users");
        for (Users usr: tUsersDao.getAll(tSession)) {
            lg.debug(usr.getIdusers() + " " + usr.getUsername() + " " + usr.getUserlastname());
        }

        lg.debug("Available accounts");
        for (Accounts acc: tAccountsDao.getAll(tSession)) {
            lg.debug(acc.getUsers().getUsername() + " " + acc.getAccdesc());
        }

        lg.debug("Available transacs");
        for (Transacs tra: tTransacsDao.getAll(tSession)) {
            lg.debug(tra.getAcounts().getUsers().getUsername() + " " + tra.getDescription() + " " + tra.getSumm());
        }
    }
}
