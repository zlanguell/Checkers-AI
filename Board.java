package CheckersAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * ****************************************************************************
 * CHECKERS
 *
 * |01|02|03|04|05|06|07|08| |09|10|11|12|13|14|15|16| |17|18|19|20|21|22|23|24|
 * |25|26|27|28|29|30|31|32| |33|34|35|36|37|38|39|40| |41|42|43|44|45|46|47|48|
 * |49|50|51|52|53|54|55|56| |57|58|59|60|61|62|63|64|
 *
 *
 * Player Max: -Black Tokens -Computer Controlled -Starts Top Border
 *
 * Player Min: -White Tokens -Player or Computer Controlled -Starts Bottom
 * Border
 * *****************************************************************************
 */
public class Board {

    public int depth;
    private static final int DIMENSION = 8;
    private HashMap<String, Integer> boardMapper = new HashMap<>();
    ArrayList<Integer> rightBorder = new ArrayList<Integer>() {
        {
            add(8);
            add(16);
            add(24);
            add(32);
            add(40);
            add(48);
            add(56);
            add(64);
        }
    };
    ArrayList<Integer> leftBorder = new ArrayList<Integer>() {
        {
            add(01);
            add(9);
            add(17);
            add(25);
            add(33);
            add(41);
            add(49);
            add(57);
        }
    };
    ArrayList<Integer> bottomBorder = new ArrayList<Integer>() {
        {
            add(57);
            add(58);
            add(59);
            add(60);
            add(61);
            add(62);
            add(63);
            add(64);
        }
    };
    ArrayList<Integer> topBorder = new ArrayList<Integer>() {
        {
            add(01);
            add(02);
            add(03);
            add(04);
            add(05);
            add(06);
            add(07);
            add(8);
        }
    };
    private ArrayList<Integer> white = new ArrayList<>();
    private ArrayList<Integer> black = new ArrayList<>();
    private ArrayList<Integer> kBlack = new ArrayList<>();
    private ArrayList<Integer> kWhite = new ArrayList<>();
    private float value;
    private boolean terminal;

    public Board() {
        value = 0;
        terminal = false;
        populateBoardMapper();
    }

    public Board(Board b) {
        this.white = b.getWhite();
        this.black = b.getBlack();
        this.kBlack = b.getkBlack();
        this.kWhite = b.getkWhite();
        value = 0;
        terminal = false;
        populateBoardMapper();
    }

