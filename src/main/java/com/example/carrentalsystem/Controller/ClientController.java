package com.example.carrentalsystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientController {


    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;


    @FXML
    private Label userNameLabel;





    @FXML
    private void logout(javafx.scene.input.MouseEvent mouseEvent) {
        // Navigate to the login.fxml scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/carrentalsystem/View/login.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage from the mouse event
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(loginRoot));
            currentStage.setTitle("Login");
            currentStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load login.fxml: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        // Create an alert with the specified alert type (ERROR, INFORMATION, etc.)
        Alert alert = new Alert(alertType);

        // Set the title of the alert window
        alert.setTitle(title);

        // Set the content text (message) of the alert
        alert.setContentText(message);

        // Optionally, you can add a header (use null to not display a header)
        alert.setHeaderText(null);

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }

    @FXML
    private void myHome(MouseEvent event) {
        bp.setCenter(ap);
    }


    @FXML void availableCars(){
        loadPage("available_cars");
    }

   @FXML
   private void myBookings(){
        loadPage("my_bookings");
   }



    @FXML
    private void profile(){
        loadPage("profile");
    }

    @FXML
    private void support(){
        loadPage("support");
    }


    private void loadPage(String page) {
        Parent root = null;
        try {
            URL fxmlURL = getClass().getResource("/com/example/carrentalsystem/View/client/" + page + ".fxml");
            if (fxmlURL == null) {
                throw new IOException("FXML file not found for page: " + page);
            }
            root = FXMLLoader.load(fxmlURL);
        } catch (IOException ex) {
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, "Error loading page: " + page, ex);
        }
        if (root != null) {
            bp.setCenter(root);
        } else {
            System.err.println("Root is null. Cannot set center for page: " + page);
        }
    }

    public void setUsername(String username) {
        userNameLabel.setText("Welcome! "+ username);
    }

}
