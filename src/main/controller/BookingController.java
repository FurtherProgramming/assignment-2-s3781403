package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BookingController implements Initializable {

    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;

    @FXML
    private List<Button> seatsArray = new ArrayList<Button>();

    @FXML
    private Button btnNext2, btnCancel2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seatsArray.addAll(Arrays.asList(seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14));
        //DB connection in here too
    }

    public void checkBookingDate(ActionEvent actionEvent) {

    }

    //TODO There's a better way to goBookingPage
    //Each button sends variable of 1/2/3
    //Leads to one method goBooking
    //drawsScene(bookpage + variable)
    public void editBookDate(ActionEvent actionEvent) {
    }

    public void createBooking(ActionEvent actionEvent) {
    }


    public void editSeatNum(ActionEvent actionEvent) {
    }


    public void goLandingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/landingpage.fxml");
    }

    public void goBookingTwo(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/booking_page_2.fxml");
    }

    public void goBookingThree(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/booking_page_3.fxml");
    }


    public void chooseSeat(ActionEvent actionEvent) {

        //Compare actionEvent source with button object, and if its the same, perform actions on it.
        for (int i = 0; i < 20; i++) {
            if (actionEvent.getSource().toString().trim().equals(seatsArray.get(i).toString().trim())) {
                seatsArray.get(i).setStyle("-fx-background-color: blue");
            }
        }

//        //SeatID -> database
//        //SeatID -> database -> change seat colour


    }
}
