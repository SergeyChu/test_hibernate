package testclasses;

import DAO.UsersDao;
import hibernateEntities.Users;

public class TestUsers {

    TestUsers() {

        UsersDao usersDao = new UsersDao();

        //Getting user by ID and printing it
        Users user = usersDao.get(1);
        System.out.println(user.getIdusers() + " " + user.getUsername() + " " + user.getUserlastname());

        //Printing list of all the users
        printAll(usersDao);

        //Creating a new user and printing list of all users
        Users newUser = new Users("Alexxx", "Smirnov", "sdfasdf@makd.gu", "+43839444");
        usersDao.add(newUser);
        printAll(usersDao);

        //Duplicate creation and error
        usersDao.add(newUser);
        //Deleting the user by name and last name
        usersDao.delete(newUser);
        //Printing new list of users
        printAll(usersDao);

        //Updating the first user get
        user.setUseremail("New@test");
        usersDao.update(user);
        printAll(usersDao);


    }

    private void printAll(UsersDao usersDao){
        for (Users u : usersDao.getAll()) {
            System.out.println(u.getIdusers() + " " + u.getUsername() + " " + u.getUserlastname());
        }
    }
}
