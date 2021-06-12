package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.model.BookingModel;
import main.model.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingButtons {
    public BookingModel bookingModel = new BookingModel();
    public static UserModel userModel = new UserModel();
    @FXML
    protected Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;
    @FXML
    protected static List<Button> seatsArray = new ArrayList<>();
    private static Button chosenBtn;
    protected static String seatNum;

    protected void createButtonsArray() {
        seatsArray.addAll(Arrays.asList(seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14));
    }

    protected void initializeUnavailableSeats() {
        ArrayList<String> covidLockedSeats = new ArrayList<>();
        ArrayList<String> bookedSeats = new ArrayList<>();
        try {
            covidLockedSeats = bookingModel.selectCovidSeats(bookingModel.tempBookingDate.toString());
            bookedSeats = bookingModel.selectBookedSeats(bookingModel.tempBookingDate.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colourSeats(covidLockedSeats, bookedSeats);
    }

    protected static void initializePreviousSeat() {
        String previousSeat = null;
        try {
            previousSeat = userModel.selectPreviousSeat();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++) {
            if (previousSeat.equals(seatsArray.get(i).getId().trim())) {
                seatsArray.get(i).setStyle("-fx-background-color: maroon");
                seatsArray.get(i).setDisable(true);
            }
        }
    }

    protected void colourSeats(ArrayList<String> covidLockedSeats, ArrayList<String> bookedSeats) {
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

    //Compares actionEvent source with array of button objects (seats)
    //Selects the appropriate seat and colours it when clicked (if possible).
    protected static void chooseSeatFunction(ActionEvent actionEvent) {
        for (int i = 0; i < 20; i++) {
            if (actionEvent.getSource().toString().trim().equals(seatsArray.get(i).toString().trim())) {
                if (chosenBtn != null)
                    chosenBtn.setStyle(""); //For changing the colour back to clear if they choose another seat
                chosenBtn = seatsArray.get(i);
                seatNum = chosenBtn.getId();
                seatsArray.get(i).setStyle("-fx-background-color: LIGHTBLUE");
            }
        }
    }


}
