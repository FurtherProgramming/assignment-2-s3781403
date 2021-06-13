package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.controller.SceneController;
import main.model.AdminBookingModel;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AdminBookingManagementController implements Initializable {

    @FXML
    private Label lblBookingDate, lblSeatNum, lblEmplName, lblEmployeeID, warning;

    @FXML
    private Button btnReturnHome, btnConfirm, btnReject, btnNextEmpl;
    private HashMap<String, Object> bookingInformation = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateButtons();
        getBookingInformation();
    }

    private void initiateButtons() {
        btnReturnHome.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
        btnConfirm.setOnAction((ActionEvent e) -> confirmStaffBooking());
        btnReject.setOnAction((ActionEvent e) -> rejectStaffBooking());
        btnNextEmpl.setOnAction((ActionEvent e) -> getBookingInformation());
    }

    private void getBookingInformation() {
        AdminBookingModel adminBookingModel = new AdminBookingModel();
        String dateToCheck = getNextDay();
        btnConfirm.setDisable(false);
        btnNextEmpl.setDisable(true);
        try {
            bookingInformation = adminBookingModel.getBookingInformation(dateToCheck);
            if (bookingInformation.containsKey("empty")) {
                generateAlert("There are no more bookings for tomorrow", "-fx-text-fill: red");
                disableButtons();
                clearLabels();
            } else {
                drawLabels();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void disableButtons() {
        btnConfirm.setDisable(true);
        btnReject.setDisable(true);
        btnNextEmpl.setDisable(true);
    }

    private void drawLabels() {
        lblSeatNum.setText(bookingInformation.get("seat").toString());
        lblBookingDate.setText(bookingInformation.get("bookingDate").toString());
        lblEmployeeID.setText("Employee ID: " + bookingInformation.get("employeeID").toString());
        lblEmplName.setText(findEmployeeName());
    }

    private String findEmployeeName() {
        UserModel userModel = new UserModel();
        String employeeName = null;
        try {
            employeeName = userModel.getFullNameByID((int) bookingInformation.get("employeeID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeName;
    }

    private String getNextDay() {
        LocalDate currentDay = LocalDate.now();
        String tomorrow = (currentDay.plusDays(1)).format(DateTimeFormatter.ISO_DATE);
        return tomorrow;
    }

    private void confirmStaffBooking() {
        AdminBookingModel adminBookingModel = new AdminBookingModel();
        try {
            adminBookingModel.updateBookingStatus("confirmed", (int) bookingInformation.get("employeeID"), bookingInformation.get("bookingDate").toString());
            generateAlert("Employee booking confirmed", "-fx-text-fill: green");
            btnConfirm.setDisable(true);
            btnNextEmpl.setDisable(false);
        } catch (SQLException e) {
            generateAlert("Confirmation unsuccessful", "-fx-text-fill: red");
            e.printStackTrace();
        }
    }

    private void rejectStaffBooking() {
        AdminBookingModel adminBookingModel = new AdminBookingModel();
        SceneController.AlertBox.draw("Delete Booking", "Really delete booking?");

        if (SceneController.AlertBox.answer) {
            try {
                adminBookingModel.deleteBooking((int) bookingInformation.get("employeeID"));
                getBookingInformation();
            } catch (SQLException e) {
                generateAlert("Deletion unsuccessful", "-fx-text-fill: red");
                e.printStackTrace();
            }
        }
    }

    private void redirect(String redirectLocation) {
        try {
            SceneController.drawScene(redirectLocation);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    //TODO This is shared between a number of classes, it should be put somewhere else
    // -> i.e. place it in SceneController, and then pass in the label every time as
    // well as desired values -> under the gun, temporary fix
    private void generateAlert(String text, String colour) {
        warning.setText(text);
        warning.setStyle(colour);
        warning.setVisible(true);
    }

    private void clearLabels() {
        lblEmplName.setText("");
        lblBookingDate.setText("");
        lblEmployeeID.setText("");
        lblSeatNum.setText("");
    }

}
