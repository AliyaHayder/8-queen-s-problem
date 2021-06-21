


import java.util.ArrayList;


public class QueenProject {

    public static void main(String[] args) {

        Fitness solution = new Fitness(); //will be used in while loop
        state example = new state();
        example.printBoard();
        state child = new state();
        ArrayList<population> generations = new ArrayList<population>(10);

        int count = 0;

        while (solution.getTotalFitness(solution.population.one) < 28) //condition for ideal state
        {
            //step 1 : create a population
            population p = new population();
            generations.add(p);
            population genTemp = generations.set(count, p);
            //apply fitness on states of that population
            Fitness genFitness = new Fitness();

            genFitness.population = genTemp; //equating populations from fitnes class and population class
            //after generating population , check fitness of all it's states one by one 

            state[] temp1 = {genFitness.population.one = new state(),
                genFitness.population.two = new state(),
                genFitness.population.three = new state(),
                genFitness.population.four = new state()};
            int[] fitnessTemp = new int[4]; //array to store fitness values in a population 
            for (int i = 0; i < 4; i++) {
                genFitness.applyFitness(temp1[i]);
                genFitness.getTotalFitness(temp1[i]);
                fitnessTemp[i] = genFitness.getTotalFitness(temp1[i]);
            }
            //reproduction , apply select parent

            ArrayList<Reproduction> crossTemp = new ArrayList<Reproduction>();
            Reproduction R = new Reproduction();
            crossTemp.add(R);
            //passing fitness of all states to select top 2 parents for mating
            crossTemp.get(count).comparison(fitnessTemp);
            crossTemp.get(count).selectParent(genFitness, temp1);
            System.out.println("After cross-over Child is: ");
            child = crossTemp.get(count).Crossover();
            child.printBoard();
            System.out.println("\n");
            crossTemp.get(count).Mutation(child);
            child.printBoard();
            solution.population.child = child;

            count++;
        }

        System.out.println("fitness of child is: " + solution.getTotalFitness(child));

    }
}

class state {
    //a class to set up a single chessboard

    int[] positions;
    String[][] board = new String[8][8];

    public state() {
        //plottting board

        //first take a single array for positioning of queens
        //initialising queens positions 
        positions = new int[8];

        int max = 7;
        int min = 0;
        for (int i = 0; i < 8; i++) {
            int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
            positions[i] = rand;
        }

        //plotting on a 8 by 8 board
        //creating an 8 by 8 array
        for (int i = 0; i < 8; i++) {
//iterating upto the umber of columns i.e 8
            board[i][positions[i]] = "Q";
        }
//         for (int i = 0; i < 8; i++) {
////iterating upto the umber of columns i.e 8
//int a = positions[i];
//            board[i][a] = "Q";
//        }

    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {

            System.out.print("" + positions[i]);
        }

//        for (int i = 0; i < 8; i++) {
//            System.out.println("\n" );
//            for (int j = 0; j < 8; j++) {
//               
//                    System.out.print(board[i][j] + "");
//
//            }
//        }
//        
        System.out.println("\nPlotting the Queens on Board:\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == "Q") {
                    System.out.print(board[i][j]);

                } else {

                    System.out.print("*");

                }
            }
            System.out.println();
        }
    }
}

class population {

    //a class to generate a popuplation of multiple chess boards
    //declaring variable to access these chessboards in main
    state one;
    state two;
    state three;
    state four;
    state child;

    //a non parameter constructor so that when population object is made,  4 chessboards are made too
    public population() {
        state ChessBoard1 = new state();
        state ChessBoard2 = new state();
        state ChessBoard3 = new state();
        state ChessBoard4 = new state();
        this.one = ChessBoard1;
        this.two = ChessBoard2;
        this.three = ChessBoard3;
        this.four = ChessBoard4;

    }

}

class Fitness {

    population population = new population();
    int nonAttackingPairs = 28;
    int conflicts = 0;

