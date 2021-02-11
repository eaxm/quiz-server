package util;

import java.util.Properties;

/**
 * Stores everything needed to establish a connection to the database
 */
public class DBConnection {

    // Enter your credentials
    private final String URL = "jdbc:postgresql://localhost/quizdb";
    private final String USER = "postgres";
    private final String PASSWORD = "dbpassword";
    private final Properties props;

    private static DBConnection dbConnection = null;


    private DBConnection(){
        props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
    }

    public static DBConnection getInstance(){
        if(dbConnection == null)
            dbConnection = new DBConnection();
        return dbConnection;
    }

    public Properties getProperties(){
        return props;
    }

    public String getURL() {
        return URL;
    }
}
