package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;

    @FXML
    private Button[] seatArray;

    @FXML
    private Button btnNext2, btnCancel2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.seatArray = {seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14};

        System.out.println(seatArray[0].getId());

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

        System.out.println(actionEvent.getSource().toString());
        System.out.println(seat1.toString());



//        initializeArray();
//
////        for(int i = 0; i < 20; i++) {
//        String v1 = actionEvent.getSource().toString().trim();
//        String v2 = seatArray[0].toString().trim();
//
//            if(actionEvent.getSource().toString().equals(seatArray[0].toString())) {
//                System.out.println("Hello please work");
//            }
////        }
//
//        //Trim all unnecessary info from actionEvent source
//        //OR
//        //Compare actionEvent source with button object, and if its the same, perform actions on it.
//
//        //SeatID -> database
//        //SeatID -> database -> change seat colour
//

    }

    //Idea here was that there is no array initialized at the beginning.
    private void initializeArray() {

//        for(int i = 0; i < 20; i++) {
//            seatArray[i] = seat;
//        }

    }
}
