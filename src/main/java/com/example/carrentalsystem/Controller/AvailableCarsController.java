package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AvailableCarsController {

    @FXML
    private VBox carsContainer; // This VBox will hold all car cards

    public void initialize() {
        // Set up the font
        Font font = Font.font("Arial", 14);

        // Fetch available car data from PHP script asynchronously
        new Thread(() -> {
            try {
                String urlString = "http://localhost/car-rental-api/get_available_cars.php";
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JSONArray jsonResponse = new JSONArray(response.toString());

                // Update the UI with car data on the JavaFX application thread
                Platform.runLater(() -> {
                    if (jsonResponse.length() == 0) {
                        Label noCarsLabel = new Label("No available cars at the moment.");
                        noCarsLabel.setFont(Font.font("Arial", 18));
                        carsContainer.getChildren().add(noCarsLabel);
                    } else {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject car = jsonResponse.getJSONObject(i);
                            VBox carCard = createCarCard(car);
                            carsContainer.getChildren().add(carCard); // Add each car card to the container
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Label errorLabel = new Label("Failed to load available cars.");
                    errorLabel.setFont(Font.font("Arial", 18));
                    carsContainer.getChildren().add(errorLabel);
                });
            }
        }).start();
    }

    // Helper method to create a car card
    private VBox createCarCard(JSONObject car) {
        VBox carCard = new VBox(10);
        carCard.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5;");

        // Car image
        ImageView carImage = new ImageView();
        String photoPath = car.optString("photo");
        if (!photoPath.isEmpty()) {
            Image image = new Image("file:" + photoPath); // Assuming local path or URL
            carImage.setImage(image);
            carImage.setFitHeight(150);
            carImage.setFitWidth(200);
        }

        // Car details
        Label modelLabel = new Label("Model: " + car.optString("model"));
        modelLabel.setFont(Font.font("Arial", 16));

        Label carNoLabel = new Label("Car No: " + car.optString("car_no"));
        carNoLabel.setFont(Font.font("Arial", 14));

        Label priceLabel = new Label("Price: $" + car.optDouble("rental_price_per_day") + " per day");
        priceLabel.setFont(Font.font("Arial", 14));

        // Add image and labels to the car card VBox
        carCard.getChildren().addAll(carImage, modelLabel, carNoLabel, priceLabel);
        return carCard;
    }
}
