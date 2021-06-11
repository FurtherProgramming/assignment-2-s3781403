package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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
        String update = "INSERT INTO bookings(bookedDate, seat, employee_id, status) VALUES(?, ?, ?, ?)";

        int userID = getUserID(currentUser);

        try {
            preparedStatement = connection.prepareStatement(update);
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

    //TODO Move this to user model
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

    public ArrayList<String> selectCovidSeats(String bookedDate) throws SQLException {
        ArrayList<String> lockedSeats = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT seat FROM bookings WHERE bookedDate = ? AND status = 'covid'";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookedDate);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lockedSeats.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //closing connection here -> fails selectBooked ..
        }
        return lockedSeats;
    }

    public ArrayList<String> selectBookedSeats(String bookedDate) throws SQLException {
        ArrayList<String> bookedSeats = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT seat FROM bookings WHERE bookedDate = ? AND status = 'booked'";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookedDate);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bookedSeats.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        return bookedSeats;
    }

}
