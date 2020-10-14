package testclasses;

import DAO.AccountsDao;
import DAO.UsersDao;
import hibernateEntities.Accounts;
import hibernateEntities.Users;


public class TestAccounts {

    TestAccounts() {
        AccountsDao accsDao = new AccountsDao();

        //Getting user by ID and printing it
        Accounts acc = accsDao.get(1);
        System.out.println(acc.getIdaccounts() + " " + acc.getIdusers() + " " + acc.getAccdesc());

        //Printing list of all the users
        printAll(accsDao);

        //Creating a new user and printing list of all users
        Accounts newAccount = new Accounts(2, "Trash test");
        accsDao.add(newAccount);
        printAll(accsDao);

        //Deleting the user by name and last name
        accsDao.delete(newAccount.getIdaccounts());
        //Printing new list of users
        printAll(accsDao);

        //Updating the first user get
        acc.setAccdesc("Updated desc");
        accsDao.update(acc);
        printAll(accsDao);

    }

    private void printAll(AccountsDao accsDao){
        for (Accounts acc : accsDao.getAll()) {
            System.out.println(acc.getIdaccounts() + " " + acc.getIdusers() + " " + acc.getAccdesc());
        }
    }

}
