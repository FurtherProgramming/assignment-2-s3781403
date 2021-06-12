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

    public BookingModel() {
        connection = SQLConnection.connect();

        if (connection == null) {
            System.exit(1);
        }
    }

    //Insert booking values into the database
    public void addBooking(String currentUser, String seatNum, String bookingDateString) throws SQLException {
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

    //Selects seats that are booked due to 'covid' reason
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
            //closing connection here causes select booked seats to close because of the nested loops in B2 calls
            //TODO solve this later. Not a now problem -> doesn't cause any issues (that I know of)
        }
        return lockedSeats;
    }

    //Selects any seats booked by a staff member from the bookings table
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

    //Checks if the user associated with the employeeID has a booking.
    public boolean checkUserBooking(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM bookings WHERE employee_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close();
            resultSet.close();
            preparedStatement.close();
        }
        return false;
    }

    public String getUserBookingDate(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT bookedDate FROM bookings WHERE employee_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return resultSet.getString("bookedDate");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        }
        return null;
    }

    public void updateBookingSeat(int employeeID, String seatNum) throws SQLException {
        PreparedStatement preparedStatement = null;
        String update = "UPDATE bookings SET seat = ? WHERE employee_id = ?";

        try {
            preparedStatement=connection.prepareStatement(update);
            preparedStatement.setString(1, seatNum);
            preparedStatement.setInt(2, employeeID);

            preparedStatement.executeUpdate();
            System.out.println("did it");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }
}
