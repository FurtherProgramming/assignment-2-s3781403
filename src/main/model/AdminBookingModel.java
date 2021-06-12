package main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminBookingModel extends BookingModel {
    Connection connection;
    UserModel userModel = new UserModel();

    public void setNoLockdown(String chosenDate) {
        PreparedStatement preparedStatement = null;
        String update = "DELETE FROM bookings WHERE status='covid' AND bookedDate = ?";



    }


    public void setBookedSeatsCovid(String chosenDate) {
        PreparedStatement preparedStatement = null;
        String update = "UPDATE bookings SET status = 'covid' WHERE bookedDate = ? AND seat = ('seat1', 'seat3', 'seat5', 'seat8', 'seat9', 'seat12', 'seat13', 'seat16', 'seat18', 'seat20')";
    }


    public void setFullLockdown(String chosenDate) {
    }
}
