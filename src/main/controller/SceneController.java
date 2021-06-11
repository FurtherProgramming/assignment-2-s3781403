package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class SceneController {

    //Draws a new scene by setting/getting appropriate variables in main
    public static void drawScene(String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(sceneName));
        Main.setRoot(loader.load());
        Scene scene = new Scene(Main.getRoot());
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }

    public static void alertBox(String title, String firstMessage) {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ui/staff/alert_box.fxml"));

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);


        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setScene(scene);
        window.showAndWait();
    }






}
