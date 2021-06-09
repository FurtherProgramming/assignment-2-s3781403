package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class BookingModel {

    Connection connection;

    private String insertBookingDate = "INSERT INTO bookings(employee_id, bookedDate) VALUES(?, ?);";

    public BookingModel() {
        connection = SQLConnection.connect();

        if (connection==null) {
            System.exit(1);
        }
    }

    public void setBookedDate(LocalDate tempBookingDate) {

        String bookingDate = String.valueOf(tempBookingDate);

        try {
            UserModel userModel = new UserModel();
            PreparedStatement preparedStatement = connection.prepareStatement(insertBookingDate);
            preparedStatement.setInt(1, userModel.getEmployeeID(Main.getCurrentUser()));
            preparedStatement.setString(2, bookingDate);

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
