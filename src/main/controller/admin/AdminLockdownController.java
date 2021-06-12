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
        TODO: Must also check for an existing covid booking on date before moving onto adding / removing bookings.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateControls();
        initiateOptionTwoMenu();
        covidLockdownSeats.addAll(Arrays.asList("seat1", "seat3", "seat5", "seat8", "seat9", "seat12", "seat13", "seat16", "seat18", "seat20"));
        fullLockdownSeats.addAll(Arrays.asList("seat1", "seat2", "seat3", "seat4", "seat5", "seat6", "seat7", "seat8", "seat9", "seat10", "seat11", "seat12", "seat13", "seat14", "seat15", "seat16", "seat17", "seat18", "seat19", "seat20"));
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
            warning.setVisible(false);
            btnOptionOne.setDisable(false);
            btnOptionTwo.setDisable(false);
            chosenDate = picker.getValue().toString();
        }
    }

    private void lockdownOptionTwo() {
        //IF BOOKING DATE DOES NOT ALREADY HAVE COVID BOOKINGS -
        if (selectedItem.getId().equals("menuNoCond") && hasBookings(chosenDate)) {
            clearCovidSeats();
        } else if (selectedItem.getId().equals("menuCovid") && !hasBookings(chosenDate)) {
            setCovidLockedSeats();
        } else if (selectedItem.getId().equals("menuFullLock") && !hasBookings(chosenDate)) {
            setFullLockdown();
        } else {
            warning.setText("Invalid Selection on Menu Item");
        }

    }

    //TODO coding this - post commit work
    private boolean hasBookings(String chosenDate) {
        return true;
    }

    //TODO: there needs to be a check here to see if there ARE actually any seats.
    //TODO: write skeleton code into actual ode
    private void clearCovidSeats() {
        AdminBookingModel adminBookingModel1 = new AdminBookingModel();
// if(adminbookingmodel2.checkIfCovidBookings(date) - boolean) {     -- do below try block
        try {
            adminBookingModel1.setNoLockdown(chosenDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        warning.setText("Successfully Reset Lockdown Status");
        warning.setStyle("-fx-text-fill: green");
        warning.setVisible(true);
        //else {
        //set warning label to be "there are no covid lockdown bookings for that date"
    }

    private void setCovidLockedSeats() {
        UserModel userModel = new UserModel();
        int employeeID = 0;
        try {
            employeeID = userModel.getEmployeeID(Main.getCurrentUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        enterSeats(employeeID, covidLockdownSeats);

        warning.setText("Successfully Covid Locked");
        warning.setStyle("-fx-text-fill: green");
        warning.setVisible(true);
    }

    //TODO make employeeID a class variable so that can be called upon w/out always creating new models.
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

        warning.setText("Successfully Set Full Lockdown");
        warning.setStyle("-fx-text-fill: green");
        warning.setVisible(true);

    }

    private void enterSeats(int employeeID, ArrayList<String> desiredSeats) {
        // TODO Add if check for already have a booking
        // - Like: if(dateAlreadyHasCovidBooking() -> setLabel to "already has covid bookings".
        //Loops through the pre-assigned covid spacing seats and adds them to a booking.
        //If don't create new bookingModel every time, the close() statements in the model cause errors
        //admBookModel.checkBooking(chosenDate);
        for (String seat : desiredSeats) {
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
