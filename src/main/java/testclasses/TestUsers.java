package testclasses;

import DAO.UsersDao;
import hibernateEntities.Identifiable;
import hibernateEntities.Users;
import main.MainLogger;
import main.SessionPool;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

public class TestUsers {

    private Logger lg;

    TestUsers() {

        Session tSession = SessionPool.getInstance().openSession();
        lg = MainLogger.getInstance();

        UsersDao usersDao = new UsersDao();

        //Getting user by ID and printing it
        Users user = usersDao.get(1, tSession);
        System.out.println(user.getIdusers() + " " + user.getUsername() + " " + user.getUserlastname());

        //Printing list of all the users
        printAll(usersDao, tSession);

        //Creating a new user and printing list of all users
        Users newUser = new Users("Alexxx", "Smirnov", "sdfasdf@makd.gu", "+43839444");
        usersDao.add(newUser, tSession);
        printAll(usersDao, tSession);

        //Duplicate creation and error
        usersDao.add(newUser, tSession);
        //Deleting the user by name and last name
        usersDao.delete(newUser);
        //Printing new list of users
        printAll(usersDao, tSession);

        //Updating the first user get
        user.setUseremail("New@test");
        usersDao.update(user, tSession);
        printAll(usersDao, tSession);


    }

    private void printAll(UsersDao usersDao, Session tSession){
        for (Users u : usersDao.getAll(tSession)) {
            lg.debug(u.getIdusers() + " " + u.getUsername() + " " + u.getUserlastname());
        }
    }
}
