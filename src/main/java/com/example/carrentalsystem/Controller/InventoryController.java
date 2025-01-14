package com.example.carrentalsystem.Controller;

import com.example.carrentalsystem.Model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class InventoryController {
    @FXML
    private void handleAddButton() {
        // Add your logic here to handle the button click
        System.out.println("Add button clicked!");
    }



    @FXML
    private TableView<Car> tableView; // The TableView in FXML
    @FXML
    private TableColumn<Car, String> carNoColumn;
    @FXML
    private TableColumn<Car, String> vehicleNameColumn;
    @FXML
    private TableColumn<Car, String> registrationNoColumn;
    @FXML
    private TableColumn<Car, String> photoColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;

    // Method to handle the data fetching and populating the table
    public void fetchCarData() {
        String urlString = "http://localhost/car-rental-api/retrieve_cars.php"; // Replace with your API URL
        HttpURLConnection connection = null;
        ObservableList<Car> carData = FXCollections.observableArrayList();

        try {
            // Create a URL object from the given string
            URL url = new URL(urlString);

            // Open a connection to the URL
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Use GET method for fetching data
            connection.setConnectTimeout(5000); // Set timeout in milliseconds
            connection.setReadTimeout(5000); // Set read timeout in milliseconds

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // Get the full response string
            String jsonResponse = responseBuilder.toString();

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String status = jsonObject.getString("status");

            if ("success".equals(status)) {
                JSONArray carsArray = jsonObject.getJSONArray("data");

                // Loop through the array and process each car
                for (int i = 0; i < carsArray.length(); i++) {
                    JSONObject car = carsArray.getJSONObject(i);

                    String carNo = car.getString("car_no");
                    String vehicleName = car.getString("vehicle_name");
                    String registrationNo = car.getString("registration_no");
                    String photo = car.getString("photo");
                    String model = car.getString("model");

                    // Add each car to the ObservableList
                    carData.add(new Car(carNo, vehicleName, registrationNo, photo, model));
                }

                // Populate the table with data
                tableView.setItems(carData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Initialize method to set the cell factories for the columns
    @FXML
    public void initialize() {
        carNoColumn.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        vehicleNameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        registrationNoColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("photo"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        // Fetch car data when the controller is initialized
        fetchCarData();
    }
}
