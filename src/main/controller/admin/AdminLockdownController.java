package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import main.controller.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLockdownController implements Initializable {
    @FXML
    private Group optionOne, optionTwo;
    @FXML
    private Button btnOptionOne, btnOptionTwo, btnReturnHome, btnConfirmTwo, btnConfirmOne;
    @FXML
    private MenuItem menuNoCond, menuFullLock, menuCovid;
    @FXML
    private MenuButton menuButton;

    private MenuItem selectedItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateControlButtons();
        initiateOptionTwoMenu();
    }

    private void initiateOptionTwoMenu() {
        menuNoCond.setOnAction((ActionEvent e) -> setMenuOption("No Condition", menuNoCond));
        menuCovid.setOnAction((ActionEvent e) -> setMenuOption("Covid Condition", menuCovid));
        menuFullLock.setOnAction((ActionEvent e) -> setMenuOption("Full Lockdown", menuFullLock));
    }

    private void setMenuOption(String lockdownType, MenuItem chosenItem) {
        selectedItem = chosenItem;
        menuButton.setText(lockdownType);
        btnConfirmTwo.setDisable(false);
    }

    private void initiateControlButtons() {
        btnOptionOne.setOnAction((ActionEvent e) -> setVisible(optionOne));
        btnOptionTwo.setOnAction((ActionEvent e) -> setVisible(optionTwo));
        btnConfirmTwo.setOnAction((ActionEvent e) -> lockdownOptionTwo());
        btnReturnHome.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
    }

    private void lockdownOptionTwo() {

    }
    private void setVisible(Group option) {
        if (option.getId().equals("optionOne")) {
            option.setVisible(true);
            optionTwo.setVisible(false);
        } else if (option.getId().equals("optionTwo")) {
            option.setVisible(true);
            optionOne.setVisible(false);
        }
    }

    private void redirect(String redirectLocation) {
        try {
            SceneController.drawScene(redirectLocation);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}
