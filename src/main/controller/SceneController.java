package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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

    public static class AlertBox {

        public static boolean answer;

        public static void draw(String title, String labelMsg) {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setMinWidth(250);
            Label label = new Label();
            label.setText(labelMsg);

            //Create buttons
            Button yesButton = new Button("Confirm");
            Button noButton = new Button("Cancel");

            yesButton.setOnAction((ActionEvent e) -> {
                answer = true;
                window.close();
            });
            noButton.setOnAction((ActionEvent e) -> {
                answer = false;
                window.close();
            });

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, yesButton, noButton);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }

    }
}
