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
 * 
 */
public class checkersAI {

    static int maxTotalBoards = 0;
    static int maxTotalPrune = 0;
    static int minTotalPrune = 0;
    static int minTotalBoards = 0;
    static int totalGamePath = 0;
    static final int MAX_VALUE = 10000;
    static final int MIN_VALUE = -8000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        while (true) {
            maxTotalBoards = 0;
            maxTotalPrune = 0;
            minTotalPrune = 0;
            minTotalBoards = 0;
            totalGamePath = 0;
            int action = mainMenu();
            switch (action) {
                case 1:
                    option1();
                    break;
                case 2:
                    option2();
                    break;
                case 3:
                    option3();
                    break;
                case 4:
                    option4();
                    break;
                case 5:
                    option5();
                    break;
                case 6:
                    option6();
                    break;
                case 7:
                    option7();
                    break;
                case 8:
                    option8();
                    break;
                case 9:
                    option9();
                    break;
                case 10:
                    option10();
                    break;
                case 11:
                    option11();
                    break;
                case 12:
                    option12();
                    break;
                case 13:
                    option13();
                    break;
                case 14:
                    option14();
                    break;
                case 0:
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
            System.out.println("\t1.Play against the computer! (minMaxAB)");
            System.out.println("\t2.Play against the computer! (ABSearch)");
            System.out.println();
            System.out.println("\t3.Watch the computers play! minMaxAB vs minMaxAB");
            System.out.println("\t                              Eval 1      Eval 2");
            System.out.println("\t4.Watch the computers play!  ABSearch vs ABSearch");
            System.out.println("\t                              Eval 1      Eval 2");
            System.out.println("\t5.Watch the computers play!  minMaxAB vs ABSearch");
            System.out.println("\t                              Eval 1      Eval 1");
            System.out.println("\t6.Watch the computers play!  minMaxAB vs ABSearch");
            System.out.println("\t                              Eval 2      Eval 2");
            System.out.println("\t7.Watch the computers play!  minMaxAB vs ABSearch");
            System.out.println("\t                              Eval 1      Eval 2");
            System.out.println("\t8.Watch the computers play!  minMaxAB vs ABSearch");
            System.out.println("\t                              Eval 2      Eval 1");
            System.out.println("\t9.Watch the computers play! minMaxAB vs minMaxAB");
            System.out.println("\t                              Eval 2      Eval 1");
            System.out.println("\t10.Watch the computers play!  ABSearch vs ABSearch");
            System.out.println("\t                              Eval 2      Eval 1");
            System.out.println("\t11.Watch the computers play!  ABSearch vs minMaxAB");
            System.out.println("\t                              Eval 2      Eval 1");
            System.out.println("\t12.Watch the computers play!  ABSearch vs minMaxAB");
            System.out.println("\t                              Eval 2      Eval 2");
            System.out.println("\t13.Watch the computers play!  ABSearch vs minMaxAB");
            System.out.println("\t                              Eval 1      Eval 1");
            System.out.println("\t14.Watch the computers play!  ABSearch vs minMaxAB");
            System.out.println("\t                              Eval 1      Eval 2");
            System.out.println("\t0.Exit");
            System.out.print("Option: ");
            option = s.nextInt();
        } while (option < 0 || option > 14);
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

    /**USER PLAY AGAINST MIN-MAX-AB
     *          Max: GetScore
     *
     */
    private static void option1() {
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
//        ValueStructure turn_result = mmABGame.start(Board.getStartBoard(), 0, Board.Player.max,
//                Integer.MAX_VALUE, Integer.MIN_VALUE);
//        Board current_board = turn_result.getPath().get(0).cloneBoard();
        Board current_board = Board.getStartBoard();
        current_board.printBoard();
        String[] userMove;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            do {
                userMove = getUserMove();
            } while (!current_board.isValidMove(userMove[0], userMove[1]));
//          current_board.moveWhite(current_board.mapValue(userMove[0]), current_board.mapValue(userMove[1]));
            current_board.printBoard();
            turn_result = mmABGame.start(current_board, 0, Board.Player.max, MAX_VALUE, MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
        }
        if (current_board.getTerminal(totalGamePath)) {
            System.out.println(current_board.getWinner());
            System.out.println("Total Boards Evaluated: " + maxTotalBoards);
            System.out.println("Total Prunes: " + maxTotalPrune);
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**USER PLAY AGAINST ALPHA-BETA-SEARCH
     *          Max: GetScore
     *
     */
    public static void option2() {
        AlphaBetaSearch mmABGame = new AlphaBetaSearch();
        ValueStructure turn_result = mmABGame.start(Board.getStartBoard(), Board.Player.max);
        Board current_board = turn_result.getPath().get(0).cloneBoard();
        current_board.printBoard();
        String[] userMove;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            do {
                userMove = getUserMove();
            } while (!current_board.isValidMove(userMove[0], userMove[1]));
//          current_board.moveWhite(current_board.mapValue(userMove[0]), current_board.mapValue(userMove[1]));
            current_board.printBoard();
            turn_result = mmABGame.start(current_board, Board.Player.max);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
        }
        if (current_board.getTerminal(totalGamePath)) {
            System.out.println(current_board.getWinner());
            System.out.println("Total Boards Evaluated: " + maxTotalBoards);
            System.out.println("Total Prunes: " + maxTotalPrune);
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS TWO MIN-MAX-AB ALGORITHMS AGAINST EACH OTHER
     *              Max: GetScore  and  Min: GetScore2
     *
     */
    public static void option3() {        
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmABGame.start(current_board, 0, Board.Player.max, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            turn_result = mmABGame.start(current_board, 0, Board.Player.min, MAX_VALUE,MIN_VALUE, 2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            minTotalPrune += turn_result.pruneCount;
            minTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }    

    /**
     *THIS FUNCTION PLAYS TWO ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
     *                  Max: GetScore  and  Min: GetScore2
     */
    public static void option4() {
        AlphaBetaSearch mmABGame = new AlphaBetaSearch();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmABGame.start(current_board, Board.Player.max);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = mmABGame.start(current_board, Board.Player.min, 2);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS MIN-MAX AB VS ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore  and  Min: GetScore
     *
     */
    public static void option5() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmAB.start(current_board, 0, Board.Player.max, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = ABS.start(current_board, Board.Player.min);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS MIN-MAX AB VS ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore2  and  Min: GetScore2
     *
     */
    public static void option6() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmAB.start(current_board, 0, Board.Player.max, MAX_VALUE,MIN_VALUE,2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = ABS.start(current_board, Board.Player.min,2);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

    /**THIS FUNCTION PLAYS MIN-MAX AB VS ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore  and  Min: GetScore2
     *
     */
    public static void option7() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmAB.start(current_board, 0, Board.Player.max, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = ABS.start(current_board, Board.Player.min,2);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS MIN-MAX AB VS ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore2  and  Min: GetScore
     *
     */
    public static void option8() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmAB.start(current_board, 0, Board.Player.max, MAX_VALUE,MIN_VALUE,2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = ABS.start(current_board, Board.Player.min);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
   
    /**THIS FUNCTION PLAYS TWO MIN-MAX-AB ALGORITHMS AGAINST EACH OTHER
     *              Max: GetScore2  and  Min: GetScore
     *
     */
     public static void option9() {        
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmABGame.start(current_board, 0, Board.Player.max, MAX_VALUE,MIN_VALUE,2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            turn_result = mmABGame.start(current_board, 0, Board.Player.min, MAX_VALUE,MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            minTotalPrune += turn_result.pruneCount;
            minTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**
     *THIS FUNCTION PLAYS TWO ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
     *                  Max: GetScore2  and  Min: GetScore
     */
    public static void option10() {
        AlphaBetaSearch mmABGame = new AlphaBetaSearch();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = mmABGame.start(current_board, Board.Player.max,2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = mmABGame.start(current_board, Board.Player.min);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS  ALPHA-BETA SEARCH VS MIN-MAX AB ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore2  and  Min: GetScore
     *
     */
    public static void option11() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = ABS.start(current_board, Board.Player.max,2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = mmAB.start(current_board, 0, Board.Player.min, MAX_VALUE,MIN_VALUE);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS  ALPHA-BETA SEARCH VS MIN-MAX AB ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore2  and  Min: GetScore2
     *
     */
    public static void option12() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = ABS.start(current_board, Board.Player.max,2);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = mmAB.start(current_board, 0, Board.Player.min, MAX_VALUE,MIN_VALUE,2);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS  ALPHA-BETA SEARCH VS MIN-MAX AB ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore  and  Min: GetScore
     *
     */
    public static void option13() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = ABS.start(current_board, Board.Player.max);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = mmAB.start(current_board, 0, Board.Player.min, MAX_VALUE,MIN_VALUE);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
    
    /**THIS FUNCTION PLAYS  ALPHA-BETA SEARCH VS MIN-MAX AB ALGORITHMS AGAINST EACH OTHER
     *                      Max: GetScore  and  Min: GetScore2
     *
     */
    public static void option14() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal(totalGamePath)) {
            turn_result = ABS.start(current_board, Board.Player.max);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalGamePath += 1;
            maxTotalPrune += turn_result.pruneCount;
            maxTotalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal(totalGamePath)) {
                turn_result = mmAB.start(current_board, 0, Board.Player.min, MAX_VALUE,MIN_VALUE,2);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalGamePath += 1;
                minTotalPrune += turn_result.pruneCount;
                minTotalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal(totalGamePath)) {
                System.out.println(current_board.getWinner());
                System.out.println("\n  Game Statistics");
                System.out.println("--------------------");
                System.out.println("Total length of game path: "+ totalGamePath);
                System.out.println("Max's Total Boards Evaluated: " + maxTotalBoards);
                System.out.println("Max's Total Prunes: " + maxTotalPrune);
                System.out.println("Min's Total Boards Evaluated: " + minTotalBoards);
                System.out.println("Min's Total Prunes: " + minTotalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
}
