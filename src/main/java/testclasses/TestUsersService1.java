package testclasses;

import DAO.UsersDao;
import hibernateEntities.Users;
import main.MainLogger;
import org.apache.logging.log4j.Logger;
import services.Service;
import services.ServicesPool;


public class TestUsersService1 {

    public TestUsersService1() throws IllegalAccessException, InstantiationException {

        Service tUserService = ServicesPool.getInstance(UsersDao.class);
        Logger lg = MainLogger.getInstance();


        int iduser2 = 0;

        for (int i = 0; i < 70; i++) {
            tUserService.add(new Users("Sidor" + i, "Sidorov" + i, "sidor.sidorov@mail.ru", "+3209309332"));
            iduser2 = tUserService.add(new Users("Getov" + i, "Get" + i, "sidor.sidodrov@mail.ru", "+329309332"));
        }

        Users user3 = (Users)tUserService.get(iduser2);
        lg.debug("Got the user from DB" + user3.getUsername() + " " + user3.getUserlastname());

        Users user4 = (Users)tUserService.get(3);
        lg.debug("Got the user from DB" + user4.getUsername() + " " + user4.getUserlastname());

        user4.setUsername("Updated name");
        tUserService.update(3, user4);

        tUserService.delete(4);
    }
}
