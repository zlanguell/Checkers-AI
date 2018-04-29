package CheckersAI;

import java.util.ArrayList;

/**
 * This class is used as result of an algorithm. It can store the path and value
 * of the outcome. When making moves, only the first board is used. The remaining
 * path is used for evaluation and testing.
 * 
 */
public class ValueStructure {
    
    private int value;
    //private int newValue;
    private ArrayList<Board> path;

    /**
     * Board Count is used to evaluate complexity by counting boards the 
     * algorithm computed values.
     */
    public int boardsEvaluatedCount=0;

    /**
     * Prune count is used to evaluate unexplored branches.
     */
    public int pruneCount = 0;

    /**
     * Creates a value structure with no values.
     */
    public ValueStructure() {
        path = new ArrayList<>();
    }

    /**
     * Creates a value structure with path, but unassigned value.
     * @param b
     */
    public ValueStructure(Board b) {
        this.path = new ArrayList<>();
        this.path.add(b);
    }

    /**
     * Creates a valued structure with no path.
     * @param v
     */
    public ValueStructure (int v){
        this.value = v;
    }

    /**
     * Sets the structure to board value and path to board.
     * @param v
     * @param b
     */
    public ValueStructure(int v, ArrayList<Board> b) {
        this.value = v;
        this.path = new ArrayList<>();
        path.addAll(b);
    }

    /**
     * Returns the value.
     * @return
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value.
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }
//    public int getNewValue() {
//        return this.newValue;
//    }
//    public void setNewValue(int value) {
//        this.newValue = value;
//    }

    /**
     * Returns path to final node of the value structure.
     * @return
     */
    public ArrayList<Board> getPath() {
        return new ArrayList<>(path);
    }

    /**
     * Adds a node to path.
     * @param newPathNode
     */
    public void addToPath(Board newPathNode) {
        //if (newPathNode) {
            path.add(newPathNode);
        //}
    }

    /**
     * Adds multiple node path.
     * @param newPath
     */
    public void addToPath(ArrayList<Board> newPath) {
       //if (newPath != null) {
            path.addAll(newPath);
       //}
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ValueStructure value=");
        s.append(value);        
        s.append(", path=\n");
        if (!path.isEmpty()) {
            for (Board b : path) {
                s.append(b.toString());
                s.append("\n Value=");
                s.append(b.getValue());
                s.append("\n\n");
            }
        }
        else {s.append("NULL}");}
        return s.toString();
    }

}
