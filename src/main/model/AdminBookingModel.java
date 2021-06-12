package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminBookingModel extends BookingModel {
    Connection connection;
    UserModel userModel = new UserModel();

    public AdminBookingModel() {
        connection = SQLConnection.connect();
        if (connection == null) {
            System.exit(1);
        }
    }


    //Sets / clears any COVID related bookings from a specific date
    public void setNoLockdown(String chosenDate) throws SQLException {
        String update = "DELETE FROM bookings WHERE status= 'covid' AND bookedDate = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, chosenDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }

    //Checks if there is a booking on a specific date
    public void checkBooking(String chosenDate) {


    }
}
