package mainFiles;

public class SimpleGame extends Game {
    private boolean gameOver = false;

    public SimpleGame(int boardSize, Player player1, Player player2) {
        super(boardSize, player1, player2);
    }

    @Override
    public boolean checkWin() {
        // Check for SOS pattern in rows, columns, and diagonals
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (checkSOS(row, col)) {
                    gameOver = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkTie() {
        // Check if all cells are filled and no SOS was made
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (grid[row][col] == 0) {
                    return false; // Found an empty cell
                }
            }
        }
        return !gameOver; // It's a tie if board is full and no SOS was made
    }

    @Override
    public void makeMove(int row, int col) {
        if (!gameOver && row >= 0 && row < board_Size && col >= 0 && col < board_Size && grid[row][col] == 0) {
            currentPlayer.makeMove(grid, row, col);
            if (checkWin()) {
                System.out.println("Game Over! " + currentPlayer.getName() + " wins!");
            } else if (checkTie()) {
                System.out.println("Game Over! It's a tie!");
            } else {
                switchTurn();
            }
        }
    }
}
