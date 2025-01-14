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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginController {
    private Stage currentStage;


    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private TextField usernameField;

    @FXML
    private void jCheckBox() {
        if (rememberMeCheckBox.isSelected()) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);
        }
    }





    @FXML
    void handleLogin() {
        String username = usernameField.getText();
        String password = rememberMeCheckBox.isSelected() ? passwordTextField.getText() : passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter both username and password.");
            return;
        }

        HttpURLConnection connection = null;

        try {
            // API URL
            URL url = new URL("http://localhost/car-rental-api/login.php"); // Replace with your actual API URL
            connection = (HttpURLConnection) url.openConnection();

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
                // Read and parse the response
                StringBuilder response = new StringBuilder();
                try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }
                }

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.has("message") && "Login successful".equals(jsonResponse.getString("message"))) {
                    String role = jsonResponse.optString("role", "client"); // Default to "client" if not provided

                    // Show success alert
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");

                    // Load the appropriate FXML based on role
                    Stage currentStage = (Stage) usernameField.getScene().getWindow();
                    FXMLLoader loader;
                    Parent root;

                    if ("client".equals(role)) {
                        loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/client/client.fxml"));
                        root = loader.load();
                        ClientController clientController = loader.getController();
                        clientController.setUsername(username);
                    } else {
                        loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/home.fxml"));
                        root = loader.load();
                        SideBarController sideBarController = loader.getController();
                        sideBarController.setUsername(username);
                    }

                    // Set the new scene
                    currentStage.setScene(new Scene(root));
                    currentStage.centerOnScreen();
                    currentStage.show();

                } else {
                    // Login failed
                    String errorMessage = jsonResponse.optString("error", "Invalid username or password.");
                    showAlert(Alert.AlertType.ERROR, "Login Failed", errorMessage);
                }
            } else {
                // Handle server errors
                showAlert(Alert.AlertType.ERROR, "Server Error", "Server returned response code: " + responseCode);
            }
        } catch (IOException e) {
            // Handle connection and I/O errors
            showAlert(Alert.AlertType.ERROR, "Connection Error", "Could not connect to the server: " + e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            // Handle JSON parsing errors
            showAlert(Alert.AlertType.ERROR, "Response Error", "Could not parse server response: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle unexpected exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure the connection is closed
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    private String extractRoleFromResponse(String responseBody) {

        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("role");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void navigateToScene(Stage stage, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/" + fxmlFile));
            Parent root = loader.load();

            // Set the scene only after successfully loading the FXML file
            stage.setScene(new Scene(root));


            // Center the stage on the screen
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            // Handle the exception and show an alert if navigation fails
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load " + fxmlFile + ": " + e.getMessage());
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
