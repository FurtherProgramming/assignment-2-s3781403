package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationModel {

    Connection connection;

    public RegistrationModel(){
        connection= SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }


    //Checks if connection to DB is closed, if not, returns true, else false
    public Boolean isDbConnected() {
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean registration(int employeeID, String firstName, String lastName, String username, String password, String role, String secQuestion, String secAnswer) throws SQLException {

//        SQLConnection connect = new SQLConnection();
//        Connection connection = connect.connect();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        System.out.println("got here");
        String query = "INSERT INTO Employees(id, firstname, surname, username, password, role, secretquestion, secretquestionanswer, previousseat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println("got here");
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, role);           //TODO change role to be dynamic
            preparedStatement.setString(7, secQuestion);
            preparedStatement.setString(8, secAnswer);
            preparedStatement.setInt(9, 0);
            System.out.println("now here");

            resultSet = preparedStatement.executeQuery();

            System.out.println("hello");

//            if (resultSet.next()) {
//                System.out.println("now here");
//                return true;
//            } else {
//                System.out.println("fa here");
//                return false;
//            }
        }
        catch (Exception e)
        {
            return false;
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }

        return true;
    }




}

