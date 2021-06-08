package main.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class LandingController {

    public void redirectBookingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/booking_page_1.fxml");
    }
}
