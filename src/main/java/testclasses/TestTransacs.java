package testclasses;

import DAO.TransacsDao;
import hibernateEntities.Transacs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TestTransacs {

    public TestTransacs() {

        TransacsDao transacsDao = new TransacsDao();

        //Getting user by ID and printing it
        Transacs tran = transacsDao.get(2);
        System.out.println(tran.getDescription() + " " + tran.getDatetime() + " " + tran.getIdaccounts()
                + " " + tran.getIdtransacs() + " " + tran.getSumm() + " " + tran.getIs_income());

        //Printing list of all the users
        printAll(transacsDao);

        //Creating a new entity and printing list of all entities
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currDateTime = formatter.format(now);

        Transacs newTransac = new Transacs(currDateTime, "test descriptionnnnn", 300, 1, false);
        transacsDao.add(newTransac);
        printAll(transacsDao);

        //Deleting the entity by id
        transacsDao.delete(newTransac.getIdtransacs());
        //Printing new list of users
        printAll(transacsDao);

        //Updating the first user get
        tran.setDescription("Updated TEST desc");
        transacsDao.update(tran);
        printAll(transacsDao);

    }

    private void printAll(TransacsDao transacsDao){
        for (Transacs tr : transacsDao.getAll()) {
            System.out.println(tr.getDescription() + " " + tr.getDatetime() + " " + tr.getIdaccounts()
                    + " " + tr.getIdtransacs() + " " + tr.getSumm() + " " + tr.getIs_income());
        }
    }
}
