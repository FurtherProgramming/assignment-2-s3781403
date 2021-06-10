package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.model.BookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BookingTwoController implements Initializable {

    private static String seatNum;
    public BookingModel bookingModel = new BookingModel();
    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;
    @FXML
    private List<Button> seatsArray = new ArrayList<>();
    @FXML
    private Button btnNext2, btnCancel2, chosenBtn;
    @FXML
    private Label bookDateDisplay;

    private String bookingDate;

    public static String getSeatNum() {
        return seatNum;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingDate = BookingController.getTempBookingDateString();
        seatsArray.addAll(Arrays.asList(seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14));
        bookDateDisplay.setText(bookingDate);
        colourBookedSeats();
        colourLockdownSeats();

    }

    private void colourLockdownSeats() {

        if(bookingModel.selectLockdownSeats(bookingDate) != null) {
            //for loop that matches each returned seat to their respective array thing
        }


    }

    //
    private void colourBookedSeats() {

        if(bookingModel.selectBookedSeats(bookingDate) != null) {
            //similar for loop to lockdown, with different colour.
        }

    }

    //Checks if the user has chosen a button
    //Calls methods to add to DB and redirects to b3
    public void goBookingThree(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/booking_page_3.fxml");
    }

    //This is cancel, should delete a temporary booking if there is one.
    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }

    //Compares actionEvent source with button objects (seats)
    //Selects the appropriate seat and performs actions on it.
    public void chooseSeat(ActionEvent actionEvent) {
        for (int i = 0; i < 20; i++) {
            if (actionEvent.getSource().toString().trim().equals(seatsArray.get(i).toString().trim())) {
                if (chosenBtn != null)
                    chosenBtn.setStyle(""); //For changing the colour back to clear if they choose another seat
                this.chosenBtn = seatsArray.get(i);
                seatNum = this.chosenBtn.getId();
                seatsArray.get(i).setStyle("-fx-background-color: LIGHTBLUE");

            }
        }
    }

}