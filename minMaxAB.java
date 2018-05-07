package CheckersAI;

import java.util.ArrayList;

/**
 *
 * @author Zachary
 */
public class minMaxAB {

    private static final int MAX_DEPTH = 10;
    private static int scoreOption = 1;

    /**
     *
     * @param position
     * @param depth
     * @param player
     * @param maxUse
     * @param minPass
     * @return
     */
    public ValueStructure start(Board position, int depth, Board.Player player, int maxUse, int minPass) {
        scoreOption = 1;
        return minMax(position, depth, player, maxUse, minPass);
    }
    
    public ValueStructure start(Board position, int depth, Board.Player player, int maxUse, int minPass, int scoreOption){
        this.scoreOption = scoreOption;
        return minMax(position, depth, player, maxUse, minPass);
    }
    
    private ValueStructure minMax(Board position, int depth, Board.Player player, int useThresh, int passThresh) {
        ValueStructure currValueStructure = new ValueStructure();
        ArrayList<Board> currPath = new ArrayList<>(); //Used to temporarily store best path
        if (deepEnough(depth)) {
            currValueStructure.boardsEvaluatedCount++;
            currValueStructure.setValue(Static(position, player));
            //currValueStructure.addToPath(position); //should be null         
            return currValueStructure;
        }

        //Generate children for this turn
        ArrayList<Board> successors = moveGen(position, player);

        //No children means terminal state reached
        if (successors.isEmpty()) {
            position.setTerminal();
            currValueStructure.setValue(Static(position, player));
            currPath = new ArrayList<>();
            currPath.add(position);
            currValueStructure.addToPath(currPath);
           // currValueStructure.addToPath(position);
            return currValueStructure;
        }

        for (Board v : successors) {
            ValueStructure resultSucc = minMax(v, (++depth), Board.switchPlayer(player), ((-1) * passThresh), ((-1) * useThresh));
            currValueStructure.boardsEvaluatedCount += resultSucc.boardsEvaluatedCount;
            currValueStructure.pruneCount += resultSucc.pruneCount;
            int newValue = ((resultSucc.getValue() * (-1))); //new value
            if (newValue > passThresh) { //Better child board found
                passThresh = newValue;
                currPath = new ArrayList<>();
                currPath.add(v);
                currPath.addAll(resultSucc.getPath());
            }
            if (passThresh >= useThresh) { //Prune
                currValueStructure.pruneCount++;
                currValueStructure.setValue(passThresh);
                currValueStructure.addToPath(currPath);
                return currValueStructure;
            }
        }
        currValueStructure.addToPath(currPath);
        currValueStructure.setValue(passThresh);
        return currValueStructure;
    }

    private boolean deepEnough(int depth) {
        return depth > MAX_DEPTH;
    }

    private static int Static(Board position, Board.Player player) {
        //Make a call to eval function
        if(scoreOption ==1){
            return evalFunction.getScore(position, player);
        }
        return evalFunction.getScore2(position, player);
    }

    private ArrayList<Board> moveGen(Board current, Board.Player player) {
        
        return Board.moveGen(current, player);
    }
}
