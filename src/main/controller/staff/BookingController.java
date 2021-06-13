package main.controller.staff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import main.Main;
import main.controller.SceneController;
import main.model.BookingModel;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

//TODO can't select date in past

public class BookingController implements Initializable {

    public BookingModel bookingModel = new BookingModel();
    public UserModel userModel = new UserModel();

    @FXML
    private Button btnNextStep, btnCancel, chosenBtn;
    @FXML
    private DatePicker bookingDate;
    @FXML
    private Label bookDateDisplay, labelWarning;

    private boolean userHasBooking;
    private int employeeID;
    private static LocalDate tempBookingDate;

    //TODO move all temporary variables from their controllers into the model where they belong

    public static String getTempBookingDateString() {
        return tempBookingDate.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEmployeeID();
        checkUserHasBooking();
        tempBookingDate=null;
    }

    //Checks if the current user has a booking
    private void checkUserHasBooking() {
        try {
            userHasBooking = bookingModel.checkUserBooking(employeeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userHasBooking) {
            bookingDate.setDisable(true);
            btnNextStep.setDisable(true);
            labelWarning.setText("You already have a booking!");
            labelWarning.setVisible(true);
        }
    }

    //Sets this classes employeeID to the logged in users ID
    private void setEmployeeID() {
        try {
            employeeID = userModel.getEmployeeID(Main.getCurrentUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkBookingDate(ActionEvent actionEvent) {

    }

    //This is cancel, should delete a temporary booking if there is one.
    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }

    //Checks if user has selected a date (preferably in the future)
    //Sets an ID in the bookings page, adds a temporary booking
    //TODO add multiple labels for different warnings for correct styling
    public void goBookingTwo(ActionEvent actionEvent) throws IOException {
//        bookingModel.setBookedDate(tempBookingDate);
        if (tempBookingDate != null && tempBookingDate.isEqual(LocalDate.now()) || tempBookingDate.isAfter(LocalDate.now())) {
            SceneController.drawScene("ui/staff/booking_page_2.fxml");
        } else if (tempBookingDate.isBefore(LocalDate.now())) {
            labelWarning.setText("Please select a date in the future!");
            labelWarning.setVisible(true);
        } else if (tempBookingDate==null){
            labelWarning.setVisible(true);
        }

    }

    //For later bookings
    public void setBookingDate(ActionEvent actionEvent) {
        tempBookingDate = bookingDate.getValue();
    }

    public static void removeTempDate() {
        tempBookingDate = null;
    }

}
