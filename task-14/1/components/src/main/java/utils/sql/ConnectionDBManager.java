package utils.sql;

import configs.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDBManager {
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    public ConnectionDBManager(AppConfig cfg) {
        URL = cfg.url;
        USERNAME = cfg.username;
        PASSWORD = cfg.password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
};