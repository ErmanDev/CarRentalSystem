package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField licenseField;

    @FXML
    public void initialize() {
        // Replace with the actual URL of your PHP API
        String apiUrl = "http://localhost/car-rental-api/display_user_info.php";

        // Fetch user data
        fetchUserData(apiUrl);
    }

    private void fetchUserData(String apiUrl) {
        try {
            // Create a URL object
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method (before establishing the connection)
            connection.setRequestMethod("GET");

            // Set request headers (optional, if needed for session or content type)
            connection.setRequestProperty("Accept", "application/json");

            // Ensure connection is made after setting properties
            connection.connect();  // Connect after setting properties

            // Check if the response code is 200 (OK)
            if (connection.getResponseCode() == 200) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                if (jsonResponse.has("error")) {
                    // Handle error response
                    System.err.println(jsonResponse.getString("error"));
                } else {
                    // Extract user data from response
                    String firstname = jsonResponse.optString("firstname", "N/A");
                    String lastname = jsonResponse.optString("lastname", "N/A");
                    boolean license = jsonResponse.optBoolean("license", false);

                    // Update the TextField values
                    firstNameField.setText(firstname);
                    lastNameField.setText(lastname);
                    licenseField.setText(license ? "Yes" : "No");
                }
            } else {
                System.err.println("Failed to fetch user data. HTTP Code: " + connection.getResponseCode());
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
