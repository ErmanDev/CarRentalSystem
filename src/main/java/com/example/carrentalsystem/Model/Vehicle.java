package com.example.carrentalsystem.Model;

public class Vehicle {
    private String carNo;
    private String vehicleName;
    private String registrationNo;
    private String model;

    public Vehicle(String carNo, String vehicleName, String registrationNo, String model) {
        this.carNo = carNo;
        this.vehicleName = vehicleName;
        this.registrationNo = registrationNo;
        this.model = model;
    }

    public String getCarNo() {
        return carNo;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getModel() {
        return model;
    }
}
