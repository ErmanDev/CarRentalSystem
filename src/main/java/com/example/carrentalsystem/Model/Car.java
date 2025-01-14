package com.example.carrentalsystem.Model;

public class Car {
    private String carNo;
    private String vehicleName;
    private String registrationNo;
    private String photo;
    private String model;

    // Constructor
    public Car(String carNo, String vehicleName, String registrationNo, String photo, String model) {
        this.carNo = carNo;
        this.vehicleName = vehicleName;
        this.registrationNo = registrationNo;
        this.photo = photo;
        this.model = model;
    }

    // Getters and setters
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
