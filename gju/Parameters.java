package gju;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Parameters {

    private int equationIndex = 0;
    private int variableIndex = 0;
    private BorderPane myRoot;
    private SystemOE system;
    private int variables;
    private int equations;
    private Stage primaryStage;

    public void beginSolve(Stage pS, int equationsI, int variablesI){
        variables = variablesI;
        equations = equationsI;
        primaryStage = pS;
        myRoot = new BorderPane();

        system = new SystemOE(equations, variables);//Create the system of the equation

        Equation eqtn = new Equation(variables);
        system.matrix[equationIndex] = eqtn;

        HBox hbox = addHBox();
        myRoot.setTop(hbox);
        myRoot.setCenter(addEquationPromptGrid(eqtn));


        Scene scene = new Scene(myRoot, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void solve(){
        system.roundNice(); //Rounds numbers. Fixes -0 to 0.
        SystemOE originalSystem = system.copy(); //saves a copy of the original numbers entered
        system.runAlgorithim(); //Solves the system.
        SystemOE solvedSystem = system.copy(); //for clarity, this is a copy of the solved system
        solvedSystem.systemAnalysis(); //Determines if system has unique, no, or infinite solution.
        addHBoxButtons(originalSystem, solvedSystem );//sets up the final scene
    }

    public HBox addHBoxButtons(SystemOE original, SystemOE solved) {
        //Sets up the top panel
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #337799;");

        //Sets up the "Original" button
        Button btn = new Button("Original");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        hbox.getChildren().addAll(hbBtn);
        btn.setOnAction((e) -> handleButtonPressOriginal(original));

        //Sets up the "Final Form" button
        Button btn2 = new Button("Final Form");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.getChildren().add(btn2);
        hbox.getChildren().addAll(hbBtn2);
        btn2.setOnAction((e) -> handleButtonPressSolved(solved));

        //Sets up the "Solution" button
        Button btn3 = new Button("Solution");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.getChildren().add(btn3);
        hbox.getChildren().addAll(hbBtn3);
        btn3.setOnAction((e) -> handleButtonPressSolution(solved));

        //Sets up the "Restart" button
        Button btn4 = new Button("Restart");
        HBox hbBtn4 = new HBox(10);
        hbBtn4.getChildren().add(btn4);
        hbox.getChildren().addAll(hbBtn4);
        btn4.setOnAction((e) -> handleButtonPressRestart());

        //Sets up the "Terminate" button
        Button btn5 = new Button("Terminate");
        HBox hbBtn5 = new HBox(10);
        hbBtn5.getChildren().add(btn5);
        hbox.getChildren().addAll(hbBtn5);
        btn5.setOnAction((e) -> handleButtonPressTerminate());


        myRoot.setTop(hbox);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //starting display
        Text scenetitle = new Text("The algorithm has been executed.\n Switch views with above panel.");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(scenetitle, 0, 0, 2, 1);

        myRoot.setCenter(grid);
        return hbox;
    }

    public void handleButtonPressTerminate(){
        Platform.exit();
    }

    public void handleButtonPressRestart(){
        //implement a pop question, are you sure you want to?
        GJGUI restart = new GJGUI();
        restart.start(primaryStage);
    }

    public void handleButtonPressOriginal(SystemOE original){
        //Put up the original SOE -


        //trying vbox
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("The Entered System of Equations:");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        for (int i=0; i<equations; i++) {
            String nextEquationText = new String("");
            for (int j=0; j<variables; j++){
                nextEquationText = nextEquationText + original.matrix[i].values[j] + "   ";
            }
            nextEquationText = nextEquationText + "  |   " + original.matrix[i].values[variables];

            Text nextEquation = new Text(nextEquationText);
            vbox.getChildren().add(nextEquation);
        }

        //the grid title etc:

        myRoot.setCenter(vbox);
    }

    public void handleButtonPressSolved(SystemOE solved){
        //Put up the original SOE -


        //trying vbox
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("The Solved System of Equations:");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        for (int i=0; i<equations; i++) {
            String nextEquationText = new String("");
            for (int j=0; j<variables; j++){
                nextEquationText = nextEquationText + solved.matrix[i].values[j] + "   ";
            }
            nextEquationText = nextEquationText + "  |   " +  String.format("%.2f", solved.matrix[i].values[variables]);

            Text nextEquation = new Text(nextEquationText);
            vbox.getChildren().add(nextEquation);
        }

        //the grid title etc:

        myRoot.setCenter(vbox);

    }

    public void handleButtonPressSolution(SystemOE solved){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("The Solution:");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        //Put up the original SOE -
        solved.systemAnalysis();

        String answerString = new String("");
        //set appropriate answer:
        if (solved.answer == "No"){
            answerString = "This system of equations does not have a solution.";
        } else if(solved.answer == "inconsistent"){
            answerString = "This system of equations does not have a unique solution.";
        } else { //unique solution
            answerString = "This system of equations has a unique solution";
        }

        Text answer = new Text(answerString);
        vbox.getChildren().add(answer);

        if (solved.answer == "No"){
            myRoot.setCenter(vbox);
            return; //We are finished
        }

        if (solved.answer == "inconsistent"){ //infinite answer
            //find which columns have been pivoted about
            int[] cpivots = solved.findPivots();

            System.out.printf("This is the cpivots:");
            for(int u = 0; u < cpivots.length; u++){
                System.out.printf("  %d  ", cpivots[u]);
            }

            //return x(v+1)(- v.coeff)
            for(int z = 0; z  < equations; z++){ //go through each equation and print it

                int test = -99;
                int v = 0;
                //find what variable to print
                for(; v < variables; v++){
                    if(solved.matrix[z].values[v] == 1){
                        test = v; //this is equal to which variable is pivoted about
                        v = variables; //breaks loop
                    }
                }
                if ((test == -99)){
                    continue;
                }

                String vectorAnswerString = String.format("{X%d}  =  %.2f", test + 1, (solved.matrix[z]).values[variables]);


                //print out info - for each variable that is not pivoted about
                for(int k = 0; k < variables; k++){
                    //check if this variable was pivoted about
                    int flip = -1; //if remains -1, was not pivoted about
                    for(int y = 0; y < cpivots.length; y++){
                        if (k == cpivots[y]){
                            flip = 1;
                            y = cpivots.length; //end this loop going to next equation
                        }
                    }

                    if (flip == -1){ //k is the now the value of the variable we want to look at
                        vectorAnswerString = vectorAnswerString + String.format("  +  X%d[%.2f]  ", k + 1, -(solved.matrix[z].values[k]));
                    }

                }

                Text vectorAnswerText = new Text(vectorAnswerString);
                vbox.getChildren().addAll(vectorAnswerText);
            }

            //prints out which variables are free, or arbitrary.

            Text notification = new Text("The free variable(s): ");
            vbox.getChildren().addAll(notification);

            String freeVariables = new String("");

            int flip2 = 1;

            for( int p = 0; p < cpivots.length; p++){
                int goAhead = 0; //Using this as a switch. If this variable is not pivoted about, this loop will break.
                for(int i = 0; i < variables; i++){
                    if (p == cpivots[i]){
                        goAhead = 1;
                    }
                }
                if(goAhead == 0){
                    ///if(cpivots[p] != p){ //this may be too specific
                    if(flip2 != 1){
                        freeVariables = freeVariables + ", ";
                    }
                    freeVariables = freeVariables + String.format("X%d", p + 1);
                    flip2 = -1; //so string is properly formatted after first entry
                }
            }
            Text freeVariablesText = new Text(freeVariables);
            vbox.getChildren().addAll(freeVariablesText);

        } else { //unique answer
            String vectorAnswerString;

            for( int i = 0; i < solved.rank; i++){
                vectorAnswerString = String.format("{X%d}  =  %.2f\n", i + 1, (solved.matrix[i]).values[variables]);
                Text vectorAnswerText = new Text(vectorAnswerString);
                vbox.getChildren().addAll(vectorAnswerText);
            }

        }

        //set the display
        myRoot.setCenter(vbox);
    }



    public GridPane addEquationPromptGrid(Equation eqtn){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Enter the values one constant at a time");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label eqtns = new Label("Field:");
        grid.add(eqtns, 0, 1);

        Label vrbls = new Label("Value:");
        grid.add(vrbls, 1, 1);

        //Want this to indicate what equation and what constant we are
        //entering
        if (variableIndex == variables){
            Label prompt = new Label("Constant value for equation " + (equationIndex + 1) + ":" );
            grid.add(prompt, 0, 2);
        } else {
            Label prompt = new Label("Coefficient for x" + (variableIndex + 1) + " in equation " + (equationIndex + 1) + ":" );
            grid.add(prompt, 0, 2);
        }

        //field to add the relevant
        TextField constant = new TextField();
        grid.add(constant, 1, 2);

        Button btn = new Button("Enter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        Text equationSoFar = new Text(eqtn.equationToString());
        grid.add(equationSoFar, 1, 4);

        //when button is pressed - save the value
        //check if done, and go to next scene
        //whether that be to enter next value or answer page
        constant.setOnAction(e -> handleButtonPress(actiontarget, constant, eqtn));
        btn.setOnAction((e) -> handleButtonPress(actiontarget, constant, eqtn));

        return grid;
    }

    private void handleButtonPress(Text actiontarget, TextField constant, Equation eqtn){
        try {
            double x = Double.parseDouble(constant.getText());
            System.out.println(x);
            System.out.println("");

            eqtn.values[variableIndex] = x;

            eqtn.printEquation();
            System.out.println("");

            actiontarget.setFill(Color.BLUE);
            actiontarget.setText("The value has been added to the equation.");

            PauseTransition pt = new PauseTransition(Duration.millis(5000));
            pt.play();

            variableIndex++; //move to next variable

            if (variableIndex == (variables + 1)){ //0 index, so variables, then constant
                System.out.printf("First check reached.\n");
                Equation eqtnU = new Equation(variables); //create next equation
                eqtnU.printEquation();

                variableIndex = 0; //reset counter
                equationIndex++; //move to next equation

                if(equationIndex == equations){
                    System.out.println("TIME TO SOLVE IT!!!");
                    system.printSystem();
                    this.solve();

                }else {

                    system.matrix[equationIndex] = eqtnU;
                    updatePrompt(eqtnU);
                }
            } else {
                updatePrompt(eqtn);
            }
        }
        catch(NumberFormatException f){
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("ERROR. PLEASE ENTER DOUBLES.");
        }
    }

    public void updatePrompt(Equation eqtn){
        HBox hbox = addHBox();
        myRoot.setTop(hbox);
        myRoot.setCenter(addEquationPromptGrid(eqtn));
    }

    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #337799;");

        Text scenetitle = new Text("Enter Equations for the System of Equations:");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        hbox.getChildren().addAll(scenetitle);

        return hbox;
    }

}
