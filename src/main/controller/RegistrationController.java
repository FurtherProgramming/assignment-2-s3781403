package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.model.RegistrationModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    public RegistrationModel registrationModel = new RegistrationModel();
//    public Button registerButton;

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
    

    //Check if database is connected
        @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (registrationModel.isDbConnected()){
            dbStatus.setText("Connected");
        } else {
            dbStatus.setText("Not connected");
        }
    }

    //Where the logic goes to determine whether this is a successful registration or not
    public void Register(ActionEvent actionEvent) throws SQLException {

            if(checkPassword()) {
                try {
                    if(registrationModel.registration(1, txtFirstName.getText(), txtLastname.getText(), txtUsername.getText(), txtFirstPassword.getText(), staffRole.getText(), txtSecQuestion.getText(), txtSecAnswer.getText())) {
                        dbStatus.setText("New account created");
                } else {
                    dbStatus.setText("Account failed to create");
                }
            } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

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
}
