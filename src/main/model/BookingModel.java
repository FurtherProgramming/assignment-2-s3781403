package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BookingModel {

    Connection connection;
    UserModel userModel = new UserModel();

    private String insertBookingDate = "INSERT INTO bookings(employee_id, bookedDate) VALUES(?, ?)";
//    private String insertBooking = "INSERT INTO bookings(bookedDate, seat, employee_id, status) VALUES(?, ?, ?, ?)";

    public BookingModel() {
        connection = SQLConnection.connect();

        if (connection == null) {
            System.exit(1);
        }
    }

    public void addBooking(String currentUser, String seatNum, String bookingDateString) throws SQLException {
        System.out.println(seatNum);
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO bookings(bookedDate, seat, employee_id, status) VALUES(?, ?, ?, ?)";

        int userID = getUserID(currentUser);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingDateString);
            preparedStatement.setString(2, seatNum);
            preparedStatement.setInt(3, userID);
            preparedStatement.setString(4, "booked");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            connection.close();
        }

    }

    private int getUserID(String currentUser) {

        int userID = 0; //TODO Should test for this before / after return somehow
        try {
            userID = userModel.getEmployeeID(currentUser);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return userID;

    }

}
