package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AdminBookingModel extends BookingModel {
    Connection connection;
    UserModel userModel = new UserModel();

    public AdminBookingModel() {
        connection = SQLConnection.connect();
        if (connection == null) {
            System.exit(1);
        }
    }

    //Displays uncomfirmed booking information for a date - usually tomorrow.
    public HashMap<String, Object> getBookingInformation(String dateToCheck) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HashMap<String, Object> bookingInformation = new HashMap<>();
        String query = "SELECT * FROM bookings WHERE bookedDate = ? AND status = 'booked'";


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dateToCheck);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                bookingInformation.clear();
                bookingInformation.put("empty", "no dates");
            } else {
                bookingInformation.clear();
                bookingInformation.put("seat", resultSet.getString("seat"));
                bookingInformation.put("employeeID", resultSet.getInt("employee_id"));
                bookingInformation.put("bookingDate", resultSet.getString("bookedDate"));
            }
            return bookingInformation;

        } catch (SQLException e) {
            System.out.println("getBookingInformation() in AdminBookingModel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        return null; // Could return bookingInformation instead
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

    //Checks if there are bookings on a specific date, returns true if so
    public boolean checkBooking(String chosenDate) throws SQLException {
        String query = "SELECT * FROM bookings WHERE status = 'covid' AND bookedDate = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, chosenDate);

            resultSet = preparedStatement.executeQuery();

            //Checks whether result set is empty
            if (!resultSet.isBeforeFirst()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        }
        return false;
    }

    //Updates a specific employees booking status on a certain date
    public void updateBookingStatus(String status, int employeeID, String bookingDate) throws SQLException {
        String update = "UPDATE bookings SET status = ? WHERE employee_id = ? AND bookedDate = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, employeeID);
            preparedStatement.setString(3, bookingDate);

            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            System.out.println("updateBookingStatus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            connection.close();
        }

    }
}
