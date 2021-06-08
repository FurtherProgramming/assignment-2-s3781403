package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }

    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event) {

        try {
            if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText())) {

                isConnected.setText("Logged in successfully");

                redirectUserType(txtUsername.getText());

            } else {

                isConnected.setText("username and password is incorrect");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //Redirects the user
    private void redirectUserType(String username) throws IOException, SQLException {

        String staffType = loginModel.checkUserRole(username);

        if (staffType.equals("admin")) {

            SceneController.drawScene("ui/admin/admin_landingpage.fxml");

        } else if (staffType.equals("staff")) {

            SceneController.drawScene("ui/staff/landingpage.fxml");

        } else {
            isConnected.setText("User has no role");
        }
    }

    public void createAccRedirect(ActionEvent actionEvent) throws IOException {
        SceneController.drawScene("ui/register.fxml");
        //This gets rid of login window and draws the registration window
    }


    //11.2.3 big sur


}
