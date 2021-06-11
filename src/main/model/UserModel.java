package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    private static final String employeeIDByUser = "SELECT id FROM Employees WHERE username = ?";
    Connection connection; //Couldn't make this work without it being static
    private String role;


    public UserModel() {
        connection = SQLConnection.connect();

        if (connection == null) {
            System.exit(1);
        }
    }

    public int getEmployeeID(String currentUser) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int employeeID = 0;

        try {
            preparedStatement = connection.prepareStatement(employeeIDByUser);
            preparedStatement.setString(1, currentUser);


            resultSet = preparedStatement.executeQuery();
            employeeID = resultSet.getInt("id");
//            if(resultSet.next()) {
//
//            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return employeeID;


    }

    public String checkUserRole(String username) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employees WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                role = resultSet.getString("role");
                return role;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return role;
    }


    public String selectPreviousSeat() throws SQLException {
        String previousSeat = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT previousseat FROM Employees WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Main.getCurrentUser());

            resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                previousSeat = resultSet.getString("previousSeat");
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return previousSeat;
    }

    public void setPreviousSeat(String seatNum) throws SQLException {
        PreparedStatement preparedStatement = null;
        String update = "UPDATE Employees SET (previousseat) = ? WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, seatNum);
            preparedStatement.setString(2, Main.getCurrentUser());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }

    public String getEmployeeName(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT firstname FROM Employees WHERE username = ?";
        String firstName = null;


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            firstName = resultSet.getString("firstname");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close();
            resultSet.close();
            preparedStatement.close();
        }
        return firstName;
    }

}
