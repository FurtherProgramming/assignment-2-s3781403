package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    Connection connection; //Couldn't make this work without it being static

    private static final String employeeIDByUser = "SELECT id FROM Employees WHERE username = ?";

    public UserModel() {
        connection = SQLConnection.connect();

        if(connection==null) {
            System.exit(1);
        }
    }

    public int getEmployeeID(String currentUser) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(employeeIDByUser);
            preparedStatement.setString(1,currentUser);

            resultSet = preparedStatement.executeQuery();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resultSet.getInt("id"));

        return resultSet.getInt("id");


    }

    public String checkUserRole(String username) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employees WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet.getString("role");
    }



}
