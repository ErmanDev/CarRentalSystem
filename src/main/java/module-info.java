module com.example.carrentalsystem {
    requires javafx.fxml;


    requires org.controlsfx.controls;

    requires com.jfoenix;
    requires java.net.http;

    requires com.google.gson;
    requires java.logging;
    requires org.json;

    opens com.example.carrentalsystem to javafx.fxml;

    exports com.example.carrentalsystem.Controller;
    opens com.example.carrentalsystem.Controller to javafx.fxml;

    exports com.example.carrentalsystem;
    exports com.example.carrentalsystem.Model;
    opens com.example.carrentalsystem.Model to javafx.fxml;
}