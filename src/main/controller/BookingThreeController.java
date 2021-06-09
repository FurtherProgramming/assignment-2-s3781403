package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingThreeController implements Initializable {

    @FXML
    private Button btnFinish, editDate, btnCancel3;

    @FXML
    private Label bookingDate, seatNum, labelEmployee;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingDate.setText(BookingController.getTempBookingDateString());
        seatNum.setText(BookingTwoController.getSeatNum());
        labelEmployee.setText(Main.getCurrentUser());
    }

    //Creates booking using gathered information
    public void createBooking(ActionEvent actionEvent) {
    }

    public void editBookDate(ActionEvent actionEvent) {
    }

    public void editSeatNum(ActionEvent actionEvent) {
    }

    //This is cancel, should delete a temporary booking if there is one.
    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }


}
