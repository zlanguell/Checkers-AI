package CheckersAI;

import java.util.ArrayList;

public class ValueStructure {
    
    private int value;
    //private int newValue;
    private ArrayList<Board> path;
    public int boardsEvaluatedCount=0;
    public int pruneCount = 0;
    public ValueStructure() {
        path = new ArrayList<>();
    }
    public ValueStructure(Board b) {
        this.path = new ArrayList<>();
        this.path.add(b);
    }
    public ValueStructure (int v){
        this.value = v;
    }
    public ValueStructure(int v, ArrayList<Board> b) {
        this.value = v;
        this.path = new ArrayList<>();
        path.addAll(b);
    }

    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }
//    public int getNewValue() {
//        return this.newValue;
//    }
//    public void setNewValue(int value) {
//        this.newValue = value;
//    }
    public ArrayList<Board> getPath() {
        return new ArrayList<>(path);
    }
    public void addToPath(Board newPathNode) {
        //if (newPathNode) {
            path.add(newPathNode);
        //}
    }
    public void addToPath(ArrayList<Board> newPath) {
        //if (!newPath.isEmpty()) {
            path.addAll(newPath);
       // }
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
