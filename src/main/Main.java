package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //Declare variables so controller can change scenes
    private static Parent root;
    private static Stage primaryStage;
    private static String title; //TODO decide if this is necessary

    private static String currentUser; ///This is the username of the employee who is logged in.

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

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Main.currentUser = currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Main.primaryStage = primaryStage;
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Arub Seat Management");
        primaryStage.show();
    }


}