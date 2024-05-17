/*
The Statistics.java class is responsible for providing users
statistics such as the top 10 players that have played the most
quizzes and the players that have the best average scores.
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Statistics {
    // An instance of the DatabaseConnection to interact with it.
    private final DatabaseConnection databaseConnection;

    public Statistics() {
        // Start the database connection.
        databaseConnection = new DatabaseConnection();
    }

    // Method to show the top 10 players that have played the most quizzes.
    public void quizzesPlayed() {
        // SQL query to select from the table players the top numbers from numberOfQuizzes and sort them in a descending manner.
        String sql = "SELECT username FROM players ORDER BY numberOfQuizzes DESC LIMIT 10";

        try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {
            // Execution of the query and we get the result set.
            ResultSet rs = ps.executeQuery();

            // Prints for the user to see.
            System.out.println("Top 10 players by quizzes played:");
            // A while loop is made in order to iterate the result set and print each player.
            while (rs.next()) {
                System.out.println(rs.getString("username"));
            }

        } catch (SQLException e) {
            // Prints error message if an exception arises.
            System.out.println(e.getMessage());
        }
    }

    // A method to display the 10 best players according to their average score.
    public void averageScore() {
        // SQL statement in order to select the username and divide two columns to extract the average score. Ordering is done in a descending manner.
        String sql = "SELECT username, (totalScore / numberOfQuizzes) as averageScore FROM players ORDER BY averageScore DESC LIMIT 10";

        try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {
            // Execute and create the result set.
            ResultSet rs = ps.executeQuery();
            // Prints an appropriate message for the user to see.
            System.out.println("Top 10 players sorted by average score:");
            // Create a while loop to iterate through the result set and see the players and their average score.
            while (rs.next()) {
                System.out.println(rs.getString("username") + " has an average score of: " + rs.getDouble("averageScore"));
            }

        } catch (SQLException e) {
            // If an error arises it prints it.
            System.out.println(e.getMessage());
        }
    }
}