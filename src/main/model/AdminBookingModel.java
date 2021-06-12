package main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminBookingModel extends BookingModel {
    Connection connection;
    UserModel userModel = new UserModel();

    //Sets / clears any COVID related bookings from a specific date
    public void setNoLockdown(String chosenDate) {
        PreparedStatement preparedStatement = null;
        String update = "DELETE FROM bookings WHERE status='covid' AND bookedDate = ?";

    }

    //Locks down all seats for the chosen date
    public void setFullLockdown(String chosenDate) {
    }

    //Checks if there is a booking on a specific date
    public void checkBooking(String chosenDate) {


    }
}
