/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckersAI;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * 
 */
public class evalFunction {

    private static final int PAWN_VALUE = 200;
    private static final int FORWARD_PAWN_VALUE = 75;
    private static final int KING_VALUE = 500;
    private static final int NEAR_KING_VALUE = 75;
    private static final int JUMP_PAWN = 25;
    private static final int JUMP_KING = 50;
    private static final int KING_DEFENSE = 35;
    
    /**
     *
     * @param b
     * @param p
     * @return
     */
    public static int getScore(Board b, Board.Player p) {
        float bValue = 0;//new Random().nextInt(9999);
        float wValue = 0;//new Random().nextInt(9999);
        ArrayList<Integer> wPieces = b.getWhite();
        ArrayList<Integer> wK_Pieces = b.getkWhite();
        ArrayList<Integer> bPieces = b.getBlack();
        ArrayList<Integer> bK_Pieces = b.getkBlack();
        int n = b.getDIMENSION();
        float wratio = ((wPieces.size()+ wK_Pieces.size())/(bPieces.size() + bK_Pieces.size() + 1));        
        float bratio = ((bPieces.size()+ bK_Pieces.size())/(wPieces.size() + wK_Pieces.size() + 1));        
        
        
        bValue += b.getBlack().size() * PAWN_VALUE;
        bValue += b.getkBlack().size() * KING_VALUE;
        wValue += b.getWhite().size() * PAWN_VALUE;
        wValue += b.getkWhite().size() * KING_VALUE;

        //Evaluate jumps for Black pieces
        for (Integer i : bPieces) {
            if (i > 40) {
                if (i > 48) {
                    bValue += NEAR_KING_VALUE;
                } else {
                    bValue += FORWARD_PAWN_VALUE;
                }
            }
            if(b.getTopBorder().contains(i)){
                bValue+=KING_DEFENSE;
            }
            if(wPieces.isEmpty() && wK_Pieces.isEmpty()){                
                bValue+=1000;
            }
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if (wK_Pieces.contains(i + (n - 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if (wK_Pieces.contains(i + (n + 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
        }
        for (Integer i : bK_Pieces) {
//            if(b.getTopBorder().contains(i)){
//                bValue+=KING_DEFENSE;
//            }
            if(wK_Pieces.size() + wPieces.size() < 3){
                  if(b.canJumpForwardLeft(i) || b.canJumpForwardRight(i) 
                          || b.canJumpRearLeft(i) || b.canJumpRearRight(i))
                      bValue += 150;
            }
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if (wK_Pieces.contains(i + (n - 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if (wK_Pieces.contains(i + (n + 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }

            if (b.canJumpRearLeft(i)) { // check left rear jump
                if (wK_Pieces.contains(i - (n + 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if (wK_Pieces.contains(i - (n - 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
        }

        //Evaluate jumps for White pieces
        for (Integer i : wPieces) {
            if (i < 25) {
                if (i < 17) {
                    wValue += NEAR_KING_VALUE;
                } else {
                    wValue += FORWARD_PAWN_VALUE;
                }
            }
            if(b.getBottomBorder().contains(i)){
                wValue+=KING_DEFENSE;
            }
            if(bPieces.isEmpty() && bK_Pieces.isEmpty()){
                wValue+=1000;
            }
            if (b.canJumpRearLeft(i)) { // check left rear jump
                if (bK_Pieces.contains(i - (n + 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if (bK_Pieces.contains(i - (n - 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
        }
        //Evaluate jumps for KING White pieces
        for (Integer i : wK_Pieces) {
//            if(b.getBottomBorder().contains(i)){
//                wValue+=KING_DEFENSE;
//            }
            if(bK_Pieces.size() + bPieces.size() < 3){
                  if(b.canJumpForwardLeft(i) || b.canJumpForwardRight(i) 
                          || b.canJumpRearLeft(i) || b.canJumpRearRight(i))
                      wValue += 150;
            }
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if (bK_Pieces.contains(i + (n - 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if (bK_Pieces.contains(i + (n + 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }

            if (b.canJumpRearLeft(i)) { // check left rear jump
                if (bK_Pieces.contains(i - (n + 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if (bK_Pieces.contains(i - (n - 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
        }
//        if(p == minMaxAB.Player.max){return (int)bValue;}
//        return (int)wValue;
        
        if (p == Board.Player.max) {
            return (int) (bValue - wValue);
        }         
        return (int) (wValue - bValue);
//          return (int) (bValue-wValue);
    }    
        
    /**
     *
     * @param b
     * @param p
     * @return
     */
    public static int getScore2(Board b, Board.Player p) {

        int bValue = 0;//new Random().nextInt(9999);
        int wValue = 0;//new Random().nextInt(9999);

        ArrayList<Integer> wPieces = b.getWhite();
        ArrayList<Integer> wK_Pieces = b.getkWhite();
        ArrayList<Integer> bPieces = b.getBlack();
        ArrayList<Integer> bK_Pieces = b.getkBlack();
        int n = b.getDIMENSION();

        bValue += b.getBlack().size() * 100;
        bValue += b.getkBlack().size() * 200;
        wValue += b.getWhite().size() * 100;
        wValue += b.getkWhite().size() * 200;

        //Evaluate jumps for Black pieces
        for (Integer i : bPieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpForwardRight(i)) // check left foward jump
            {
                bValue += 50;
            }
        }
        //Evaluate max king pieces
        for (Integer i : bK_Pieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpRearLeft(i) || b.canJumpForwardRight(i) || b.canJumpRearRight(i)) { // check left foward jump
                bValue += 50;
            }
            //Evaluate jumps for White pieces
            if (b.canJumpRearLeft(i) || b.canJumpRearRight(i)) { // check left rear jump
                wValue += 50;

            }
        }
        //Evaluate jumps for min pieces
        for (Integer i : wPieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpForwardRight(i)) // check left foward jump
            {
                bValue += 50;
            }
        }
        //Evaluate jumps for KING White pieces
        for (Integer i : wK_Pieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpForwardRight(i) || b.canJumpRearLeft(i) || b.canJumpRearRight(i)) { // check left foward jump
                wValue += 50;
            }
        }

        if (p == Board.Player.max) {
            return (bValue);
        } else {
            return (wValue);
        }        
}

}
