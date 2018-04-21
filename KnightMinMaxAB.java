/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckersAI;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Zachary
 */
public class KnightMinMaxAB {

    static int totalBoards = 0;
    static int totalPrune = 0;
    static final int MAX_VALUE = 7000;
    static final int MIN_VALUE = -8000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        while (true) {
            int action = mainMenu();
            switch (action) {
                case 1:
                    playerVsComputer();
                    break;
                case 2:
                    playerVsComputer2();
                    break;
                case 3:
                    computerVsComputer();
                    break;
                case 4:
                    computerVsComputer2();
                    break;
                case 5:
                    computerVsComputer3();
                    break;
                case 6:
                    System.out.println("Thank you for playing!");
                    System.exit(0);
            }
        }

    }

    private static int mainMenu() {
        Scanner s = new Scanner(System.in);
        int option = 0;
        System.out.println("Let's Play Checkers!\n\n");
        do {
            System.out.println("\t\t  Main Menu");
            System.out.println("\t\t--------------");
            System.out.println("\t1.Play against the computer!(minMaxAB)");
            System.out.println("\t2.Play against the computer!(ABSearch)");
            System.out.println("\t3.Watch the computers play! (minMaxAB vs minMaxAB)");
            System.out.println("\t4.Watch the computers play! (ABSearch vs ABSearch)");
            System.out.println("\t5.Watch the computers play! (minMaxAB vs ABSearch)");
            System.out.println("\t6.Exit");
            System.out.print("Option: ");
            option = s.nextInt();
        } while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6);
        return option;
    }

    private static String[] getUserMove() {
        Scanner s = new Scanner(System.in);
        //Pattern letterNumber = Pattern.compile("[A-Z]\\d");
        String piece = null;
        String newLoc = null;
        while (piece == null) {
            System.out.print("Which piece would you like to move?: ");
            piece = s.next();
            if (Pattern.matches("[A-Z]\\d", piece)) {
                break;
            } else {
                System.out.println("Bad format. (Ex. G3, F8, A1, ...)");
                piece = null;
            }
        }
        System.out.println();
        while (newLoc == null) {
            System.out.print("Where would you like to move it?: ");
            newLoc = s.next();
            if (Pattern.matches("[A-Z]\\d", newLoc)) {
                break;
            } else {
                System.out.println("Bad format. (Ex. G3, F8, A1, ...)");
                newLoc = null;
            }
        }
        System.out.println();
        return new String[]{piece, newLoc};
    }

    private static int getConfirm() {
        Scanner s = new Scanner(System.in);
        int option = 0;
        do {
            System.out.print("\nPress 1 to continue watching \n Or press 2 to exit: ");
            option = s.nextInt();
        } while (option != 1 && option != 2);

        return option;
    }

    //USER PLAY AGAINST MIN-MAX-AB
    // Utilizes primary evaluation function
    private static void playerVsComputer() {
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
//        ValueStructure turn_result = mmABGame.start(Board.getStartBoard(), 0, Board.Player.black,
//                Integer.MAX_VALUE, Integer.MIN_VALUE);
//        Board current_board = turn_result.getPath().get(0).cloneBoard();
        Board current_board = Board.getStartBoard();
        current_board.printBoard();
        String[] userMove;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            do {
                userMove = getUserMove();
            } while (!current_board.isValidMove(userMove[0], userMove[1]));
//          current_board.moveWhite(current_board.mapValue(userMove[0]), current_board.mapValue(userMove[1]));
            current_board.printBoard();
            turn_result = mmABGame.start(current_board, 0, Board.Player.black, MAX_VALUE, MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
        }
        if (current_board.getTerminal()) {
            System.out.println(current_board.getWinner());
            System.out.println("Total Boards Evaluated: " + totalBoards);
            System.out.println("Total Prunes: " + totalPrune);
        }
        //*****************End Main Game Loop*******************//
    }

    //USER PLAY AGAINST ALPHA-BETA-SEARCH
    // ABS uses primary evaluation function
    /**
     *
     */
    public static void playerVsComputer2() {
        AlphaBetaSearch mmABGame = new AlphaBetaSearch();
        ValueStructure turn_result = mmABGame.start(Board.getStartBoard(), Board.Player.black);
        Board current_board = turn_result.getPath().get(0).cloneBoard();
        current_board.printBoard();
        String[] userMove;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            do {
                userMove = getUserMove();
            } while (!current_board.isValidMove(userMove[0], userMove[1]));
//          current_board.moveWhite(current_board.mapValue(userMove[0]), current_board.mapValue(userMove[1]));
            current_board.printBoard();
            turn_result = mmABGame.start(current_board, Board.Player.black);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
        }
        if (current_board.getTerminal()) {
            System.out.println(current_board.getWinner());
            System.out.println("Total Boards Evaluated: " + totalBoards);
            System.out.println("Total Prunes: " + totalPrune);
        }
        //*****************End Main Game Loop*******************//
    }

    //THIS FUNCTION PLAYS 2 MIN-MAX-AB ALGORITHMS AGAINST EACH OTHER
    // Both utilize the primary evaluation function
    /**
     *
     */
    public static void computerVsComputer() {
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            turn_result = mmABGame.start(current_board, 0, Board.Player.black, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            turn_result = mmABGame.start(current_board, 0, Board.Player.white, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

    //THIS FUNCTION PLAYS TWO ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
    //  Each uses a different evaluation function, black with primary

    /**
     *
     */
    public static void computerVsComputer2() {
        AlphaBetaSearch mmABGame = new AlphaBetaSearch();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            turn_result = mmABGame.start(current_board, Board.Player.black);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal()) {
                turn_result = mmABGame.start(current_board, Board.Player.white, 2);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalPrune += turn_result.pruneCount;
                totalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

    //THIS FUNCTION PLAYS MIN-MAX AB VS ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
    // Both use the primary evaluation function
    /**
     *
     */
    public static void computerVsComputer3() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            turn_result = mmAB.start(current_board, 0, Board.Player.black, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal()) {
                turn_result = ABS.start(current_board, Board.Player.white);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalPrune += turn_result.pruneCount;
                totalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

}
