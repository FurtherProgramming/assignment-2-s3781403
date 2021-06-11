package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.model.RegistrationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    public RegistrationModel registrationModel = new RegistrationModel();


    //Creates variables from the fxml registration file
    @FXML
    private TextField txtEmployeeID, txtFirstName, txtLastname, txtUsername;
    @FXML
    private TextField txtSecQuestion, txtSecAnswer, txtFirstPassword, txtSecondPassword;
    @FXML
    private Label dbStatus;
    @FXML
    private Label passwordMatch;
    @FXML
    private RadioButton staffRole;
    @FXML
    private Label incorrectInputs;
    @FXML
    private Button btnGoLoginPage, btnCancel, registerButton;


    //Variables to be manipulated and then passed into SQL statements
    private int sqlEmployeeID, sqlPreviousSeat;
    private String sqlFirstName, sqlLastname, sqlUsername, sqlSecQuestion, sqlSecAnswer, sqlPassword, sqlSecondPass, sqlStaffRole;

    //Check if database is connected
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (registrationModel.isDbConnected()) {
            dbStatus.setText("DB Connected");
        } else {
            dbStatus.setText("Not connected");
        }
    }

    //Clicking button on registration page brings here, this is where the logic happens
    public void Register(ActionEvent actionEvent) throws SQLException {

        checkRegistrationInputs(); //TODO: Code this

        generateInputs();

        //Also want to add some additional checks here: password length, password strength, etc
        //Perhaps can use lambdas to do checks on multiple things at once
        if (checkPassword()) {
            try {
                if (registrationModel.registration(sqlEmployeeID, sqlFirstName, sqlLastname, sqlUsername, sqlPassword, sqlStaffRole, sqlSecQuestion, sqlSecAnswer)) {
                    dbStatus.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");  //These should be changed to be hidden labels in better positions
                    dbStatus.setText("New account created");
                    btnGoLoginPage.setVisible(true);
                    btnCancel.setDisable(true);
                    registerButton.setDisable(true);
                } else {
                    dbStatus.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
                    dbStatus.setText("Failed to create an account");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //Incomplete, just checking out what works.
    //Use lambdas calling multiple
    private boolean checkRegistrationInputs() {
        if (txtEmployeeID.getText().isEmpty()
                || txtFirstName.getText().isEmpty()
                || txtLastname.getText().isEmpty()
                || txtUsername.getText().isEmpty()
                || txtSecQuestion.getText().isEmpty()
                || txtSecAnswer.getText().isEmpty()
                || txtFirstPassword.getText().isEmpty()
                || txtSecondPassword.getText().isEmpty()
                || staffRole.getText().isEmpty()) {

            incorrectInputs.setVisible(true);
            return false;

        } else {
            return true;
        }
        //TODO Turn the above into separate functions that modify their respective boxes based on failed input.
        //TODO Could then all be used in a lambda function?

        //EmployeeID should be only numbers, password meeting a min requirement of 'x'. etc
        //        checkEmployeeID();
        //        checkFirstName();
        //        checkLastName();
        //        checkUsername();
    }



    //I believe this method is now redundant, sadly.
    private void generateInputs() {

        sqlEmployeeID = Integer.parseInt(txtEmployeeID.getText());
        sqlFirstName = txtFirstName.getText();          //Should create a full name variable?
        sqlLastname = txtLastname.getText();
        sqlSecQuestion = txtSecQuestion.getText();
        sqlSecAnswer = txtSecAnswer.getText();
        sqlPassword = txtFirstPassword.getText(); //Only need one password, they're both the same. Should encrypt them in some way.
        sqlStaffRole = staffRole.getText();
        sqlUsername = txtUsername.getText();
        sqlPreviousSeat = 10;

    }

    private boolean checkPassword() {
        if (txtFirstPassword.getText().equals(txtSecondPassword.getText())) {
            passwordMatch.setText("Passwords match");
            passwordMatch.setVisible(true);
            return true;
        } else {
            passwordMatch.setText("Passwords do not match");
            passwordMatch.setVisible(true);
            return false;
        }
    }

    //TODO - Later, once actual stuff completed, encrypt passwords in some format.
    private void infoToString() {
        System.out.println("EmployeeID: " + sqlEmployeeID + "\n First Name: " + sqlFirstName + "\n Last name: " + sqlLastname
                + "\n Security Question: " + sqlSecQuestion + "\n Security Q Answer: " + sqlSecAnswer
                + "\n Password:  " + sqlPassword + "\n Staff Role: " + sqlStaffRole
                + " \n Username: " + sqlUsername);
    }


    //Is it better to have try catch, or throws IOException?
    public void goLoginPage(ActionEvent actionEvent) {
        try {
            SceneController.drawScene("ui/login.fxml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
