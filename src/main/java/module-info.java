module com.example.carrentalsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.logging;
    requires java.desktop;

    opens com.example.carrentalsystem to javafx.fxml;

    exports com.example.carrentalsystem.Controller;
    opens com.example.carrentalsystem.Controller to javafx.fxml;

    exports com.example.carrentalsystem;
}