package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.RegistrationController;
import main.controller.SceneController;
import main.model.RegistrationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminRegisterController extends RegistrationController implements Initializable {

    @FXML
    private TextField txtFieldArubID, txtFieldUser, txtFieldFirstName, txtFieldLastName, txtFieldSecQuestion, txtFieldSecAnswer;

    @FXML
    private PasswordField passFieldPassOne, passFieldPassTwo;

    @FXML
    private MenuButton menuSelectRole;

    @FXML
    private MenuItem menuItemAdmin, menuItemStaff, selectedItem;

    @FXML
    private Button btnCancel, btnAddEmployee, btnReturnToMenu;

    @FXML
    private Label warning;

    private String selectedRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
        initializeMenu();

    }

    private void initializeButtons() {
        btnCancel.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
        btnReturnToMenu.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
        btnAddEmployee.setOnAction((ActionEvent e) -> beginRegistration());
    }

    private void initializeMenu() {
        menuItemAdmin.setOnAction((ActionEvent e) -> setMenuOption("admin", menuItemAdmin));
        menuItemStaff.setOnAction((ActionEvent e) -> setMenuOption("staff", menuItemStaff));
    }

    private void setMenuOption(String role, MenuItem menuItem) {
        selectedItem = menuItem;
        selectedRole = role;
        menuSelectRole.setText(menuItem.getText());
    }

    private void beginRegistration() {
        RegistrationModel registrationModel = new RegistrationModel();
        if(checkInputs()) {
            try {
                if(registrationModel.registration(Integer.parseInt(txtFieldArubID.getText()), txtFieldFirstName.getText(),txtFieldLastName.getText(), txtFieldUser.getText(), passFieldPassOne.getText(), selectedRole, txtFieldSecQuestion.getText(), txtFieldSecAnswer.getText())) {
                    System.out.println("it was supposed to work!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            warning.setText("Failed to create an account");
            warning.setStyle("-fx-text-fill:red");
            warning.setVisible(true);
        }
    }


    private boolean checkInputs() {
        if (checkAllFilled()) {
            if (checkPasswordsMatch()) {
                warning.setStyle("-fx-text-fill: green");
                warning.setText("Passwords match");
                return true;
            } else {
                warning.setStyle("-fx-text-fill: red");
                warning.setText("Passwords do not match");
                warning.setVisible(true);
                return false;
            }
        } else {
            warning.setText("Please fill out ALL values");
            warning.setVisible(true);
            return false;
        }
    }

    private boolean checkPasswordsMatch() {
        if (passFieldPassOne.toString().equals(passFieldPassTwo.toString())) {
            return true;
        } else {
            return false;
        }
    }

    private void redirect(String redirectLocation) {
        try {
            SceneController.drawScene(redirectLocation);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    //TODO better input checking (as well as using the superclass registers method instead and passing variables.
    private boolean checkAllFilled() {
        if (txtFieldArubID.getText().isEmpty()   //This needs to be changed to checking if its a number
                || txtFieldFirstName.getText().isEmpty()
                || txtFieldLastName.getText().isEmpty()
                || txtFieldSecAnswer.getText().isEmpty()
                || txtFieldSecQuestion.getText().isEmpty()
                || txtFieldUser.getText().isEmpty()
                || selectedRole.isEmpty()) {
            warning.setStyle("-fx-text-fill: red");
            warning.setText("Please fill ALL fields");
            return false;
        } else {
            return true;
        }

    }

//TODO implement later

//    private boolean checkIsNumber(TextField txtFieldArubID) {
//        txtFieldArubID.getText()
//    }
}


