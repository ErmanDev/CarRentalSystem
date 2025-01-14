package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterController {

    @FXML
    private TextField usernameField, passwordField, firstnameField, lastnameField;


    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Username and password cannot be empty.");
            return;
        }

        // Create a JSON object with the registration data
        JSONObject registrationData = new JSONObject();
        registrationData.put("username", username);
        registrationData.put("password", password);
        registrationData.put("firstname",firstname);
registrationData.put("lastname", lastname);
        // Send the registration data to the backend API
        String apiUrl = "http://localhost/car-rental-api/register.php";  // Replace with your actual API URL
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send the request data
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = registrationData.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Handle the success response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Display success message (you can customize this)
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You can now log in.");
                // Optionally, navigate to the login page
                navigateToLogin();

                in.close();
            } else {
                // Handle errors from the backend
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Failed to register. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "Failed to connect to the server: " + e.getMessage());
        }
    }

    public void navigateToLogin() {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/login.fxml"));
            Parent root = loader.load(); // Load the FXML

            // Create a new scene with the loaded root
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) usernameField.getScene().getWindow();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load login.fxml: " + e.getMessage());
        }
    }

    public void gotoLogin(MouseEvent mouseEvent) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/login.fxml"));
            Parent root = loader.load(); // Load the FXML

            // Create a new scene with the loaded root
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load login.fxml: " + e.getMessage());
        }
    }

    // Method to show alerts (you can adjust this as needed)
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
