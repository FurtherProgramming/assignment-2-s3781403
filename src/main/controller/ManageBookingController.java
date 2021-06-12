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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ManageBookingController extends BookingButtons implements Initializable  {

    public BookingModel bookingModel = new BookingModel();
    public UserModel userModel = new UserModel();
    @FXML
    private Button confirmChange, deleteBooking, cancel, returnHome;
    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;
    @FXML
    private List<Button> seatsArray = new ArrayList<>();
    private Button chosenBtn;
    @FXML
    private Label noBookingsLabel, bookDateDisplay;

    /*
        TODO The method for the buttons on the page could be extended from another class
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
        seatsArray.addAll(Arrays.asList(seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14));
        bookDateDisplay.setText("swag");


        //if userHasBooking();
        //    updateBookingTable();
        //else
        //    disableTable();
        //    updateLabels();
    }



    private void initializeButtons() {
        confirmChange.setOnAction((ActionEvent e) ->
                        SceneController.alertBox("Confirm Changes?", "Update your bookings information?")
                //If alert box returns true: update the database with changes
                //else, close alert box
        );

        deleteBooking.setOnAction((ActionEvent e) ->
                        SceneController.alertBox("Delete Booking?", "This action cannot be undone")
                //If alert box returns true: delete the entry from the database
                //Else: close the alert box
        );

        cancel.setOnAction((ActionEvent e) ->
                System.out.println("cancelled")
        );

        returnHome.setOnAction((ActionEvent e) ->
                {
                    try {
                        SceneController.drawScene("ui/staff/landingpage.fxml");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        );
    }

    public void chooseSeat(ActionEvent actionEvent) {
        for (int i = 0; i < 20; i++) {
            if (actionEvent.getSource().toString().trim().equals(seatsArray.get(i).toString().trim())) {
                if (chosenBtn != null)
                    chosenBtn.setStyle(""); //For changing the colour back to clear if they choose another seat
                this.chosenBtn = seatsArray.get(i);
//                seatNum = this.chosenBtn.getId();
                seatsArray.get(i).setStyle("-fx-background-color: LIGHTBLUE");
            }
        }
    }

}
