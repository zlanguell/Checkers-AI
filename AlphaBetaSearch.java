/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckersAI;

import java.util.ArrayList;

/**
 *
 * @author Zachary
 */
public class AlphaBetaSearch {
    
    private static final int MAX_DEPTH = 6;
    
    public ValueStructure start(Board b){
        return alphaBetaSearch(b);
    }
    private ValueStructure alphaBetaSearch(Board b){
        return maxValue(b, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);         
    }
    
    /**
     * 
     * @param b
     * @param alpha
     * @param beta
     * @param depth
     * @return 
     */
    private ValueStructure maxValue(Board b, int alpha, int beta, int depth){
        ValueStructure currValStruct = new ValueStructure();
        ArrayList<Board> currPath = null;
        if (b.getTerminal()==true || depth > MAX_DEPTH){
            currValStruct.boardsEvaluatedCount++;
            currValStruct.setValue(evalFunc.getScore(b, minMaxAB.Player.max));
            return currValStruct;
        }
        currValStruct.setValue(Integer.MIN_VALUE);
        ArrayList<Board> successors = blackMoveGen(b);
        for(Board s : successors){
            ValueStructure v = minValue(s, alpha, beta, ++depth);
            currValStruct.boardsEvaluatedCount+=v.boardsEvaluatedCount;
            currValStruct.pruneCount+=v.pruneCount;
            int x = v.getValue();
            if(currValStruct.getValue() < x){
                currValStruct.setValue(x);
                currPath = new ArrayList<>();
                currPath.add(s);
            }
            if(currValStruct.getValue() >= beta){
                currValStruct.pruneCount++;
                currValStruct.addToPath(currPath);
                return currValStruct;
            }
            alpha = Integer.max(alpha, currValStruct.getValue());
        }            
        currValStruct.addToPath(currPath);
        return currValStruct;
    }
    
    /**
     * 
     * @param b
     * @param alpha
     * @param beta
     * @param depth
     * @return 
     */
    private ValueStructure minValue(Board b, int alpha, int beta, int depth){
        ValueStructure currValStruct = new ValueStructure();
        ArrayList<Board> currPath = null;
        if (b.getTerminal()==true || depth > MAX_DEPTH){
            currValStruct.boardsEvaluatedCount++;
            currValStruct.setValue(evalFunc.getScore(b, minMaxAB.Player.max));
            return currValStruct;
        }
        
        currValStruct.setValue(Integer.MAX_VALUE);
        ArrayList<Board> successors = whiteMoveGen(b);
        for(Board s : successors){ 
            ValueStructure v = maxValue(s, alpha, beta, ++depth);
            currValStruct.boardsEvaluatedCount+=v.boardsEvaluatedCount;
            currValStruct.pruneCount+=v.pruneCount;
            int x = v.getValue();
            if(currValStruct.getValue() > x){
                currValStruct.setValue(x);
                currPath = new ArrayList<>();
                currPath.add(s);
            }            
            if(currValStruct.getValue() <= alpha){
                currValStruct.pruneCount++;
                currValStruct.addToPath(currPath);
                return currValStruct;
            }
            beta = Integer.min(beta, currValStruct.getValue());
        }
        currValStruct.addToPath(currPath);
        return currValStruct;
    }
    
    private ArrayList<Board> blackMoveGen(Board current) {
        int n = current.getDIMENSION();
        ArrayList<Integer> bPieces = current.getBlack();
        ArrayList<Integer> wPieces = current.getWhite();
        ArrayList<Integer> bkPieces = current.getkBlack();
        ArrayList<Integer> wkPieces = current.getkWhite();        
        ArrayList<Board> children = new ArrayList<>();
        Board child;

        ///**************************BLACK*********************************///
        
            //**********************BLACK PIECE MOVE GENERATION**********************//
            for (int p : bPieces) {
                /*************************Left Side Empty or Jump**************/
                if (current.canMoveForwardLeft(p)) { //check forward left for empty
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (n - 1)); //move black piece to empty square
                    children.add(child);
                } 
                else if (current.canJumpForwardLeft(p)) { //Jump the white piece
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (2 * (n - 1))); //Move Black Piece
                    child.moveWhite(p + (n - 1), 0); //Delete Jumped White piece
                    children.add(child);
                }
                /*************************End Left Side Check******************/

