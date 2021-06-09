package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class BookingModel {

    Connection connection;
    UserModel userModel = new UserModel();

    private String insertBookingDate = "INSERT INTO bookings(employee_id, bookedDate) VALUES(?, ?);";
    private String insertBooking = "INSERT INTO bookings(bookedDate, seat, employee_id, status) VALUES(?, ?, ?, ?);";

    public BookingModel() {
        connection = SQLConnection.connect();

        if (connection == null) {
            System.exit(1);
        }
    }

    public void addBooking(String currentUser, String seatNum, String bookingDateString) {
        System.out.println(seatNum);
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(insertBooking);
            preparedStatement.setString(1, bookingDateString);
            preparedStatement.setString(2, seatNum);
            preparedStatement.setInt(3, getUserID(currentUser));
            preparedStatement.setString(4, "booked");

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private int getUserID(String currentUser) {

        int userID = 0; //TODO Should test for this before / after return somehow

        try {
            userID = userModel.getEmployeeID(currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;

    }

    public void setBookedDate(LocalDate tempBookingDate) {

        String bookingDate = String.valueOf(tempBookingDate);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertBookingDate);
            preparedStatement.setInt(1, userModel.getEmployeeID(Main.getCurrentUser()));
            preparedStatement.setString(2, bookingDate);

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }


}
