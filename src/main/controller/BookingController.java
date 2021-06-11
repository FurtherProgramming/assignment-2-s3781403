package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import main.model.BookingModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//TODO can't select date in past

public class BookingController implements Initializable {

    public BookingModel bookingModel = new BookingModel();

    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;
    @FXML
    private List<Button> seatsArray = new ArrayList<>();
    @FXML
    private Button btnNext2, btnCancel2, chosenBtn;
    @FXML
    private DatePicker bookingDate;
    @FXML
    private Label bookDateDisplay, labelWarning;

    private static LocalDate tempBookingDate;

    public static void setTempDate() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        bookingDate.setValue(LocalDate.now());
        //DB connection in here too

    }

    public void checkBookingDate(ActionEvent actionEvent) {

    }

    //This is cancel, should delete a temporary booking if there is one.
    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }

    //Checks if user has selected a date (preferably in the future)
    //Sets an ID in the bookings page, adds a temporary booking
    //TODO Make "cancel" delete the temporary booking that matches the ID
    public void goBookingTwo(ActionEvent actionEvent) throws IOException {
//        bookingModel.setBookedDate(tempBookingDate);
        if(tempBookingDate!=null) {
            SceneController.drawScene("ui/staff/booking_page_2.fxml");
        } else {
            labelWarning.setVisible(true);
        }

//        bookDateDisplay.setText(tempBookingDate.toString());
    }

    //Booking page 1 temporary set value for later bookings.
    public void setBookingDate(ActionEvent actionEvent) {
        tempBookingDate=bookingDate.getValue();
        System.out.println(bookingDate.getValue());
    }

    public static String getTempBookingDateString() {
        return tempBookingDate.toString();
    }


}
