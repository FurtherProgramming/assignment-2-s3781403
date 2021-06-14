package main.controller.staff;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/* TODO
       - Code this.
 */

public class ResetPasswordController implements Initializable {
    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnNextStep;

    @FXML
    private Group groupOneQuestion;

    @FXML
    private Group groupTwoNewPass;

    @FXML
    private TextField txtSecretQuestion;

    @FXML
    private Button btnResetPass;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblNewPass;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateControls();
        initiateGroups(false, false);
    }

    private void initiateGroups(boolean groupOne, boolean groupTwo) {
        groupOneQuestion.setVisible(groupOne);
        groupTwoNewPass.setVisible(groupTwo);
    }




}
