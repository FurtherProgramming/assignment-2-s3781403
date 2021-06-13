package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffManagementModel extends UserModel {

    public StaffManagementModel() {
        connection = SQLConnection.connect();

        if (connection == null) {
            System.exit(1);
        }
    }

    public String getEmployeeUsernameByID(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT username from Employees where id = ?";
        String username = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()) {
                return username="Has no username";
            } else if(resultSet.next()) {
                return username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        }

        return username;
    }

    public ObservableList<String> getAllEmployeeIDs() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT ALL id FROM Employees";
        ObservableList<String> employeeIDs = FXCollections.observableArrayList();

        try {
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    employeeIDs.add(String.valueOf(resultSet.getInt("id")));
                }
                return employeeIDs;
            }
            return employeeIDs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            resultSet.close();
            preparedStatement.close();
        }
        return employeeIDs;
    }

    public void deleteEmployee(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        String update = "DELETE FROM Employees WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }


    }


}
