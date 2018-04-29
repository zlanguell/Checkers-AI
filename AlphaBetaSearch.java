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
    
    private static final int MAX_DEPTH = 10;
    private int scoreOption=1;
    
    /**
     *
     * @param b
     * @param p
     * @return
     */
    public ValueStructure start(Board b, Board.Player p){
        scoreOption = 1;
        return alphaBetaSearch(b, p);
    }
    
    /**
     *
     * @param b
     * @param p
     * @param scoringOption
     * @return
     */
    public ValueStructure start(Board b, Board.Player p, int scoringOption){
        scoreOption = scoringOption;
        return alphaBetaSearch(b, p);
    }
    
    /**
     *
     * @param b
     * @param p
     * @return
     */
    private ValueStructure alphaBetaSearch(Board b, Board.Player p){
        return maxValue(b,  Integer.MIN_VALUE, Integer.MAX_VALUE, 0, p);         
    }
    
    /**
     * 
     * @param b
     * @param alpha
     * @param beta
     * @param depth
     * @return 
     */
    private ValueStructure maxValue(Board b, int alpha, int beta, int depth, Board.Player p){
        ValueStructure currValStruct = new ValueStructure();
        ArrayList<Board> currPath = null;
        
        if (b.getTerminal()==true || depth > MAX_DEPTH){
            currValStruct.boardsEvaluatedCount++;            
            currValStruct.setValue(Static(b, p));            
            return currValStruct;
        }
        currValStruct.setValue(Integer.MIN_VALUE);
        ArrayList<Board> successors = Board.moveGen(b, p);
        if (successors.isEmpty()){
            b.setTerminal();
            currValStruct.boardsEvaluatedCount++;            
            currValStruct.setValue(Static(b, Board.switchPlayer(p)));            
            return currValStruct;
        }
        for(Board s : successors){
            ValueStructure v = minValue(s, alpha, beta, ++depth, Board.switchPlayer(p));
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
    private ValueStructure minValue(Board b, int alpha, int beta, int depth, Board.Player p){
        ValueStructure currValStruct = new ValueStructure();
        ArrayList<Board> currPath = null;
        
        if (b.getTerminal()==true || depth > MAX_DEPTH){
            currValStruct.boardsEvaluatedCount++;            
            currValStruct.setValue(Static(b, Board.switchPlayer(p)));            
            return currValStruct;
        }
        
        currValStruct.setValue(Integer.MAX_VALUE);
        ArrayList<Board> successors = Board.moveGen(b, p);
        
        if (successors.isEmpty()){
            b.setTerminal();
            currValStruct.boardsEvaluatedCount++;            
            currValStruct.setValue(Static(b, Board.switchPlayer(p)));            
            return currValStruct;
        }
        for(Board s : successors){ 
            ValueStructure v = maxValue(s, alpha, beta, ++depth, Board.switchPlayer(p));
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
    
    /**
     *
     * @param b
     * @param p
     * @return
     */
    private int Static(Board b, Board.Player p){
        if(scoreOption == 1){
            return evalFunction.getScore(b, p);
        }
        return evalFunction.getScore2(b,p);
    }    
}
