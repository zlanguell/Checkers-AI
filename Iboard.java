/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckersAI;

import java.util.ArrayList;

/**
 *
 * 
 */
public interface Iboard {
    // Two Player Games
    public enum Player{min, max};    
    // Prints the board in current state
    public void printBoard();    
    // Returns the static final square dimension of the game board
    public int getDIMENSION();    
    // Generate children from current board state based on player
    public ArrayList<Board> moveGen(Board b, Player p);    
    // Switch Players
    public Player switchPlayer(Player p);
    // Return the starting board
    public Board getStartBoard();
}
