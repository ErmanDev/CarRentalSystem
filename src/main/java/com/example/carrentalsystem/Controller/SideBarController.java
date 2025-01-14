package com.example.carrentalsystem.Controller;

import com.example.carrentalsystem.Model.CarRecord;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URL;


import java.util.logging.Level;
import java.util.logging.Logger;



public class SideBarController {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;




    @FXML
    private Text dashboardText, carNoDigit, customerNoDigit, carAvailableDigit, carRentedDigit;

    @FXML
    private Label userNameLabel;

    // Event handler for the "Dashboard" button
    @FXML
    private void dashboard(MouseEvent event) {
        bp.setCenter(ap);
    }

    // Event handler for the "Inventory" button
    @FXML
    private void inventory(MouseEvent event) {
        loadPage("inventory");
    }

    // Event handler for the "Bookings" button
    @FXML
    private void bookings(MouseEvent event) {
        loadPage("bookings");
    }

    // Event handler for the "Customer" button
    @FXML
    private void customer() {
        loadPage("customer");
    }

    // Event handler for the "Settings" button
    @FXML
    private void settings() {
        loadPage("settings");
    }

    private static Stage loginStage;

    public static Stage getLoginStage() {
        return loginStage; // Provide access to the login stage
    }
    // Method to load pages based on button clicks
    private void loadPage(String page) {
        Parent root = null;
        try {
            URL fxmlURL = getClass().getResource("/com/example/carrentalsystem/View/" + page + ".fxml");
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
        userNameLabel.setText(username);
    }


    private void fetchCarData() {
        Task<Void> fetchDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("http://127.0.0.1/car-rental-api/get_count.php"))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println("HTTP Response Code: " + response.statusCode());
                    System.out.println("Response Body: " + response.body());

                    if (response.statusCode() == 200) {
                        JSONObject jsonResponse = new JSONObject(response.body());

                        if (jsonResponse.optBoolean("success", false)) {
                            JSONObject data = jsonResponse.optJSONObject("data");
                            if (data != null) {
                                // Fetch data from the 'data' object
                                int carCount = Integer.parseInt(data.optString("car_count", "0"));
                                int customerCount = Integer.parseInt(data.optString("customer_count", "0"));
                                int availableCars = Integer.parseInt(data.optString("available_cars", "0"));

                                // Extract rented cars data, which is an array
                                JSONArray rentedCarsArray = data.optJSONArray("rented_cars");
                                int rentedCarCount;
                                if (rentedCarsArray != null) {
                                    rentedCarCount = rentedCarsArray.length(); // Count the number of rented cars
                                } else {
                                    rentedCarCount = 0;
                                }

                                // Update the UI with fetched data
                                Platform.runLater(() -> {
                                    carNoDigit.setText(String.valueOf(carCount));
                                    customerNoDigit.setText(String.valueOf(customerCount));
                                    carAvailableDigit.setText(String.valueOf(availableCars));
                                    carRentedDigit.setText(String.valueOf(rentedCarCount));

                                    System.out.println("UI updated successfully.");
                                });
                            } else {
                                System.err.println("No 'data' object found in the response.");
                            }
                        } else {
                            System.err.println("API response indicates failure: " + jsonResponse.optString("message"));
                        }
                    } else {
                        System.err.println("Failed to fetch data. HTTP Status: " + response.statusCode());
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching car data: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };

        new Thread(fetchDataTask).start();
    }


    private void fetchCurrentUser() {
        Task<Void> fetchDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("http://127.0.0.1/car-rental-api/get_user.php"))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println("HTTP Response Code: " + response.statusCode());
                    System.out.println("Response Body: " + response.body());

                    if (response.statusCode() == 200) {
                        JSONObject jsonResponse = new JSONObject(response.body());

                        if (jsonResponse.optBoolean("success", false)) {
                            JSONObject data = jsonResponse.optJSONObject("data");
                            if (data != null) {
                                String username = data.optString("username", "Unknown User");

                                // Update the UI on the JavaFX Application Thread
                                Platform.runLater(() -> {
                                    userNameLabel.setText("Welcome, " + username + "!");
                                });
                            } else {
                                System.err.println("No 'data' object found in the response.");
                            }
                        } else {
                            System.err.println("API response indicates failure: " + jsonResponse.optString("message"));
                        }
                    } else {
                        System.err.println("Failed to fetch data. HTTP Status: " + response.statusCode());
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching user data: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };

        new Thread(fetchDataTask).start();
    }



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

        Alert alert = new Alert(alertType);


        alert.setTitle(title);


        alert.setContentText(message);


        alert.setHeaderText(null);


        alert.showAndWait();
    }


    @FXML
    public void initialize() {
fetchCurrentUser();
        fetchCarData();


    }

}


