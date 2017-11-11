package dbsettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection(String connection, String username, String password) {
        Connection resultConnection = null;

        try {
            resultConnection = DriverManager.getConnection(connection, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultConnection;
    }
}
