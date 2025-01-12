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
import javafx.scene.Parent;
import com.google.gson.JsonObject;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.json.JSONObject;

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
    private JFXButton dashboardButton, inventoryButton, bookingsButton, customerButton, settingsButton, transactionsButton, maintenanceLogButton, bookingsReportButton, incomeExpensesButton;

    @FXML
    private Text dashboardText, carNoDigit, customerNoDigit, carAvailableDigit;

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

    // Event handler for the "Transactions" button
    @FXML
    private void transactions() {
        dashboardText.setText("Transactions Selected");
        System.out.println("Transactions button clicked");
    }

    // Event handler for the "Maintenance Log" button
    @FXML
    private void maintenanceLog() {
        dashboardText.setText("Maintenance Log Selected");
        System.out.println("Maintenance Log button clicked");
    }

    // Event handler for the "Bookings Report" button
    @FXML
    private void bookingsReport() {
        dashboardText.setText("Bookings Report Selected");
        System.out.println("Bookings Report button clicked");
    }

    // Event handler for the "Income & Expenses" button
    @FXML
    private void incomeExpenses() {
        dashboardText.setText("Income & Expenses Selected");
        System.out.println("Income & Expenses button clicked");
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

    // Fetch car count, customer count, and available cars data
    private void fetchCarData() {
        Task<Void> fetchDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("http://127.0.0.1/car-rental-api/get_car_count.php"))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println("HTTP Response Code: " + response.statusCode());
                    System.out.println("Response Body: " + response.body());

                    if (response.statusCode() == 200) {
                        JSONObject jsonResponse = new JSONObject(response.body());
                        int carCount = jsonResponse.optInt("car_count", 0);
                        int customerCount = jsonResponse.optInt("customer_count", 0);
                        int availableCars = jsonResponse.optInt("available_cars", 0);

                        Platform.runLater(() -> {
                            carNoDigit.setText(String.valueOf(carCount));
                            customerNoDigit.setText(String.valueOf(customerCount));
                            carAvailableDigit.setText(String.valueOf(availableCars));
                            System.out.println("UI updated successfully.");
                        });
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


    /**
     * Prepare JSON data and print it to the console.
     */
    public void prepareJsonData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", "value"); // Replace "key" and "value" with your actual data
        System.out.println(jsonObject.toString()); // This prints the JSON to the console
    }


    // Call fetchCarData when the controller is initialized
    @FXML
    public void initialize() {
        fetchCarData();  // Fetch data when the controller is initialized
    }
}


