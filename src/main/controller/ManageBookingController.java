package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {

    @FXML
    private Button confirmChange, deleteBooking, cancel, returnHome;

    @FXML
    private Label noBookingsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
    }

    private void initializeButtons() {
        confirmChange.setOnAction((ActionEvent e) ->
                SceneController.alertBox("Confirm Changes?", "Update your bookings information?")
        );

        deleteBooking.setOnAction((ActionEvent e) ->
                SceneController.alertBox("Delete Booking?", "This action cannot be undone")
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


}
