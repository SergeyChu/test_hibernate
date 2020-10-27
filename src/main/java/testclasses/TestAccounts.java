package testclasses;

import DAO.AccountsDao;
import DAO.UsersDao;
import hibernateEntities.Accounts;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;


public class TestAccounts {

    private Logger lg;

    TestAccounts() {

        Session tSession = SessionPool.getInstance().openSession();
        lg = MainLogger.getInstance();

        AccountsDao accsDao = new AccountsDao();
        UsersDao usersDao = new UsersDao();

        //Getting user by ID and printing it
        Accounts acc = (Accounts)accsDao.get(1, tSession);
        lg.debug(acc.getIdaccounts() + " " + acc.getUsers().getIdusers() + " " + acc.getAccdesc());

        //Printing list of all the users
        printAll(accsDao, tSession);

        //Creating a new user and printing list of all users
        Accounts newAccount = new Accounts(usersDao.get(2, tSession), "Trash test");
        accsDao.add(newAccount, tSession);
        printAll(accsDao, tSession);

        //Deleting the user by name and last name
        accsDao.delete(newAccount.getIdaccounts(), tSession);
        //Printing new list of users
        printAll(accsDao, tSession);

        //Updating the first user get
        acc.setAccdesc("Updated desc");
        accsDao.update(acc, tSession);
        printAll(accsDao, tSession);

    }

    private void printAll(AccountsDao accsDao, Session tSession){
        for (Accounts acc : accsDao.getAll(tSession)) {
            lg.debug(acc.getIdaccounts() + " " + acc.getUsers().getIdusers() + " " + acc.getAccdesc());
        }
    }

}
