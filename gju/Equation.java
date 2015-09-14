package gju;

/**
 * Created by Xenocidist on 8/11/15.
 */
//import java.util.Scanner;

//This class is the Equation class, and objects of this class will hold equations
public class Equation{
    int variables; //the number of variables
    double[] values; //the values of the equation, first coeffients then constant

    Equation(){ //default constructor
        this(0);
    }
    Equation(int v){ //constructor
        this.variables = v;
        this.values = new double[variables + 1];
    }
    Equation(Equation another){
        this.variables = another.variables;
        this.values = another.values;
    }

  /* This code is now a relic. Was used when algorithm was used directly from the console.
  public Equation enterEquation(int vars){//creates an equation through user input
    Scanner in = new Scanner(System.in);
    int i = 0;
    //System.out.println("Enter number of variables of:");
    variables = vars;
    values = new double[variables + 1];
    //prompts user to enter a value for each variable
    for(; i < variables; i++){
      double temp;
      System.out.printf("Enter constant value for variable coefficient of X%d:\n", i + 1);
      temp = in.nextDouble();
      this.values[i] = temp;
    }
    //i++; Index is incremented after last loop, now effectively points to equation constant
    //prompts user to enter a value for the equation constant
    System.out.println("Enter value for equation constant.");
    this.values[i] = in.nextDouble();
    return this;
  }
  */

    public int process(){
    /* this method check if values are all zero if so,
     * it is known that the row was either canceled out,
     * or potentially given as all zeros - and in either case,
     * this reduces the rank.
     */
        double sum = 0; //Initialize the sum

        for(int i = 0; i < variables + 1; i++){
            sum = Math.abs(values[i]) + sum;
        }
        //Sums all the values in the equation.

        if (sum == 0) return 0; //If the sum is 0, return 0, indicating there row was all 0s

        return 1; //else return 1
    }

    public int checkEquation(){
	  /*This method checks if the equation had all zero variable coefficients
	   *and a non-zero constant. This means that the system has no solution.
	   */
        double sum = 0;

        for(int i = 0;i < variables; i++){
            sum = Math.abs(values[i]) + sum;
        }
        //Sums all the coefficient of the equation

        //Determines if the constant is not zero, and the sum is zero
        if ((sum == 0) && (values[variables] != 0)){
            return 1; //indicates no solution
        }
        return 0; //indicates that this equation does not have an issue
    }


    public void printEquation(){ //prints the system of equations
        int i = 0;
        for(; i < variables; i++){
            System.out.printf("%.2f  ",values[i]);
        }
        System.out.printf(" |  %.2f", values[i]);
    }
    public String equationToString(){
        String value = new String("");
        for (int j=0; j<variables; j++){
            value = value + this.values[j] + "   ";
        }
        value = value + "  |   " +  String.format("%.2f", this.values[variables]);
        return value;
    }


    public int pivot(){ //pivots the equation - making the first non-zero value  1

        //Goes through all the variables to find the first non-zero value
        for(int i = 0; i < variables; i++){
            if(values[i] != 0){
                double divisor = values[i]; //the divisor is the number
      /*  if (divisor == 1){
          i = variables;
          return 2; //indicated it was already pivoted about
        }
        */
                for(int j = 0; j < variables + 1; j++){
                    values[j] = values[j] / divisor;
                }
                return 1; //indicated pivoted
            }
        }
        return 0; //indicate empty - all zeroes
    }

    public int findPivot(){ //determines which variable was pivoted about
        int value = -1;

        for(int i = 0; i < variables; i++){
            double temp = values[i];
            if (temp == 1.0){
                return i; //return which variable was pivoted about
            }
        }
        return value; //indicates that row was not pivoted about
    }

    public int reduce(Equation e){
	  /* reduce the equation called upon by the equation that is the parameter.
	   * to_be_reduced.reduce(the_reducer)
	   */
        double divisor = 0; //declare divisor
        int index = -1; //declare variable which will be reduced by

        //this finds the index, and is based off the equation you are reducing by
        for(int i = 0; i < variables; i++){
            if (e.values[i] == 1){
                index = i;
                i = variables;
            }
        }

        if (index == -1) return 55; //something is wrong

        //the divisor is simply the number at the index in the equation you are calling it upon
        divisor = this.values[index];

        if (divisor == 0) return 1; //indicate failure

        for(int j = 0; j < variables + 1; j++){ //reduce each variable coefficient and equation constant
            this.values[j] = this.values[j] + (-1 * divisor * e.values[j]);
        }

        return 0;
    }
}
