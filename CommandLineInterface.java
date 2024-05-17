/*
The CommandLineInterface.java class is the user interface of
the application.
It provides players to perform actions such as registering, logging in,
viewing statistics and exiting the app.
 */

import java.util.Scanner;

public class CommandLineInterface {
    // Instances of the UserManager, Scanner, Quiz and Statistics.
    private final UserManager userManager;
    private final Scanner scanner;
    private final Quiz quiz;
    private final Statistics statistics;

    // Initialise userManager, scanner, quiz and the statistics.
    public CommandLineInterface() {
        userManager = new UserManager();
        scanner = new Scanner(System.in);
        ApiQuestionsFetcher apiConnector = new ApiQuestionsFetcher();
        quiz = new Quiz(apiConnector);
        statistics = new Statistics();
    }

    // This method is used in the Main class in order to start the interface.
    public void startUI() {
        // A while true loop is created in order to loop the menu until the user decides to exit.
        while (true) {
            // The menu is presented with four (4) choices to the user.
            System.out.println("Press (1) to Register.");
            System.out.println("Press (2) to Login.");
            System.out.println("Press (3) to access Statistics.");
            System.out.println("Press (4) to Exit.");
            System.out.print("Enter your choice 1 to 4: ");
            // We read the user input and then a switch is created.
            int choice = scanner.nextInt();
            scanner.nextLine();

            // We have four cases in the switch and the user decides the option.
            switch (choice) {
                // Case (1) is the registration.
                case 1:
                    // Reads the new username.
                    System.out.print("Enter your new username: ");
                    String newUsername = scanner.nextLine();
                    // Reads the new password.
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    // Register method is called from the UserManager class.
                    if (userManager.register(newUsername, newPassword)) {
                        System.out.println("Registration was successful:)");
                    } else {
                        System.out.println("Registration failed :(, Please try again.");
                    }
                    break;
                // Case (2) is the login.
                case 2:
                    // The existing user enters the username.
                    System.out.print("Enter your username: ");
                    String loginUsername = scanner.nextLine();
                    // Same as above but for the password.
                    System.out.print("Enter your password: ");
                    String loginPassword = scanner.nextLine();
                    // The login method from the UserManager class is called.
                    if (userManager.login(loginUsername, loginPassword)) {
                        System.out.println("Welcome, you are now logged in.");
                        // A variable is created in order for the user to be able to play multiple quizzes back to back.
                        boolean playAgain;
                        do {
                            // Gets the score after the quiz is finished.
                            int score = quiz.startQuiz();
                            // Updates the score in the database using the update score method from the UserManager.
                            userManager.updateScore(loginUsername, score);
                            // User is prompted if he wants to play again.
                            System.out.println("You can play the quiz again if you want. Please type (yes) or (no): ");
                            String replayChoice = scanner.nextLine();
                            playAgain = "yes".equalsIgnoreCase(replayChoice);
                            // Updates in the database how many times the user has played.
                            userManager.updateNumberOfQuizzes(loginUsername);
                        } while (playAgain);
                    } else {
                        System.out.println("Login failed :(");
                    }
                    break;
                // Case (3) is Statistics of the players.
                case 3:
                    // The user is displayed with two (2) options to choose from.
                    System.out.println("Statistics:");
                    System.out.println("Press (1) to list the 10 top players that have played the most quizzes.");
                    System.out.println("Press (2) to list the 10 top players with the best average score.");
                    System.out.print("Enter your choice (1) or (2): ");
                    // Scanner is present to choose between the two.
                    int statisticsChoice = scanner.nextInt();
                    scanner.nextLine();

                    // A switch is created in order to choose what method to call from the statistics class.
                    switch (statisticsChoice) {
                        case 1:
                            // quizzesPlayed() method is called.
                            statistics.quizzesPlayed();
                            break;
                        case 2:
                            // averageScore() method is called.
                            statistics.averageScore();
                            break;
                        default:
                            System.out.println("Wrong input. Please try again.");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Program was terminated...");
                    return;
                default:
                    System.out.println("Error. Please try again.");
            }
        }
    }
}