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
    private final ArrayList<String> covidLockdownSeats = new ArrayList<>();
    private final ArrayList<String> fullLockdownSeats = new ArrayList<>();
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

    /*
        TODO: When adding a covid lockdown, must also remove any booking in a seat that is already occupied.
        TODO: Add ability for admin to select specific seats, instead of just the three options.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateControls();
        initiateOptionTwoMenu();
        covidLockdownSeats.addAll(Arrays.asList("seat1", "seat3", "seat5", "seat8", "seat9", "seat12", "seat13", "seat16", "seat18", "seat20"));
        fullLockdownSeats.addAll(Arrays.asList("seat1", "seat2", "seat3", "seat4", "seat5", "seat6", "seat7", "seat8", "seat9", "seat10", "seat11", "seat12", "seat13", "seat14", "seat15", "seat16", "seat17", "seat18", "seat19", "seat20"));
    }

    //Creates actions for each button/datePicker
    private void initiateControls() {
        datePicker.setOnAction((ActionEvent e) -> selectedDate(datePicker));
        btnOptionOne.setOnAction((ActionEvent e) -> setVisible(optionOne));
        btnOptionTwo.setOnAction((ActionEvent e) -> setVisible(optionTwo));
        btnConfirmTwo.setOnAction((ActionEvent e) -> lockdownOptionTwo());
        btnReturnHome.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
    }

    //Creates actions for the menu in option 2 on UI page.
    private void initiateOptionTwoMenu() {
        menuNoCond.setOnAction((ActionEvent e) -> setMenuOption("No Condition", menuNoCond));
        menuCovid.setOnAction((ActionEvent e) -> setMenuOption("Covid Condition", menuCovid));
        menuFullLock.setOnAction((ActionEvent e) -> setMenuOption("Full Lockdown", menuFullLock));
    }

    //Sets the selected menu item as a local variable and allows the user
    //To confirm their change
    private void setMenuOption(String lockdownType, MenuItem chosenItem) {
        selectedItem = chosenItem;
        menuButton.setText(lockdownType);
        btnConfirmTwo.setDisable(false);
    }

    //Indicates that the user has or hasn't selected an appropriate date.
    private void selectedDate(DatePicker picker) {
        if (picker.getValue().isBefore(LocalDate.now())) {
            displayWarning("-fx-text-fill: red","Please select a date in the future", true);
            btnOptionOne.setDisable(true);
            btnOptionTwo.setDisable(true);
        } else {
            warning.setVisible(false);
            btnOptionOne.setDisable(false);
            btnOptionTwo.setDisable(false);
            chosenDate = picker.getValue().toString();
        }
    }

    //When the user clicks confirm in option two, this checks if there are already bookings for that date
    //If there are bookings, it alerts the user. Otherwise, it updates the bookings table.
    private void lockdownOptionTwo() {

        if (selectedItem.getId().equals("menuNoCond") && hasBookings(chosenDate)) {
            clearCovidSeats();
        } else if (selectedItem.getId().equals("menuCovid") && !hasBookings(chosenDate)) {
            setCovidLockedSeats();
        } else if (selectedItem.getId().equals("menuFullLock") && !hasBookings(chosenDate)) {
            setFullLockdown();
        } else {
            displayWarning("-fx-text-fill: red", "Unable to apply lockdown", true);
        }

    }

    //Returns a boolean to check whether a chosen date has any bookings.
    private boolean hasBookings(String chosenDate) {
        AdminBookingModel adminBookingModel = new AdminBookingModel();
        boolean isBooking = false;

        try {
            isBooking = adminBookingModel.checkBooking(chosenDate);
            return isBooking;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isBooking;
    }

    //Clears any covid-related bookings from a selected date
    private void clearCovidSeats() {
        AdminBookingModel adminBookingModel1 = new AdminBookingModel();

        try {
            adminBookingModel1.setNoLockdown(chosenDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayWarning("-fx-text-fill: green", "Successfully Reset Lockdown Status", true);
    }

    //Sets covid locked seats by adding them into the database
    //This is for the one space between each person
    private void setCovidLockedSeats() {
        UserModel userModel = new UserModel();
        int employeeID = 0;
        try {
            employeeID = userModel.getEmployeeID(Main.getCurrentUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        enterSeats(employeeID, covidLockdownSeats);
        displayWarning("-fx-text-fill: green", "Successfully Covid Locked", true);
    }

    //Locks down every single seat for a specific date.
    private void setFullLockdown() {
        UserModel userModel = new UserModel();
        int employeeID = 0;

        try {
            employeeID = userModel.getEmployeeID(Main.getCurrentUser());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        enterSeats(employeeID, fullLockdownSeats);
        displayWarning("-fx-text-fill: green", "Successfully Set Full Lockdown", true);
    }

    //Puts either full lock down seat bookings, or 1 space seat bookings into the bookings table.
    //A new adminBookingModel must be created each time, otherwise the original connection is closed.
    private void enterSeats(int employeeID, ArrayList<String> desiredSeats) {
        for (String seat : desiredSeats) {
            AdminBookingModel adminBookingModel = new AdminBookingModel();
            try {
                adminBookingModel.addBooking(employeeID, seat, chosenDate, "covid");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //Sets group one or group two to visible depending on which option is clicked
    private void setVisible(Group option) {
        if (option.getId().equals("optionOne")) {
            option.setVisible(true);
            optionTwo.setVisible(false);
        } else if (option.getId().equals("optionTwo")) {
            option.setVisible(true);
            optionOne.setVisible(false);
        }
    }

    //Redirects the user to a location (dynamic)
    private void redirect(String redirectLocation) {
        try {
            SceneController.drawScene(redirectLocation);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void displayWarning(String textColour, String message, boolean visibility) {
        warning.setStyle(textColour);
        warning.setText(message);
        warning.setVisible(visibility);
    }
}
