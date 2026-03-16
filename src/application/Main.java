package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginPage;    // ← THIS IMPORT IS MISSING

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new LoginPage(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}