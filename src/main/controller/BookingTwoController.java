package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.model.BookingModel;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BookingTwoController implements Initializable {

    private static String seatNum;
    public BookingModel bookingModel = new BookingModel();
    public UserModel userModel = new UserModel();
    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;
    @FXML
    private List<Button> seatsArray = new ArrayList<>();
    @FXML
    private Button btnNext2, btnCancel2, chosenBtn;
    @FXML
    private Label bookDateDisplay, warnings;

    private String bookingDate;

    public static String getSeatNum() {
        return seatNum;
    }

    public static void removeSeatNum() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingDate = BookingController.getTempBookingDateString();
        seatsArray.addAll(Arrays.asList(seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14));
        bookDateDisplay.setText(bookingDate);
        initializeUnavailableSeats();
        initializePreviousSeat();
    }

    private void initializePreviousSeat() {
        String previousSeat = userModel.selectPreviousSeat();

        for (int i = 0; i < 20; i++) {
            if (previousSeat.equals(seatsArray.get(i).getId().trim())) {
                seatsArray.get(i).setStyle("-fx-background-color: maroon");
                seatsArray.get(i).setDisable(true);
            }
        }
    }


    private void initializeUnavailableSeats() {
        ArrayList<String> covidLockedSeats = new ArrayList<>();
        ArrayList<String> bookedSeats = new ArrayList<>();
        try {
            covidLockedSeats = bookingModel.selectCovidSeats(bookingDate);
            bookedSeats = bookingModel.selectBookedSeats(bookingDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colourSeats(covidLockedSeats, bookedSeats);
    }

    //Nested for loops to find unavailable seats in the array of seats, then disable/colour them based on the style of availability.
    private void colourSeats(ArrayList<String> covidLockedSeats, ArrayList<String> bookedSeats) {
        for (String covidLockedSeat : covidLockedSeats) {
            for (Button button : seatsArray) {
                if (covidLockedSeat.equals(button.getId().trim())) {
                    button.setStyle("-fx-background-color: orange");
                    button.setDisable(true);
                }
            }
        }
        for (String bookedSeat : bookedSeats) {
            for (Button button : seatsArray) {
                if (bookedSeat.equals(button.getId().trim())) {
                    button.setStyle("-fx-background-color: LIGHTCORAL");
                    button.setDisable(true);
                }
            }
        }
    }

    //Checks if the user has chosen a button
    public void goBookingThree(ActionEvent actionEvent) throws IOException {
        if (chosenBtn != null) {
            SceneController.drawScene("ui/staff/booking_page_3.fxml");
        } else {
            warnings.setVisible(true);
        }

    }

    //This is cancel, should unset temp values.
    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }

    //Compares actionEvent source with array of button objects (seats)
    //Selects the appropriate seat and colours it when clicked (if possible).
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