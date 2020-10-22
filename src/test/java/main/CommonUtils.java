package main;
import hibernateEntities.Accounts;
import hibernateEntities.Transacs;
import hibernateEntities.Users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CommonUtils {

    public static List<Users> users;
    public static List<Accounts> accounts;
    public static List<Transacs> transacs;

    public static void cleanDB() throws IOException, SQLException {

        Connection connection = TestDBConnection.getInstance();
        connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        stmt.executeUpdate("truncate table accounts");
        stmt.executeUpdate("truncate table transacs");
        stmt.executeUpdate("truncate table users");
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void pushTestData() throws IOException, SQLException {

        Connection connection = TestDBConnection.getInstance();
        Statement stmt = connection.createStatement();


        //To define all the users to be inserted
        users = new ArrayList<Users>() {{
            add(new Users("Ivan", "Ivanov", "ivan.ivanov42@mail.ru", "+7910323783389"));
            add(new Users("Petr", "Petrov", "pp11@mail.ru", "+23032023"));
            add(new Users("Anonymous", "Anonymous", "sdfasdf@makd.gu", "+43839444"));
        }};

        StringBuilder userValuesRawQuery = new StringBuilder();

        userValuesRawQuery.append("INSERT INTO `users` (`username`, `userlastname`, `useremail`,`userphone`) VALUES ");

        for (Users user: users) {
            userValuesRawQuery.append("('").append(user.getUsername()).append("','").append(user.getUserlastname())
                    .append("','").append(user.getUseremail()).append("','").append(user.getUserphone()).append("'),");
        }
        userValuesRawQuery.setLength(userValuesRawQuery.length() - 1);

        stmt.executeUpdate("ALTER TABLE `users` AUTO_INCREMENT = 0;");
        System.out.println("Got raw users query: " + userValuesRawQuery.toString());
        stmt.executeUpdate(userValuesRawQuery.toString());


        //To define all the accounts to be inserted
        accounts = new ArrayList<Accounts>() {{
            add(new Accounts( users.get(1), "User1 first acc"));
            add(new Accounts( users.get(1), "User1 second acc"));
            add(new Accounts( users.get(2), "User2 first acc"));
        }};

        StringBuilder accountsValuesRawQuery = new StringBuilder();

        accountsValuesRawQuery.append("INSERT INTO `accounts` (`idusers`, `accdesc`) VALUES ");

        for (Accounts account: accounts) {
            accountsValuesRawQuery.append("('").append(account.getUsers().getIdusers()).append("','")
                    .append(account.getAccdesc()).append("'),");
        }
        accountsValuesRawQuery.setLength(accountsValuesRawQuery.length() - 1);

        stmt.executeUpdate("ALTER TABLE `accounts` AUTO_INCREMENT = 0;");
        stmt.executeUpdate(accountsValuesRawQuery.toString());

        //To define all the transacs to be inserted
        transacs = new ArrayList<Transacs>() {{
            add(new Transacs( accounts.get(1), getCurrentDateTime(), "acc 1 transac 1", 556, true));
            add(new Transacs( accounts.get(1), getCurrentDateTime(), "acc 1 transac 2", 343, true));
            add(new Transacs( accounts.get(2), getCurrentDateTime(), "acc 2 transac 1", 777, false));
        }};

        StringBuilder transacsValuesRawQuery = new StringBuilder();

        transacsValuesRawQuery.append("INSERT INTO `transacs` (`datetime`, `description`, " +
                "`summ`, `idaccounts`, `is_income`) VALUES");

        for (Transacs transac: transacs) {
            transacsValuesRawQuery.append("('").append(transac.getDatetime()).append("','")
                    .append(transac.getDescription()).append("','").append(transac.getSumm()).append("','")
                    .append(transac.getAcounts().getIdaccounts()).append("','").append(transac.getIs_income() ? 1 : 0)
                    .append("'),");
        }
        transacsValuesRawQuery.setLength(transacsValuesRawQuery.length() - 1);

        stmt.executeUpdate("ALTER TABLE `transacs` AUTO_INCREMENT = 0;");
        stmt.executeUpdate(transacsValuesRawQuery.toString());

    }

    public static ResultSet getUserByIDNative(int pIdUsers) throws SQLException, IOException {

        Connection connection = TestDBConnection.getInstance();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet result = stmt.executeQuery("SELECT username, userlastname, useremail, userphone FROM USERS WHERE idusers=" + pIdUsers);

        return result;

    }

    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }
}
