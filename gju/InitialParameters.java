package gju;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class InitialParameters {

    public Scene beginLayout(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("The Gauss Jordan Algorithm");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label equations = new Label("Number of Equations:");
        grid.add(equations, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label variables = new Label("Number of Variables:");
        grid.add(variables, 0, 2);

        TextField userTextFieldTwo = new TextField();
        grid.add(userTextFieldTwo, 1, 2);

        Button btn = new Button("Solve System");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //when button is pressed
        //userTextField

        userTextField.setOnAction(e -> {userTextFieldTwo.requestFocus(); });
        //if enter is pressed work as if
        userTextFieldTwo.setOnAction(e -> handleButtonPress(primaryStage, actiontarget, userTextField, userTextFieldTwo));
        btn.setOnAction((e) -> handleButtonPress(primaryStage, actiontarget, userTextField, userTextFieldTwo));


        Scene scene = new Scene(grid, 500, 500);

        return scene;
    }

    private void handleButtonPress(Stage primaryStage, Text actiontarget, TextField uTF1, TextField uTF2){
        try {
            int x = Integer.parseInt(uTF1.getText());
            int y = Integer.parseInt(uTF2.getText());

            actiontarget.setFill(Color.BLUE);
            actiontarget.setText("THE PROGRAM WILL EXECUTE.");

            PauseTransition pt = new PauseTransition(Duration.millis(5000));
            pt.play();


            Parameters p = new Parameters();
            p.beginSolve(primaryStage, x, y);

        } catch(NumberFormatException f){
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("ERROR. PLEASE ENTER INTEGERS.");
        }
    }
}
