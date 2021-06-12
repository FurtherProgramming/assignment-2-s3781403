package main.controller.staff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import main.controller.SceneController;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LandingController implements Initializable {

    public UserModel userModel = new UserModel();

    @FXML
    private Label labelEmplName;
    @FXML
    private Button manageBooking;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayEmployeeName();

        manageBooking.setOnAction((ActionEvent e) -> {
            try {
                SceneController.drawScene("ui/staff/edit_booking.fxml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

    }

    private void displayEmployeeName() {
        try {
            labelEmplName.setText("Welcome to Arub Hotdesking, " + userModel.getEmployeeName(Main.getCurrentUser()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redirectBookingPage(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/staff/booking_page_1.fxml");
    }


}
