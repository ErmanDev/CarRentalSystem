package com.example.carrentalsystem.Controller;

import com.example.carrentalsystem.Model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController {

    @FXML
    private TextField CarNoInput;

    @FXML
    private TextField VehicNameInput;

    @FXML
    private TextField RegNameInput;

    @FXML
    private TextField ModelInput;

    @FXML
    private TableView<Vehicle> tableView;

    @FXML
    private TableColumn<Vehicle, String> carNoColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleNameColumn;

    @FXML
    private TableColumn<Vehicle, String> registrationNoColumn;

    @FXML
    private TableColumn<Vehicle, String> modelColumn;

    private ObservableList<Vehicle> vehicleList;

    @FXML
    public void initialize() {
        // Initialize columns
        carNoColumn.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        vehicleNameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        registrationNoColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        // Initialize vehicle list
        vehicleList = FXCollections.observableArrayList();
        tableView.setItems(vehicleList);
    }

    @FXML
    public void handleAddButton(javafx.event.ActionEvent event) {
        // Get input values
        String carNo = CarNoInput.getText();
        String vehicleName = VehicNameInput.getText(); // Updated to match the correct variable name
        String registrationNo = RegNameInput.getText();
        String model = ModelInput.getText();

        // Add new vehicle to the list
        if (!carNo.isEmpty() && !vehicleName.isEmpty() && !registrationNo.isEmpty() && !model.isEmpty()) {
            Vehicle vehicle = new Vehicle(carNo, vehicleName, registrationNo, model);
            vehicleList.add(vehicle);

            // Clear input fields
            CarNoInput.clear();
            VehicNameInput.clear();
            RegNameInput.clear();
            ModelInput.clear();
        } else {
            // Show an alert dialog if fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Fields");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
        }
    }
}
