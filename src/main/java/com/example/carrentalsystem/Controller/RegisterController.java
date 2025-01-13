package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegisterController {


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
