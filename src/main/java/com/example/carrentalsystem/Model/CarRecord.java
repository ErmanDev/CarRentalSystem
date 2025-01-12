package com.example.carrentalsystem.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CarRecord {

    private final StringProperty carNoColumn;
    private final StringProperty vehicleNameColumn;
    private final StringProperty registrationNoColumn;
    private final StringProperty photoColumn;
    private final StringProperty modelColumn;
    private final StringProperty actionsColumn;

    public CarRecord(String carNo, String vehicleName, String registrationNo, String photo, String model, String actions) {
        this.carNoColumn = new SimpleStringProperty(carNo);
        this.vehicleNameColumn = new SimpleStringProperty(vehicleName);
        this.registrationNoColumn = new SimpleStringProperty(registrationNo);
        this.photoColumn = new SimpleStringProperty(photo);
        this.modelColumn = new SimpleStringProperty(model);
        this.actionsColumn = new SimpleStringProperty(actions);
    }

    // Getters for the properties
    public StringProperty carNoColumnProperty() {
        return carNoColumn;
    }

    public StringProperty vehicleNameColumnProperty() {
        return vehicleNameColumn;
    }

    public StringProperty registrationNoColumnProperty() {
        return registrationNoColumn;
    }

    public StringProperty photoColumnProperty() {
        return photoColumn;
    }

    public StringProperty modelColumnProperty() {
        return modelColumn;
    }

    public StringProperty actionsColumnProperty() {
        return actionsColumn;
    }
}
