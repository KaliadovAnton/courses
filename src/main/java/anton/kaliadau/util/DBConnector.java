package anton.kaliadau.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private static Properties props;
    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_NAME = "jdbc.username";
    private static final String JDBC_PASSWORD = "jdbc.password";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    static {
        var rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        var appConfigPath = rootPath + "database.properties";
        props = new Properties();
            try {
                props.load(new FileInputStream(appConfigPath));
            } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            var url = props.getProperty(JDBC_URL);
            var name = props.getProperty(JDBC_NAME);
            var password = props.getProperty(JDBC_PASSWORD);
            return DriverManager.getConnection(url, name, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
