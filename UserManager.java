/*
This class build upon the DatabaseConnection.java class in order to
interact with the database. It is responsible for managing users
in order to log in and register. Furthermore, it updates user
information such as the quizzes and the total score a user/player has.
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    // We create an instance of the DatabaseConnection in order to interact with it.
    private final DatabaseConnection databaseConnection;

    public UserManager() {
        // Initialize the DatabaseConnection.
        databaseConnection = new DatabaseConnection();
    }


    // Method for registration of new users.
    public boolean register(String username, String password) {
        // SQL query to insert into the players table a new user.
        String sql = "INSERT INTO players (username, password) VALUES (?, ?)";

        try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {

            // PS stands for prepared statement.
            // Set the username and the new password.
            ps.setString(1, username);
            ps.setString(2, password);
            // Execute the statement.
            ps.executeUpdate();

        } catch (SQLException e) {
            // Display the error message and if an exception is present it returns false.
            System.out.println(e.getMessage());
            return false;
        }
        // Returns true when a new user registers successfully.
        return true;
    }

    // Method for existing users to log in to the application.
    public boolean login(String username, String password) {
        // SQL query in order to select a user from the database's players table.
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";

        try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {
            // Set the username and the password with the prepared statements in the table.
            ps.setString(1, username);
            ps.setString(2, password);
            // Execute and return the result set.
            ResultSet rs = ps.executeQuery();
            // Returns true if the result set is not empty.
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            // Otherwise if there is an exception it prints the error.
            System.out.println(e.getMessage());
        }
        // Returns false if the login procedure is not successful.
        return false;
    }

    // Method for players to update their number of quizzes taken.
    public void updateNumberOfQuizzes(String username) {
        String sql = "UPDATE players SET numberOfQuizzes = numberOfQuizzes + 1 WHERE username = ?";

        try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method for players to update their total quiz score after they finish it.
    public void updateScore(String username, int score) {
        // SQL query for every user to update their total score from the quizzes they have taken.
        String sql = "UPDATE players SET totalScore = totalScore + ? WHERE username = ?";

        try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {
            // Set the score and the username using the following two prepared statements.
            ps.setInt(1, score);
            ps.setString(2, username);
            // Executes the update.
            ps.executeUpdate();

        } catch (SQLException e) {
            // Otherwise, if there is an error it prints it.
            System.out.println(e.getMessage());
        }
    }
}