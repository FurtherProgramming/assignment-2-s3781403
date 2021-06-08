package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationModel {

    Connection connection;

    public RegistrationModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }


    //Checks if connection to DB is closed, if not, returns true, else false
    public Boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean registration(int employeeID, String firstName, String lastName, String username, String password, String role, String secQuestion, String secAnswer) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO Employees(id, firstname, surname, username, password, role, secretquestion, secretquestionanswer) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, role);
            preparedStatement.setString(7, secQuestion);
            preparedStatement.setString(8, secAnswer);
            //Change previous seat sat to be dynamic
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {

            //TODO This stuff should only happen if you correctly put things in the inputs.
//            preparedStatement.close();    // This could be uncommented once the rego page redirects to login
//            connection.close();
        }
        return true;
    }


}

