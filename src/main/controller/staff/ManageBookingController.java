package main.controller.staff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import main.controller.SceneController;
import main.model.BookingModel;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/*
    TODO Extend a superclass that handles button creation and drawing.
     - Add Check that it isn't 48 hours before
     - REALLY REMEMBER TO FIX THE OO -- IT'S BAD AT THE MOMENT!
     - CLEAN UP BOOKING CLASSES!
 */

public class ManageBookingController implements Initializable {
    public UserModel userModel = new UserModel();
    public BookingModel bookingModel = new BookingModel();
    @FXML
    private Button seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14;
    @FXML
    private List<Button> seatsArray = new ArrayList<>();
    @FXML
    private Button confirmChange, deleteBooking, returnHome, chosenBtn;
    @FXML
    private Label noBookingsLabel, labelBookingDate;
    private int employeeID;
    private String seatNum, bookingDate;
    private boolean hasBooking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEmployeeID();

        try {
            userHasBooking();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeButtons();
        seatsArray.addAll(Arrays.asList(seat1, seat2, seat4, seat5, seat7, seat9, seat11, seat15, seat19, seat18, seat3, seat16, seat17, seat13, seat6, seat8, seat10, seat12, seat20, seat14));

        if (hasBooking) {
            drawBookingDate();
            initializeUnavailableSeats();
        } else {
            disableTable();
        }

    }

    //Disables ability to use table.
    private void disableTable() {
        for (Button button : seatsArray) {
            button.setDisable(true);
        }
        confirmChange.setDisable(true);
        deleteBooking.setDisable(true);
        noBookingsLabel.setText("You have no bookings");
        noBookingsLabel.setVisible(true);
    }
    //Initialize unavailable seats and colourbookingtable should be called from a superclass instead
    //tells the page which seats aren't available by pulling from database
    private void initializeUnavailableSeats() {
        BookingModel bookingModel3 = new BookingModel();
        ArrayList<String> covidLockedSeats = new ArrayList<>();
        ArrayList<String> bookedSeats = new ArrayList<>();
        try {
            covidLockedSeats = bookingModel3.selectCovidSeats(bookingDate);
            bookedSeats = bookingModel3.selectBookedSeats(bookingDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colourBookingTable(covidLockedSeats, bookedSeats);
    }

    //Sets the color of certain seats (covid or booked)
    private void colourBookingTable(ArrayList<String> covidLockedSeats, ArrayList<String> bookedSeats) {
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

    //Sets the employeeID in the class (should be elsewhere (model))
    private void setEmployeeID() {
        try {
            employeeID = userModel.getEmployeeID(Main.getCurrentUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Updates the label with the booking date
    private void drawBookingDate() {
        try {
            bookingDate = bookingModel.getUserBookingDate(employeeID);
            labelBookingDate.setText("Booking Date: " + bookingDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Try catch causes problems
    private void userHasBooking() throws SQLException {
        BookingModel bookingModel2 = new BookingModel();
        if (bookingModel2.checkUserBooking(employeeID)) {
            hasBooking = true;
        } else {
            hasBooking = false;
        }

    }

    //Creates actions for the buttons on the page
    private void initializeButtons() {
        confirmChange.setOnAction((ActionEvent e) -> {
            if (seatNum != null) {
                //If alert box returns true: update the database with changes
                SceneController.AlertBox.draw("Confirm Change", "Accept booking changes?");
                if (SceneController.AlertBox.answer) {
                    updateEmployeeSeating();
                    goLandingPage();
                }
            } else {
                noBookingsLabel.setText("You need to select a seat first.");
                noBookingsLabel.setVisible(true);
            }
        });

        deleteBooking.setOnAction((ActionEvent e) -> {
            //If alert box returns true: delete the entry from the database
            SceneController.AlertBox.draw("Delete Booking", "Really delete booking?");
            if (SceneController.AlertBox.answer) {
                deleteEmployeeBooking();
                goLandingPage();
            }
        });

        returnHome.setOnAction((ActionEvent e) -> goLandingPage());
    }

    //Deletes an employee booking
    private void deleteEmployeeBooking() {
        BookingModel bookingModel1 = new BookingModel();
        try {
            bookingModel1.deleteBooking(employeeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Updates an employees booked seat
    private void updateEmployeeSeating() {
        BookingModel bookingModel1 = new BookingModel();
        UserModel userModel1 = new UserModel();
        try {
            bookingModel1.updateBookingSeat(employeeID, seatNum);
            userModel1.setPreviousSeat(seatNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void goLandingPage() {
        try {
            SceneController.drawScene("ui/staff/landingpage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
