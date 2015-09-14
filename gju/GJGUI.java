package gju;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GJGUI extends Application {
   //This starts the program.
   @Override
   public void start(Stage primaryStage) {
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

