package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import main.model.BookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingThreeController implements Initializable {

    public BookingModel bookingModel = new BookingModel();

    @FXML
    private Button btnFinish, editDate, btnCancel3;

    @FXML
    private Label bookingDate, seatNum, labelEmployee;

    private String bookedDate, bookedSeat, user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBookingValues();
        setLabels();

    }

    //Creates booking using gathered information

    public void createBooking(ActionEvent actionEvent) {

        bookingModel.addBooking(Main.getCurrentUser(), BookingTwoController.getSeatNum(), BookingController.getTempBookingDateString());

    }

    public void editBookDate(ActionEvent actionEvent) {
    }

    public void editSeatNum(ActionEvent actionEvent) {
    }

    //This is cancel, should delete a temporary booking if there is one.

    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }

    //Could overload to give ability to insert values at will
    private void setBookingValues() {
        bookedDate = BookingController.getTempBookingDateString();
        bookedSeat = BookingTwoController.getSeatNum();
        user = Main.getCurrentUser();
    }

    private void setLabels() {
        bookingDate.setText(bookedDate);
        seatNum.setText(bookedSeat);
        labelEmployee.setText(user);
    }


}
