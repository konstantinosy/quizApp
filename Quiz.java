/*
This class is responsible for a quiz for the players to play.
It uses the ApiQuestionsFetcher to fetch the questions from the API.
There is a prompt for the user to answer the questions, it checks
if it is correct, and it keeps the total score which is returned in
the end
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Scanner;

public class Quiz {
    // Instance of the ApiQuestionsFetcher
    private final ApiQuestionsFetcher apiConnector;

    // Initialise the fetcher.
    public Quiz(ApiQuestionsFetcher apiConnector) {
        this.apiConnector = apiConnector;
    }

    // This method is responsible for starting the quiz, and it is called in the user interface when there is a login.
    public int startQuiz() {
        // Fetch the questions from the api.
        JsonArray questions = apiConnector.fetch();

        // Create a new scanner in order to read from the user.
        Scanner scanner = new Scanner(System.in);

        // Initialise a totalScore variable.
        int totalScore = 0;

        // Create a loop in order to iterate the questions.
        for (int i = 0; i < questions.size(); i++) {
            // A JasonObject is created, and we get the current question.
            JsonObject questionObject = questions.get(i).getAsJsonObject();

            // The following three lines are responsible for fetching the questions, correct answers and incorrect.
            String question = questionObject.get("question").getAsString();
            String correctAnswer = questionObject.get("correct_answer").getAsString();
            JsonArray incorrectAnswers = questionObject.get("incorrect_answers").getAsJsonArray();

            // Prints the questions and the options the player has.
            System.out.println("Quiz question " + (i + 1) + ": " + question);
            System.out.println("Your options:");
            System.out.println("1. " + correctAnswer);
            for (int j = 0; j < incorrectAnswers.size(); j++) {
                System.out.println((j + 2) + ". " + incorrectAnswers.get(j).getAsString());
            }

            // Then the user is prompted to enter his/her answer.
            System.out.print("Please enter your answer (enter the number): ");
            int userAnswer = scanner.nextInt();
            // Consumes leftover characters.
            scanner.nextLine();

            // Check if the user's choice is correct.
            if (userAnswer == 1) {
                System.out.println("Your answer was correct!!");
                totalScore++; // Increment the score if the answer of the player was correct.
            } else {
                System.out.println("Your answer was incorrect and the correct answer was: " + correctAnswer);
            }

            System.out.println();
        }
        // Finally, the totalScore is returned in order to use it.
        return totalScore;
    }
}