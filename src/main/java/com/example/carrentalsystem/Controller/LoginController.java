package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize() {
        // Initially hide the TextField and show PasswordField
        passwordTextField.setVisible(false);
        passwordField.setVisible(true);

        // Add listener to rememberMeCheckBox to toggle visibility of password fields
        rememberMeCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Show password in plain text
                passwordField.setVisible(false);
                passwordTextField.setVisible(true);
                passwordTextField.setText(passwordField.getText()); // Transfer text from PasswordField to TextField
            } else {
                // Show password as masked (PasswordField)
                passwordTextField.setVisible(false);
                passwordField.setVisible(true);
                passwordField.setText(passwordTextField.getText()); // Transfer text from TextField to PasswordField
            }
        });
    }

    @FXML
    void handleLogin() {
        String username = usernameField.getText();
        String password = rememberMeCheckBox.isSelected() ? passwordTextField.getText() : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter both username and password.");
            return;
        }

        try {
            // API URL
            URL url = new URL("http://localhost/car-rental-api/login.php"); // Replace with your actual API URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request properties
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create JSON payload
            String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

            // Write JSON to request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }

                    // Handle response (Assume it's JSON)
                    String responseBody = response.toString();
                    if (responseBody.contains("Login successful")) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
                        navigateToHome();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to connect to the server. Response code: " + responseCode);
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    private void navigateToHome() {
        try {
            // Load the home.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/home.fxml"));
            Parent homeRoot = loader.load();

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(new Scene(homeRoot));
            currentStage.setTitle("Home");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load home.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void goToRegister(javafx.scene.input.MouseEvent mouseEvent) {
        // Navigate to the register.fxml scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/register.fxml"));
            Parent registerRoot = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(new Scene(registerRoot));
            currentStage.setTitle("Register");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load register.fxml: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
