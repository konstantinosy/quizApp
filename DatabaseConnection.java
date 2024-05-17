/*
This class is responsible to connect to a MySQL database
and return the connection to use it elsewhere in the program.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Instance for the database connection.
    private final Connection connection;

    public DatabaseConnection() {
        try {
            // The database URL we use in order to connect to it.
            String url = "jdbc:mysql://localhost:3306/quiz_app";
            // The database username we use to authenticate...
            String user = "konstantinos";
            // ... and it's corresponding password.
            String password = "12345";
            // Try to connect to the database.
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed:( There was an error when connecting to the database...", e);
        }
    }

    // Getter method in order to retrieve connection.
    public Connection getConnection() {
        return connection;
    }
}