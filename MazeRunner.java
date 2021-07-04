package com.ishakssite;
import java.util.Scanner;

public class MazeRunner {
    private static Scanner scanner = new Scanner(System.in);
    private static Maze myMap = new Maze();
    private static int moves = 0;

    public static void main(String[] args) {
        intro();
        userMove();
    }

    private static void intro() {
        System.out.println("Welcome to Maze Runner!");
        System.out.println("Here is your current position: ");
        myMap.printMap();
    }

    private static void userMove() {
        while (!myMap.didIWin()) { // while didIWin() is false.
            System.out.println("Where would you like to move? (R, L, U, D)");
            String userDirection = scanner.next();

            if (!(userDirection.equals("R") || userDirection.equals("L") || userDirection.equals("U") || userDirection.equals("D"))) // input validation. if input isn't R/L/U/D, control is moved to top and codes below in this while loop aren't executed.
                continue;

            // note for below if statement: if user wants to move in a certain direction, but cannot do so, and there's no pit ahead in that direction as well, then there is a wall ahead in that direction. if this statement gets executed, user is notified that there's a wall ahead and again, control is moved to top in this while loop immediately.
            if ((userDirection.equals("R") && !myMap.canIMoveRight() && !myMap.isThereAPit("R")) || (userDirection.equals("L") && !myMap.canIMoveLeft() && !myMap.isThereAPit("L")) || (userDirection.equals("U") && !myMap.canIMoveUp() && !myMap.isThereAPit("U")) || (userDirection.equals("D") && !myMap.canIMoveDown() && !myMap.isThereAPit("D"))) {
                System.out.println("Sorry, you've hit a wall. Pick a new direction.");
                continue;
            }

            if (myMap.isThereAPit(userDirection)) { // if there's a pit ahead in user's direction, only navigatePit(userDirection) gets executed, and control is moved to top.
                navigatePit(userDirection);
                continue;
            }
                // < if this line is reached in while loop (3 "continue" keywords above in this while loop doesn't get executed), that means user can move in preferred direction. no wall/pit ahead. >
            if (userDirection.equals("R")) myMap.moveRight();
            if (userDirection.equals("L")) myMap.moveLeft();
            if (userDirection.equals("U")) myMap.moveUp();
            if (userDirection.equals("D")) myMap.moveDown();

            moves++; // after every successful normal move, number of moves is incremented by 1 and is implemented in this line. after every successful jump over pit, number of moves is incremented by 2, but is implemented in method named navigatePit.
            myMap.printMap();
            movesMessage(moves);
        } // this line marks the end of while loop.

        System.out.println("Congratulations, you made it out alive!"); // once while loop stops executing (didIWin() is true), user is congratulated.
        System.out.println("and you did it in " + moves + " moves.");
    }

    private static void navigatePit(String userDirection) { // once pit is detected ahead in user's direction, this method gets called and user has option to jump/not jump over the pit.
        System.out.println("Watch out! There's a pit ahead, jump it? If yes, enter a string that starts with \"y\". \"y\" or \"yes\" is preferred.");
        String userJumpChoice = scanner.next();

        if (userJumpChoice.startsWith("y")) {
            myMap.jumpOverPit(userDirection);
            moves += 2; // for every jump over pit, moves is incremented by 2, unlike normal move where moves is incremented by 1.
            myMap.printMap();
            movesMessage(moves);
        }
    }

    private static void movesMessage(int moves) { // after every successful move/jump, number of moves are incremented accordingly and this method gets called to limit (and warn) the user to 100 moves or lesser to complete the maze.
        switch (moves) {
            case 50: System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes"); break;
            case 75: System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape."); break;
            case 90: System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!"); break;
            case 100: System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:["); break;
            case 101: System.out.println("Sorry, but you didn't escape in time- you lose!");
            System.exit(0);
        }
    }
}