package gju;

public class SystemOE{
    int equations; //number of equations in system to begin with
    int variables; //number of variables in system
    int rank;      //number of equations in final form
    Equation[] matrix; /*holds the actual equations in an array,
                      *each being another Equation object
                      */

    String answer; //used for checking if system is inconsistent



    public SystemOE(){ //default constructor
        this(0,0);
    }
    public SystemOE(int e, int v){ //system constructor
        this.equations = e;
        this.variables = v;
        this.rank =  e; //rank begins as the total number of equations
        this.matrix = new Equation[equations];
    }

    public SystemOE copy(){
        SystemOE copy = new SystemOE();
        copy.equations = this.equations;
        copy.variables = this.variables;
        copy.rank = this.rank;
        Equation[] newMatrix = new Equation[equations];

        for(int i = 0; i < equations; i++){
            Equation nextEquation = new Equation(this.variables);
            nextEquation.variables = this.variables;
            for (int j = 0; j < variables + 1; j++){
                nextEquation.values[j] = this.matrix[i].values[j];
            }
            newMatrix[i] = nextEquation;
        }

        copy.matrix = newMatrix;
        return copy;
    }

    public void roundNice(){ //fixes the equations to look nice.
        for (int e = 0; e < equations; e++){
            for(int d = 0; d < variables + 1; d++){
                //since floats can have -0 & +0, we are going to change the -0s to +0s to look better
                if (this.matrix[e].values[d] == -0.00){
                    this.matrix[e].values[d] = 0.00;
                }

            }
        }
    }

    public int systemAnalysis(){
        this.setRank(); //determines and sets the rank of the equation

        if(variables > rank){ //if the number of variables is greater than the
            this.answer = "inconsistent"; //rank there is no unique solution.
        }

        int r = this.noSolution(); //checks if system of equations has no solution
        if (r == 1){
            this.answer = "No";
            System.out.println("This system of equations does not have a solution.");
            return -1; //indicate no solution
        }


        //return infinite solution
        if(answer == "inconsistent"){
            System.out.println("This equation does not have a unique solution.");
            System.out.printf("\nThe answer in vector form:\n");

            //find which columns have been pivoted about
            int[] cpivots = findPivots();
            //return x(v+1)(- v.coeff)
            for(int z = 0; z  < equations; z++){ //go through each equation and print it
                //prints out particular solution
                //but we need to check which variables to print out.

                System.out.printf("{X%d}  =  %.2f", z + 1, (this.matrix[z]).values[variables]);

                //print out info - for each variable that is not pivoted about
                for(int k = 0; k < variables; k++){
                    //check if this variable was pivoetd about
                    int flip = -1; //if remains -1, was not pivoted about
                    for(int y = 0; y < cpivots.length; y++){
                        if (k == cpivots[y]){
                            flip = 1;
                            y = cpivots.length; //end this loop going to next equation
                        }
                    }

                    if (flip == -1){ //k is the now the value of the variable we want to look at
                        System.out.printf("  +  X%d[%.2f]  ", k + 1, -(matrix[z].values[k]));
                    }
                }
                System.out.printf("\n");
            }

            //prints out which variables are free, or arbitrary.
            System.out.printf("The free variable(s): ");
            int flip2 = 1;
            for( int p = 0; p < cpivots.length; p++){
                if(cpivots[p] != p){
                    if(flip2 != 1){
                        System.out.printf(", ");
                    }
                    System.out.printf("X%d", p + 1);
                    flip2 = -1;
                }
            }
            System.out.printf(".\n");

            return 2;
        }

        //return unique solution
        int j = 1;
        for( int i = 0; i < rank; i++){
            System.out.printf("{X%d}  =  %.2f\n", j, (this.matrix[i]).values[variables]);
            j++;
        }
        return 0;
    }


    public void setRank(){ //determines the rank of the system
        rank = equations; //begins with number of equations
        for(int i = 0; i < equations; i++){ //is reduced if an equation is all zeroes
            if(matrix[i].process() == 0){
                rank--;
            }
        }
    }

    public int noSolution(){ //checks if the system does not have a solution
        int sum = 0;
        for(int i = 0; i < equations; i++){
            sum = matrix[i].checkEquation() + sum;
        }
        if(sum != 0){ //the sum will be greater than 0 if any one of the equations result
            //would result in that.
            return 1; //indicate that there is not a solution
        }
        return 0; //indicate there is a solution
    }

    public int[] findPivots(){ //determine where the pivots are in the system[around what variables?]
        int[] cpivots = new int[variables];
        int index = 0;
        for(int i = 0; i < equations; i++){
            int temp = matrix[i].findPivot();
            if (temp != -1){
                cpivots[index] = temp;
                index++;
            }
        }
        return cpivots;
    }

  /* Again - a relic of an old user interface
  public void enterSystem(){ //prompts user to enter information
    matrix = new Equation[equations];
    for(int i = 0; i < equations; i++){
      Equation temp = new Equation();
      matrix[i] = temp.enterEquation(variables);
    }
  }
  */

    public void printSystem(){ //prints the system, one equation at a time
        for(int i = 0;i < equations; i++){
            matrix[i].printEquation();
            System.out.printf("\n");
        }

    }
    public void runAlgorithim(){
        //pivots each row
        for(int i = 0; i < this.equations; i++){
            matrix[i].pivot();

            //then reduces all rows above and below it, then repeats for each row.
            for(int j = i + 1; j < this.equations; j++){ //reducing down
                matrix[j].reduce(matrix[i]);
            }
            for(int z = i - 1; z > -1; z--){ //reducing up
                matrix[z].reduce(matrix[i]);
            }

        }
    }
}



