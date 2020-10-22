package testclasses;

import DAO.AccountsDao;
import DAO.TransacsDao;
import hibernateEntities.Transacs;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TestTransacs {

    private Logger lg;

    public TestTransacs() {

        Session tSession = SessionPool.getInstance().openSession();
        lg = MainLogger.getInstance();

        TransacsDao transacsDao = new TransacsDao();
        AccountsDao accountsDao = new AccountsDao();

        //Getting user by ID and printing it
        Transacs tran = transacsDao.get(2, tSession);
        lg.debug(tran.getDescription() + " " + tran.getDatetime() + " " + tran.getAcounts().getIdaccounts()
                + " " + tran.getIdtransacs() + " " + tran.getSumm() + " " + tran.getIs_income());

        //Printing list of all the users
        printAll(transacsDao, tSession);

        //Creating a new entity and printing list of all entities
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currDateTime = formatter.format(now);

        Transacs newTransac = new Transacs(accountsDao.get(1, tSession), currDateTime, "test descriptionnnnn", 300, false);
        transacsDao.add(newTransac, tSession);
        printAll(transacsDao, tSession);

        //Deleting the entity by id
        transacsDao.delete(newTransac.getIdtransacs(), tSession);
        //Printing new list of users
        printAll(transacsDao, tSession);

        //Updating the first user get
        tran.setDescription("Updated TEST desc");
        transacsDao.update(tran, tSession);
        printAll(transacsDao, tSession);

    }

    private void printAll(TransacsDao transacsDao, Session tSession){
        for (Transacs tr : transacsDao.getAll(tSession)) {
            lg.debug(tr.getDescription() + " " + tr.getDatetime() + " " + tr.getAcounts().getIdaccounts()
                    + " " + tr.getIdtransacs() + " " + tr.getSumm() + " " + tr.getIs_income());
        }
    }
}