    public int applyFitness(state state) {
        //checking diagonal
        for (int i = state.board.length - 1; i > 0; i--) {
            for (int j = 0, x = i; x <= state.board.length - 1; j++, x++) {
                conflicts = 0;
                //to traverse uptil the queen on chessoard
                if (state.board[i][j] != null) {
                    //two inner loops staring from this point

                    for (int k = i; k < state.board.length; k++) { // upto the row where queen is
                        for (int l = j; l < state.board.length; l++) //upto the col queen is 
                        {
                            //right up 
                            if (state.board[i][j] != null) {
                                conflicts++;

                            }
                        }
                        //right down
                        if (state.board[i][j] != null) {
                            conflicts++;
                        }

                    }
                }
            }
        }
        //##### checking left right top bottom
        //how to start from where the queen is placed
        for (int i = 0; i < state.positions.length; i++) {
            for (int j = 0; j < state.positions.length; j++) {
                // Traverse to the right

                if (state.board[i][j] != null) {

                }
            }
            conflicts++;
        }
        for (int i = 0; i < state.positions.length; i++) {
            for (int j = 0; j < state.positions.length; j++) {
                // Traverse downwards
                if (state.board[i][j] != null) {

                }
            }
            conflicts++;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 7; j >= 0; j--) {
                // Traverse upwards
                if (state.board[i][j] != null) {

                }
            }
            conflicts++;
        }

        return conflicts;
    }

    public int getTotalFitness(state board) {
        //per state
        int totalFitness = 0;
        conflicts = applyFitness(board);
        totalFitness = nonAttackingPairs - conflicts;

        return totalFitness;
    }

}

class Reproduction {

    //this class will handle selection, crossover and mutation
    state parentOne = new state();
    state parentTwo = new state();
    state child = new state();
    Fitness gen;
    int maxOne = 0;
    int maxTwo = 0;
//alternate
//    public state[] selection(state p1, state p2) {
//
//        if (parent1Fitness.getTotalFitness(p1) > parent2Fitness.getTotalFitness(p2)) {
//            parentOne = p1;
//            parentTwo = p2;
//            parents[1] = parentOne;
//            parents[2] = parentTwo;
//        } else {
//            parentOne = p2;
//            parentTwo = p1;
//            parents[1] = parentOne;
//            parents[2] = parentTwo;
//        }
//        return parents;
//    }

    public void comparison(int[] parentsFitness) {

        for (int n : parentsFitness) {
            if (maxOne < n) {
                maxTwo = maxOne;
                maxOne = n;
            } else if (maxTwo < n) {
                maxTwo = n;
            }
        }

    }

    public void selectParent(Fitness gen, state[] temp) {
        //selecting parents for mating based on top 2 fitness 
        for (int i = 0; i < 4; i++) {
            if (gen.getTotalFitness(temp[i]) == maxOne) {
                parentOne = temp[i];
            }
            if (gen.getTotalFitness(temp[i]) == maxTwo) {
                parentTwo = temp[i];
            }

        }

    }

//method to mate
    public state Crossover() {

        //for storing array contents for swapping
        //crossover point is selected as 4
        for (int i = 4; i < 8; i++) {
            parentOne.positions[i] = parentTwo.positions[i];
        }
        child.positions = parentOne.positions;
        for (int i = 0; i < 8; i++) {
            System.out.print(child.positions[i]);
        }
        System.out.println("\n");
        return child;
    }
//method to mutate

    public state Mutation(state child) {

        //generating a random index for mutation
        int max = 7;
        int min = 0;
        int rd = (int) Math.floor(Math.random() * (max - min + 1) + min);
        child.positions[rd] = (int) Math.floor(Math.random() * (max - min + 1) + min);
        System.out.println("After mutation the child is");
        for (int i = 0; i < 8; i++) {
            System.out.print(child.positions[i]);
        }
        System.out.println("\n");
        return child;
    }
}