                /************************Right Side Empty or Jump**************/
                if (current.canMoveForwardRight(p)) { //check forward right for empty
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (n + 1)); //move token to forward right
                    children.add(child);
                } 
                else if (current.canJumpForwardRight(p)) { //Jump White Piece
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (2 * (n + 1))); //move black piece
                    child.moveWhite(p + (n + 1), 0); //remove jumped white token
                    children.add(child);
                }
                /**************************End Right Side Check****************/
            }
            //********************END BLACK PIECE MOVE GENERATION*****************//

            //**********************BLACK KING MOVE GENERATION**********************//
            for (int k : bkPieces) {
                /**
                 * *******************Left Side Empty or
                 * Jump************************
                 */

                if (current.canMoveForwardLeft(k)) {//CHECK LEFT FRONT FOR EMPTY SPACE
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (n - 1)); //move black token to empty square
                    children.add(child);
                } else if (current.canJumpForwardLeft(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (2 * (n - 1)));
                    child.moveWhite(k + (n - 1), 0);
                    children.add(child);
                }

                if (current.canMoveRearLeft(k)) {//CHECK LEFT REAR FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (n + 1)); //move black token to empty spot
                    children.add(child);
                } else if (current.canJumpRearLeft(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (2 * (n + 1)));
                    child.moveWhite(k - (n + 1), 0);
                    children.add(child);

                }
                /**
                 * *********************End Left Side
                 * Checks*************************
                 */

                /**
                 * **************************Right Side Empty or
                 * Jump**************
                 */
                if (current.canMoveForwardRight(k)) {//CHECK RIGHT FORWARD FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (n + 1)); //move black token forward right to empty space
                    children.add(child);

                } else if (//CHECK RIGHT FORWARD FOR JUMP
                        current.canJumpForwardRight(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (2 * (n + 1)));
                    child.moveWhite(k + (n + 1), 0);
                    children.add(child);
                }

                if (current.canMoveRearRight(k)) {//CHECK RIGHT REAR FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (n - 1)); //move black king right rear to empty space
                    children.add(child);

                } else if (//CHECK RIGHT REAR FOR JUMP
                        current.canJumpRearRight(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (2 * (n - 1)));
                    child.moveWhite(k - (n - 1), 0);
                    children.add(child);
                }
                /**
                 * *********************End Right Side
                 * Checks*************************
                 */
            }
            //********************END BLACK KING MOVE GENERATION*****************//
         ///************************END BLACK*******************************///       
        
        return children; //RETURN ALL GENERATED CHILDREN
    }
    
    private ArrayList<Board> whiteMoveGen(Board current) {
        int n = current.getDIMENSION();
        ArrayList<Integer> bPieces = current.getBlack();
        ArrayList<Integer> wPieces = current.getWhite();
        ArrayList<Integer> bkPieces = current.getkBlack();
        ArrayList<Integer> wkPieces = current.getkWhite();        
        ArrayList<Board> children = new ArrayList<>();
        Board child;
        ///**************************WHITE*********************************///		
        
            //**********************WHITE PIECE MOVE GENERATION**********************//
            for (int p : wPieces) {
                /**
                 * ***********************Left Side Empty or
                 * Jump************************
                 */
                if (current.canMoveRearLeft(p)) { //check left rear for empty
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (n + 1));
                    children.add(child);
                } else if ( //CHECK LEFT REAR FOR JUMP
                        current.canJumpRearLeft(p)) {
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (2 * (n + 1))); //move white token
                    child.moveBlack(p - (n + 1), 0);  //remove jumped black token
                    children.add(child);
                }
                /**
                 * ***********************End Left Side
                 * Check****************************
                 */

                /**
                 * **********************Right Side Empty or
                 * Jump************************
                 */
                if (current.canMoveRearRight(p)) { //check rear right for empty
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (n - 1)); //move white token right rear
                    children.add(child);

                } else if ( //check for right rear jump
                        current.canJumpRearRight(p)) {
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (2 * (n - 1))); //move white piece
                    child.moveBlack(p - (n - 1), 0); //delete black piece
                    children.add(child);
                }
            }
            /***********************End Right Side Check***********************/
        
        //********************END WHITE PIECE MOVE GENERATION*****************//
        //**********************WHITE KING MOVE GENERATION**********************//
        for (int k : wkPieces) {
            /************************Left Side Empty or Jump*******************/
            if (current.canMoveRearLeft(k)) {//check left rear for empty
                child = current.cloneBoard();
                child.moveWhite(k, k - (n + 1));
                children.add(child);
            } else if ( // check left rear for jump
                    current.canJumpRearLeft(k)) {
                child = current.cloneBoard();
                child.moveWhite(k, k - (2 * (n + 1))); //move white king
                child.moveBlack(k - (n + 1), 0); //remove jumped black token
                children.add(child);
            }

            if (current.canMoveForwardLeft(k)) {//check forward left for empty
                child = current.cloneBoard();
                child.moveWhite(k, k + (n - 1)); //move left forward
                children.add(child);
            } else if (//check left forward for jump
                    current.canJumpForwardLeft(k)) {
                child = current.cloneBoard();
                child.moveWhite(k, k + (2 * (n - 1))); //move white king
                child.moveBlack(k + (n - 1), 0); //remove jumped black token
                children.add(child);
            }
            /*******************End Left Side Check****************************/
            
            /*****************Right Side Empty or Jump*************************/
            if (current.canMoveRearRight(k)) { //check rear right for empty
                child = current.cloneBoard();
                child.moveWhite(k, k - (n - 1)); //move white token right rear
                children.add(child);
            } else if ( //check for right rear jump
                    current.canJumpRearRight(k)) {
                child = current.cloneBoard();
                child.moveWhite(k, k - (2 * (n - 1))); //move white piece
                child.moveBlack(k - (n - 1), 0); //delete black piece
                children.add(child);
            }

            if (current.canMoveForwardRight(k)) { //check front right for empty
                child = current.cloneBoard();
                child.moveWhite(k, k + (n + 1)); //move white token right front
                children.add(child);
            } else if ( //check for right front jump
                    current.canJumpForwardRight(k)) {
                child = current.cloneBoard();
                child.moveWhite(k, k + (2 * (n + 1))); //move white piece
                child.moveBlack(k + (n + 1), 0); //delete black piece
                children.add(child);
            }
            /**
             * *****************End Right Side Check***************************
             */
            //********************END WHITE KING MOVE GENERATION*****************//
        
        ///************************END WHITE*******************************///
        
        }
        return children;
    }
}
