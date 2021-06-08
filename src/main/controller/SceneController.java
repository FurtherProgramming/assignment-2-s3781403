package main.controller;

import javafx.fxml.FXMLLoader;
import main.Main;
import javafx.scene.Scene;

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

}
