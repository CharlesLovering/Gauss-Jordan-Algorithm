package gju;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Xenocidist on 8/11/15.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("The Gauss Jordan Algorithm");
        InitialParameters ip = new InitialParameters();
        Scene scene = ip.beginLayout(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
