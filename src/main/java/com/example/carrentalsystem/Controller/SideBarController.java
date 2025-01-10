package com.example.carrentalsystem.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SideBarController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;


    @FXML
    private Button dashboardButton;

    @FXML
    private Button inventoryButton;

    @FXML
    private Button bookingsButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button maintenanceLogButton;

    @FXML
    private Button bookingsReportButton;

    @FXML
    private Button incomeExpensesButton;

    @FXML
    private Text dashboardText;

    // Event handler for the "Dashboard" button
    @FXML
    private void dashboard(MouseEvent event) {
    bp.setCenter(ap);
    }

    // Event handler for the "Inventory" button
    @FXML
    private void inventory(MouseEvent event) {
      loadPage("inventory");;
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

    private void loadPage(String page){
        Parent root = null;
try {
    root = FXMLLoader.load(getClass().getResource("/com/example/carrentalsystem/View/"+ page +".fxml"));

} catch (IOException ex){
Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
}
bp.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize any required logic here
        System.out.println("SideBarController initialized");
    }
}
