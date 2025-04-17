package mainFiles;

import javax.swing.*;

public class GeneralGame extends Game {
    private char currentPlayerCharacter;
    private int lastRow = -1;
    private int lastCol = -1;
    private boolean gameOver = false;

    public GeneralGame(int boardSize, Player player1, Player player2) {
        super(boardSize, player1, player2);
        this.currentPlayer = player1;
    }

    //Check win method checks if any new SOS patterns have been made in the current game
    //and updates the score of the player who made the SOS pattern. If the board is full,
    //the game will declare a winner based on the scores of the players
    @Override
    public boolean checkWin() {
        // In General Game, checkWin is used to check for SOS patterns and award points
        boolean foundSOS = false;
        
        // Only check the last move's position and surrounding cells
        int row = lastRow;
        int col = lastCol;
        
        if (row != -1 && col != -1) {  // If there was a last move
            if (checkSOS(row, col)) {
                foundSOS = true;
                currentPlayer.score++;
            }
        }
        
        return foundSOS;
    }

    //Used to make a move in the game along with tracking the position of the last move, and if the move
    //made an SOS pattern or not. If an SOS pattern was made, the score of the player who made it is
    //increased using the check win method
    @Override
    public void makeMove(int row, int col) {
        if (!gameOver && row >= 0 && row < board_Size && col >= 0 && col < board_Size && grid[row][col] == 0) {
            currentPlayer.makeMove(grid, row, col);
            lastRow = row;
            lastCol = col;
            
            // Check if the move created an SOS pattern
            boolean createdSOS = checkWin();
            
            if (checkTie()) {
                // Game is over, determine winner based on scores
                if (player1.score > player2.score) {
                    System.out.println("Game Over! " + player1.getName() + " wins with " + player1.score + " points!");
                } else if (player2.score > player1.score) {
                    System.out.println("Game Over! " + player2.getName() + " wins with " + player2.score + " points!");
                } else {
                    System.out.println("Game Over! It's a tie with " + player1.score + " points each!");
                }
            } else if (!createdSOS) {
                // Only switch turns if no SOS was created
                switchTurn();
            }
        }
    }

    //Checks if the board is full. Used to check for a tie
    public boolean IsBoardFull(){
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (grid[row][col] == 0) {
                    return false; // Found an empty cell
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkTie() {
        // Check if all cells are filled
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (grid[row][col] == 0) {
                    return false; // Found an empty cell
                }
            }
        }
        gameOver = true;
        return true; // Board is full
    }
}
