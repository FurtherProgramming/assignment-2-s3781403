package main.controller.admin;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import main.controller.SceneController;
import main.model.StaffManagementModel;
import main.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AdminDeactivateController implements Initializable {

    @FXML
    private Group groupEmployeeDetails;

    @FXML
    private Label lblFullName, lblRole, lblUsername, warning;

    @FXML
    private Button btnDeactivate, btnCancel, btnReturnHome;

    @FXML
    private ComboBox<String> comboBoxID;

    private ObservableList<String> comboOptions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateComboBoxOptions();
        initiateActions();
        groupEmployeeDetails.setVisible(false);
    }

    //Creates combo box options based on a list taken from the model (employeeID)
    private void initiateComboBoxOptions() {
        StaffManagementModel staffManagementModel1 = new StaffManagementModel();
        comboBoxID.setVisibleRowCount(6);
        try {
            comboOptions = staffManagementModel1.getAllEmployeeIDs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboBoxID.getItems().addAll(comboOptions);
    }

    //Displays all relevant user info
    private void displayUserInformation() {
        groupEmployeeDetails.setVisible(true);
        setNameLabel();
        setRoleLabel();
        setUsernameLabel();
        warning.setVisible(false);
    }

    private void setUsernameLabel() {
        StaffManagementModel staffManagementModel = new StaffManagementModel();
        try {
            lblUsername.setText("Username: " +
                    staffManagementModel.getEmployeeUsernameByID(Integer.parseInt(comboBoxID.getValue())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRoleLabel() {
        StaffManagementModel staffManagementModel = new StaffManagementModel();
        UserModel userModel = new UserModel();

        try {
            //Nested statements meant that there was no new model being created -> hence no more connection opening
            lblRole.setText("Role: " +
                    userModel.checkUserRole(staffManagementModel.getEmployeeUsernameByID(Integer.parseInt(comboBoxID.getValue()))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setNameLabel() {
        StaffManagementModel staffManagementModel = new StaffManagementModel();
        try {
            lblFullName.setText("Name: " +
                    staffManagementModel.getFullNameByID(Integer.parseInt(comboBoxID.getValue())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initiateActions() {
        btnReturnHome.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
        comboBoxID.setOnAction((ActionEvent e) -> displayUserInformation());
        btnDeactivate.setOnAction((ActionEvent e) -> deactivateEmployee());
        btnCancel.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_landingpage.fxml"));
    }

    //Method to delete an employee and update labels/buttons on the page
    private void deactivateEmployee() {
        StaffManagementModel staffManagementModel = new StaffManagementModel();
        SceneController.AlertBox.draw("Delete Employee", "This action cannot be undone");

        if (SceneController.AlertBox.answer) {
            try {
                staffManagementModel.deleteEmployee(Integer.parseInt(comboBoxID.getValue()));
                warning.setText("Employee Deleted");
                warning.setVisible(true);
                clearLabels();
                initiateComboBoxOptions();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void clearLabels() {
        lblUsername.setText("");
        lblRole.setText("");
        lblFullName.setText("");
    }

    //TODO Shared across multiple classes - Should be implemented elsewhere and then called in these,
    // possibly via extending a superclass that has them in there
    private void redirect(String location) {
        try {
            SceneController.drawScene(location);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
