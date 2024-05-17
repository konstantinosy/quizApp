/*
The fetch() method is responsible for fetching questions from
the API provided. A connection is made with the API, a get request
is sent to the API, it reads, and then we parse it in a json array.
If the response code is not '200' or in case of any other exceptions
an exception is thrown.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiQuestionsFetcher {
    // URL of the API we are going to use for the questions.
    // Fetches easy questions both true/false and multiple choice questions.
    private static final String API_URL = "https://opentdb.com/api.php?amount=10&difficulty=easy";

    // Method to fetch the data from the API we provided.
    public JsonArray fetch() {
        try {
            // Create a new object of the URL type.
            URL url = new URL(API_URL);
            // Opens the connection to the URL specified.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Then we set the request method to get.
            connection.setRequestMethod("GET");
            // And we set the accept header to json to work with it.
            connection.setRequestProperty("Accept", "application/json");

            // The 200 response code means everything is ok.
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Error code: " + connection.getResponseCode());
            }

            // We create a buffered reader to read the response from the api.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output;
            // We read the response one line at a time, and we append it to the StringBuilder to concatenate.
            StringBuilder response = new StringBuilder();
            while ((output = bufferedReader.readLine()) != null) {
                response.append(output);
            }

            // Close the connection.
            connection.disconnect();

            // We take the response and put it into a json element called json.
            JsonElement json = JsonParser.parseString(response.toString());

            // Lastly, there is a return of the results from the parsed json.
            return json.getAsJsonObject().getAsJsonArray("results");

        } catch (Exception e) {
            throw new RuntimeException("API connection has unfortunately failed...", e);
        }
    }
}