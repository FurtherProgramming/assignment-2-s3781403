package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import main.Main;
import main.controller.SceneController;
import main.model.AdminBookingModel;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AdminLockdownController implements Initializable {
    @FXML
    private Group optionOne, optionTwo;
    @FXML
    private Button btnOptionOne, btnOptionTwo, btnReturnHome, btnConfirmTwo, btnConfirmOne;
    @FXML
    private MenuItem menuNoCond, menuFullLock, menuCovid;
    @FXML
    private MenuButton menuButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label warning;
    private MenuItem selectedItem;
    private String chosenDate;
    private ArrayList<String> covidLockdownSeats = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateControls();
        initiateOptionTwoMenu();
        covidLockdownSeats.addAll(Arrays.asList("seat1", "seat3", "seat5", "seat8", "seat9", "seat12", "seat13", "seat16", "seat18", "seat20"));
    }

    private void initiateControls() {
        datePicker.setOnAction((ActionEvent e) -> selectedDate(datePicker));
        btnOptionOne.setOnAction((ActionEvent e) -> setVisible(optionOne));
        btnOptionTwo.setOnAction((ActionEvent e) -> setVisible(optionTwo));
        btnConfirmTwo.setOnAction((ActionEvent e) -> lockdownOptionTwo());
        btnReturnHome.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
    }

    private void initiateOptionTwoMenu() {
        menuNoCond.setOnAction((ActionEvent e) -> setMenuOption("No Condition", menuNoCond));
        menuCovid.setOnAction((ActionEvent e) -> setMenuOption("Covid Condition", menuCovid));
        menuFullLock.setOnAction((ActionEvent e) -> setMenuOption("Full Lockdown", menuFullLock));
    }

    private void setMenuOption(String lockdownType, MenuItem chosenItem) {
        selectedItem = chosenItem;
        menuButton.setText(lockdownType);
        btnConfirmTwo.setDisable(false);
    }

    private void selectedDate(DatePicker picker) {
        if (picker.getValue().isBefore(LocalDate.now())) {
            warning.setText("Please select a date in the future");
            warning.setVisible(true);
            btnOptionOne.setDisable(true);
            btnOptionTwo.setDisable(true);
        } else {
            btnOptionOne.setDisable(false);
            btnOptionTwo.setDisable(false);
            chosenDate = picker.getValue().toString();
        }
    }

    private void lockdownOptionTwo() {
        AdminBookingModel adminBookingModel = new AdminBookingModel();
        UserModel userModel = new UserModel();

        if (selectedItem.getId().equals("menuNoCond")) {
            adminBookingModel.setNoLockdown(chosenDate);
        } else if (selectedItem.getId().equals("menuCovid")) {
            setCovidLockedSeats();
        } else if (selectedItem.getId().equals("menuFullLock")) {
            adminBookingModel.setFullLockdown(chosenDate);
        } else {
            warning.setText("Invalid Selection on Menu Item");
        }

        // TODO - Code this after adding datepicker
    }

    private void setCovidLockedSeats() {
        UserModel userModel = new UserModel();
        int employeeID = 0;

        try {
            employeeID = userModel.getEmployeeID(Main.getCurrentUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Loops through the pre-assigned covid spacing seats and adds them to a booking.
        //If don't create new bookingModel every time, the close() statements in the model cause errors
        for (String seat : covidLockdownSeats) {
            AdminBookingModel adminBookingModel = new AdminBookingModel();
            try {
                adminBookingModel.addBooking(employeeID, seat, chosenDate, "covid");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void setVisible(Group option) {
        if (option.getId().equals("optionOne")) {
            option.setVisible(true);
            optionTwo.setVisible(false);
        } else if (option.getId().equals("optionTwo")) {
            option.setVisible(true);
            optionOne.setVisible(false);
        }
    }

    private void redirect(String redirectLocation) {
        try {
            SceneController.drawScene(redirectLocation);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}