    private void populateBoardMapper() {
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        int squareNumber = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 8; j++) {
                boardMapper.put(letters[i] + j, squareNumber);
                squareNumber++;
            }
        }
    }

    public int mapValue(String s) {
        return boardMapper.get(s);
    }

    public static Board getStartBoard() {
        Board b = new Board();
        b.setBlack(new ArrayList<>(Arrays.asList(2, 4, 6, 8, 9, 11, 13, 15, 18, 20, 22, 24)));
        b.setWhite(new ArrayList<>(Arrays.asList(41, 43, 45, 47, 50, 52, 54, 56, 57, 59, 61, 63)));
        return b;
    }

    public boolean getTerminal() {
        return this.terminal;
    }

    public void setTerminal() {
        this.terminal = true;
    }

    public ArrayList<Integer> getRightBorder() {
        return rightBorder;
    }

    public ArrayList<Integer> getLeftBorder() {
        return leftBorder;
    }

    public ArrayList<Integer> getBottomBorder() {
        return bottomBorder;
    }

    public ArrayList<Integer> getTopBorder() {
        return topBorder;
    }

    public int getDIMENSION() {
        return DIMENSION;
    }

    public ArrayList<Integer> getWhite() {
        return new ArrayList<>(this.white);
    }

    public void setWhite(ArrayList<Integer> white) {
        this.white = white;
    }

    public ArrayList<Integer> getkWhite() {
        return new ArrayList<>(this.kWhite);
    }

    public void setkWhite(ArrayList<Integer> kWhite) {
        this.kWhite = kWhite;
    }

    public ArrayList<Integer> getBlack() {
        return new ArrayList<>(this.black);
    }

    public void setBlack(ArrayList<Integer> black) {
        this.black = black;
    }

    public ArrayList<Integer> getkBlack() {
        return new ArrayList<>(this.kBlack);
    }

    public void setkBlack(ArrayList<Integer> kBlack) {
        this.kBlack = kBlack;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isEmpty(int space) {

        return (!white.contains(space) && !black.contains(space)
                && !kWhite.contains(space) && !kBlack.contains(space));
    }

    public void moveBlack(int oldp, int newp) {
        /*System.out.println("Before Black");
        this.printBoard();*/
        Integer oldP = oldp;
        Integer newP = newp;
        if (newP != 0) {
            if (this.kBlack.contains(oldP)) {
                this.kBlack.remove(oldP);
                this.kBlack.add(newP);
            } else if (this.black.contains(oldP)) {
                this.black.remove(oldP);
                if (this.bottomBorder.contains(newP)) {
                    this.kBlack.add(newP);
                } else {
                    this.black.add(newP);
                }

            }
        } else if (this.kBlack.contains(oldP)) {
            this.kBlack.remove(oldP);
        } else if (this.black.contains(oldP)) {
            this.black.remove(oldP);
        }
        checkTerminal();
        /*System.out.println("After Black");
        this.printBoard();
        System.out.println("\n\n");*/
    }

    public void moveWhite(int oldp, int newp) {
        /*System.out.println("Before White:");
        this.printBoard();*/
        Integer oldP = oldp;
        Integer newP = newp;
        if (newP != 0) {
            if (kWhite.contains(oldP)) {
                kWhite.remove(oldP);
                kWhite.add(newP);
            } else if (white.contains(oldP)) {
                white.remove(oldP);
                if (this.topBorder.contains(newP)) {
                    this.kWhite.add(newP);
                } else {
                    white.add(newP);
                }
            }
        } else if (kWhite.contains(oldP)) {
            kWhite.remove(oldP);
        } else if (white.contains(oldP)) {
            white.remove(oldP);
        }
        checkTerminal();
        /*System.out.println("After White");
        this.printBoard();
        System.out.println("\n\n");*/
    }

    public void printBoard() {
        int n = getDIMENSION();
        ArrayList<Integer> bP = getBlack();
        ArrayList<Integer> wP = getWhite();
        ArrayList<Integer> bK = getkBlack();
        ArrayList<Integer> wK = getkWhite();
        char[] rowIndex = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        char[] colIndex = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        int tile = 1;
        for (int row = -1; row < n; row++) {
            if (row >= 0) {
                System.out.print(rowIndex[row] + "\t");
            } else {
                System.out.print("\t");
            }
            for (int col = 0; col < n; col++) {
                if (row == -1) {
                    System.out.print("  " + colIndex[col] + " ");
                    continue;
                }
                char token;
                if (bP.contains(tile)) {
                    token = 'b';
                } else if (wP.contains(tile)) {
                    token = 'w';
                } else if (bK.contains(tile)) {
                    token = 'B';
                } else if (wK.contains(tile)) {
                    token = 'W';
                } else {
                    token = '_';
                }
                System.out.print("| " + token + " ");
                tile++;
            }
            System.out.println();
        }
        //System.out.println("Value is: " + this.getValue());
    }

    private void checkTerminal() {
        if ((this.black.isEmpty() && this.kBlack.isEmpty()) || (this.white.isEmpty() && this.kWhite.isEmpty())) {
            this.terminal = true;
        }
    }

    //***************MOVE VALIDATION************************//
    public boolean isValidMove(String currMove, String newMove) {
        //convert B1 to 9
        int parsedCurrMove;
        int parsedNewMove;

        if (boardMapper.containsKey(currMove) && boardMapper.containsKey(newMove)) {
            parsedCurrMove = boardMapper.get(currMove);
            parsedNewMove = boardMapper.get(newMove);
            if (white.contains(parsedCurrMove) || kWhite.contains(parsedCurrMove)) {
                if (parsedNewMove == (parsedCurrMove - (DIMENSION + 1))) {
                    if (canMoveRearLeft(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        return true;
                    }
                } else if (parsedNewMove == (parsedCurrMove - (DIMENSION - 1))) {
                    if (canMoveRearRight(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        return true;
                    }
                } else if (parsedNewMove == (parsedCurrMove - 2 * (DIMENSION + 1))) {
                    if (canJumpRearLeft(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        this.moveBlack(parsedCurrMove - (DIMENSION + 1), 0);
                        return true;
                    }
                } else if (parsedNewMove == (parsedCurrMove - 2 * (DIMENSION - 1))) {
                    if (canJumpRearRight(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        this.moveBlack(parsedCurrMove - (DIMENSION - 1), 0);
                        return true;
                    }
                }
                if (kWhite.contains(parsedCurrMove)) {
                    if (parsedNewMove == (parsedCurrMove + (DIMENSION + 1))) {
                        if (canMoveForwardRight(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            return true;
                        }
                    } else if (parsedNewMove == (parsedCurrMove + (DIMENSION - 1))) {
                        if (canMoveForwardLeft(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            return true;
                        }
                    } else if (parsedNewMove == (parsedCurrMove + 2 * (DIMENSION + 1))) {
                        if (canJumpForwardRight(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            this.moveBlack(parsedCurrMove + (DIMENSION + 1), 0);
                            return true;
                        }
                    } else if (parsedNewMove == (parsedCurrMove + 2 * (DIMENSION - 1))) {
                        if (canJumpForwardLeft(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            this.moveBlack(parsedCurrMove + (DIMENSION - 1), 0);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canMoveForwardLeft(int pos) {
        int pos2 = pos + (this.getDIMENSION() - 1);
        if (this.getBottomBorder().contains(pos) || this.getLeftBorder().contains(pos)
                || this.getBlack().contains(pos2) || this.getWhite().contains(pos2)
                || this.getkBlack().contains(pos2) || this.getkWhite().contains(pos2)) {
            return false;
        } else {
            return true;
        }

    }

    public boolean canMoveForwardRight(int pos) {
        int pos2 = pos + (this.getDIMENSION() + 1);
        if (this.getBottomBorder().contains(pos) || this.getRightBorder().contains(pos)
                || this.getBlack().contains(pos2) || this.getWhite().contains(pos2)
                || this.getkBlack().contains(pos2) || this.getkWhite().contains(pos2)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canMoveRearLeft(int pos) {
        int pos2 = pos - (this.getDIMENSION() + 1);
        if (this.getTopBorder().contains(pos) || this.getLeftBorder().contains(pos)
                || this.getBlack().contains(pos2) || this.getWhite().contains(pos2)
                || this.getkBlack().contains(pos2) || this.getkWhite().contains(pos2)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canMoveRearRight(int pos) {
        int pos2 = pos - (this.getDIMENSION() - 1);
        if (this.getTopBorder().contains(pos) || this.getRightBorder().contains(pos)
                || this.getBlack().contains(pos2) || this.getWhite().contains(pos2)
                || this.getkBlack().contains(pos2) || this.getkWhite().contains(pos2)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canJumpForwardLeft(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos + (DIMENSION - 1);
        int pos3 = pos2 + (DIMENSION - 1);
        if (enemy.contains(pos2) && !this.getBottomBorder().contains(pos2)
                && !this.getLeftBorder().contains(pos2) && isEmpty(pos3)) {
            return true;
        }
        return false;
    }

    public boolean canJumpForwardRight(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos + (DIMENSION + 1);
        int pos3 = pos2 + (DIMENSION + 1);
        if (enemy.contains(pos2) && !this.getBottomBorder().contains(pos2)
                && !this.getRightBorder().contains(pos2) && isEmpty(pos3)) {
            return true;
        }
        return false;
    }

    public boolean canJumpRearLeft(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos - (DIMENSION + 1);
        int pos3 = pos2 - (DIMENSION + 1);
        if (enemy.contains(pos2) && !this.getTopBorder().contains(pos2)
                && !this.getLeftBorder().contains(pos2) && isEmpty(pos3)) {
            return true;
        }
        return false;
    }

    public boolean canJumpRearRight(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos - (DIMENSION - 1);
        int pos3 = pos2 - (DIMENSION - 1);
        if (enemy.contains(pos2) && !this.getTopBorder().contains(pos2)
                && !this.getRightBorder().contains(pos2) && isEmpty(pos3)) {
            return true;
        }
        return false;
    }
//***************END MOVE VALIDATION************************//

    private ArrayList<Integer> getEnemies(int pos) {
        ArrayList<Integer> enemy;
        if (this.getBlack().contains(pos) || this.getkBlack().contains(pos)) {
            enemy = this.getWhite();
            enemy.addAll(this.getkWhite());
        } else {
            enemy = this.getBlack();
            enemy.addAll(this.getkBlack());
        }
        return enemy;
    }

    public Board cloneBoard() {
        Board child = new Board();
        child.setBlack(this.getBlack());
        child.setWhite(this.getWhite());
        child.setkBlack(this.getkBlack());
        child.setkWhite(this.getkWhite());
        if(this.getTerminal() == true){
            child.setTerminal();
        }
        return child;
    }

    @Override
    public String toString() {

        int n = getDIMENSION();
        StringBuilder s = new StringBuilder();
        s.append("Depth =").append(this.depth).append("\n");
        ArrayList<Integer> bP = getBlack();
        ArrayList<Integer> wP = getWhite();
        ArrayList<Integer> bK = getkBlack();
        ArrayList<Integer> wK = getkWhite();
        char[] rowIndex = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        char[] colIndex = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        int tile = 1;
        for (int row = -1; row < n; row++) {
            if (row >= 0) {
                s.append(rowIndex[row]).append("\t");
            } else {
                s.append("\t");
            }
            for (int col = 0; col < n; col++) {
                if (row == -1) {
                    s.append("  ").append(colIndex[col]).append(" ");
                    continue;
                }
                char token;
                if (bP.contains(tile)) {
                    token = 'b';
                } else if (wP.contains(tile)) {
                    token = 'w';
                } else if (bK.contains(tile)) {
                    token = 'B';
                } else if (wK.contains(tile)) {
                    token = 'W';
                } else {
                    token = '_';
                }
                s.append("| ").append(token).append(" ");
                tile++;
            }
            s.append("\n");
        }
        return s.toString();
    }
     public String getWinner() {
        if (this.getkWhite().size() + this.getWhite().size() == 0)
            return "Player with Black pieces is the winner";
        else if (this.getBlack().size() + this.getkBlack().size() == 0) {
            return "Player with White pieces is the winner";
        }
        return "Winner not decided yet";
    }
}
