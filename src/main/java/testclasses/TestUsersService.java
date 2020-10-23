package testclasses;

import hibernateEntities.Users;
import main.MainLogger;
import org.apache.logging.log4j.Logger;
import services.UsersService;

public class TestUsersService {

    public TestUsersService() {

        UsersService uSer = UsersService.getInstance();
        Logger lg = MainLogger.getInstance();

        int iduser1 = 0;
        int iduser2 = 0;

        for (int i = 0; i < 70; i++) {
            iduser1 = uSer.add(new Users("Sidor" + i, "Sidorov" + i, "sidor.sidorov@mail.ru", "+3209309332"));
            iduser2 = uSer.add(new Users("Getov" + i, "Get" + i, "sidor.sidodrov@mail.ru", "+329309332"));
        }

        Users user3 = uSer.get(iduser2);
        lg.debug(user3.getUsername() + " " + user3.getUserlastname());

        Users user4 = uSer.get(3);
        lg.debug(user4.getUsername() + " " + user4.getUserlastname());
    }
}
