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
        if (checkInputs()) {
            System.out.println("received inputs");
            try {
                if (registrationModel.registration(Integer.parseInt(txtFieldArubID.getText()), txtFieldFirstName.getText(), txtFieldLastName.getText(), txtFieldUser.getText(), passFieldPassOne.getText(), selectedRole, txtFieldSecQuestion.getText(), txtFieldSecAnswer.getText())) {
                    generateWarning("New " + selectedRole + " created", "-fx-text-fill: green");
                } else {
                    generateWarning("Failed to create an account", "-fx-text-fill:red");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            generateWarning("Fill out all inputs", "-fx-text-fill:red");
        }
    }

    private void generateWarning(String text, String colour) {
        warning.setText(text);
        warning.setStyle(colour);
        warning.setVisible(true);
    }


    private boolean checkInputs() {
        if (checkAllFilled()) {
            if (checkPasswordsMatch()) {
                generateWarning("Passwords match", "-fx-text-fill: green");
                return true;
            } else {
                generateWarning("Passwords do not match", "-fx-text-fill: red");
                return false;
            }
        } else {
            generateWarning("Please fill out ALL values", "-fx-text-fill: red");
            return false;
        }
    }

    private boolean checkPasswordsMatch() {
        if (passFieldPassOne.getText().equals(passFieldPassTwo.getText())) {
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
                || selectedRole.isEmpty() || selectedRole == null) {
            generateWarning("Please fill out ALL values", "-fx-text-fill: red");
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


