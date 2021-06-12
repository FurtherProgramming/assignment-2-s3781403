package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.controller.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLandingController implements Initializable {

    @FXML
    private Button btnGenerateReport, btnManageBookings, btnCreateAccounts, btnDeleteAccounts, btnEditAccounts, btnLockdownSeats;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeRedirects();
    }

    private void initializeRedirects() {
        btnGenerateReport.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_generate-report.fxml"));
        btnManageBookings.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_manage-employee-bookings.fxml"));
        btnCreateAccounts.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_create-employee-accounts.fxml"));
        btnDeleteAccounts.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_deactivate-employee-accounts.fxml"));
        btnEditAccounts.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_change-employee-accounts.fxml"));
        btnLockdownSeats.setOnAction((ActionEvent e) -> redirect("ui/admin/admin_seat-lockdown.fxml"));
    }

    public void redirect(String redirectLocation) {
        try {
            SceneController.drawScene(redirectLocation);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
