package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

public class CarCardController {

    @FXML
    private ImageView carImage;
    @FXML
    private Label modelLabel;
    @FXML
    private Label availabilityLabel;
    @FXML
    private Label carNoLabel;
    @FXML
    private Label priceLabel;

    // Method to populate the card with data
    public void setCarDetails(String model, String status, String carNo, double price, String photoPath) {
        modelLabel.setText("Car Model: " + model);
        availabilityLabel.setText("Availability: " + status);
        carNoLabel.setText("Car No: " + carNo);
        priceLabel.setText("Price: $" + price + " per day");

        if (photoPath != null && !photoPath.isEmpty()) {
            Image image = new Image("file:" + photoPath); // Assuming the photoPath is a local file path
            carImage.setImage(image);
        }
    }
}
