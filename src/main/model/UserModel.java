package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    Connection connection; //Couldn't make this work without it being static

    private static final String employeeIDByUser = "SELECT id FROM Employees WHERE username = ?";
    private String role;


    public UserModel() {
        connection = SQLConnection.connect();

        if(connection==null) {
            System.exit(1);
        }
    }

    public int getEmployeeID(String currentUser) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int employeeID = 0;

        try {
            preparedStatement = connection.prepareStatement(employeeIDByUser);
            preparedStatement.setString(1,currentUser);


            resultSet = preparedStatement.executeQuery();
            employeeID = resultSet.getInt("id");
//            if(resultSet.next()) {
//
//            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        System.out.println("User ID:" + employeeID);

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
            role = resultSet.getString("role");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return role;

    }


    public String selectPreviousSeat() {
        String previousSeat = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT previousseat FROM Employees WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Main.getCurrentUser());

            resultSet = preparedStatement.executeQuery();

            if(resultSet != null) {
                previousSeat = resultSet.getString("previousSeat");
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return previousSeat;
    }
}
