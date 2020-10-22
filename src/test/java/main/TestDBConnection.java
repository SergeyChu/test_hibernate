package main;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestDBConnection {

    private static volatile Connection connection;

    public static Connection getInstance() throws IOException, SQLException {

        if (connection == null) {

            synchronized (TestDBConnection.class) {

                if (connection == null) {

                    FileInputStream propFis = new FileInputStream("src/test/resources/connection.prop");
                    Properties p =new Properties ();
                    p.load(propFis);
                    String url = p.getProperty("url");
                    String user = p.getProperty("user");
                    String password = p.getProperty("password");

                    connection = DriverManager.getConnection(url, user, password);
                }
            }
        }

        return connection;
    }

}
