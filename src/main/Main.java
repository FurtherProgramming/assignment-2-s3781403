package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //Declare variables so controller can change scenes
    private static Parent root;
    private static Stage primaryStage; //Better variable name
    private static String title; //TODO Use this later ?


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Main.primaryStage = primaryStage;
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Arub Seat Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    //Getters/Setters for scene drawing
    public static Parent getRoot() {
        return root;
    }

    public static void setRoot(Parent root) {
        Main.root = root;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void setStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Main.title = title;
    }


}