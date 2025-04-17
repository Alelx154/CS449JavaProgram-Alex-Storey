package mainFiles;

import java.util.Random;

public class ComputerPlayer extends Player {
    public int chosenRow;
    public int chosenCol;

    public ComputerPlayer(String name) {
        super(name);
    }

    public int[] findBestMove(int[][] grid){
        int boardSize = grid.length;
        int[] bestMove = null;
        int bestScore = -1;
        char bestSymbol = 'S';

        // Try both S and O to find the best move
        for (char symbol : new char[]{'S', 'O'}) {
            setSymbol(symbol);
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (grid[row][col] == 0) { // Check only empty cells
                        // Simulate the move
                        grid[row][col] = (symbol == 'S') ? 1 : 2;
                        int score = evaluateMove(grid, row, col);
                        // Undo the move
                        grid[row][col] = 0;

                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = new int[]{row, col};
                            bestSymbol = symbol;
                        }
                    }
                }
            }
        }

        setSymbol(bestSymbol);
        return bestMove;
    }

    private int evaluateMove(int[][] grid, int row, int col) {
        int score = 0;
        // Check for immediate SOS opportunities
        if (canFormSOS(grid, row, col)) {
            score += 100; // High score for immediate SOS
        }
        // Check for potential SOS in next move
        if (canCreateSOSOpportunity(grid, row, col)) {
            score += 50; // Medium score for creating SOS opportunity
        }
        // Prefer center and corners for better strategic positions
        int center = grid.length / 2;
        if ((row == center && col == center) ||
                (row == 0 && col == 0) ||
                (row == 0 && col == grid.length - 1) ||
                (row == grid.length - 1 && col == 0) ||
                (row == grid.length - 1 && col == grid.length - 1)) {
            score += 10;
        }
        return score;
    }

    private boolean canCreateSOSOpportunity(int[][] grid, int row, int col) {
        int boardSize = grid.length;
        int s = 1; // S is represented as 1
        int o = 2; // O is represented as 2

        // Check if placing this symbol creates a potential SOS pattern
        if (getSymbol() == 'S') {
            // Check if placing S creates a potential S-O-S pattern
            // Vertical up
            if (row >= 2 && isValid(grid, row - 1, col, o) && grid[row - 2][col] == 0) return true;
            // Vertical down
            if (row <= boardSize - 3 && isValid(grid, row + 1, col, o) && grid[row + 2][col] == 0) return true;
            // Horizontal left
            if (col >= 2 && isValid(grid, row, col - 1, o) && grid[row][col - 2] == 0) return true;
            // Horizontal right
            if (col <= boardSize - 3 && isValid(grid, row, col + 1, o) && grid[row][col + 2] == 0) return true;
            // Diagonal up-left
            if (row >= 2 && col >= 2 && isValid(grid, row - 1, col - 1, o) && grid[row - 2][col - 2] == 0) return true;
            // Diagonal up-right
            if (row >= 2 && col <= boardSize - 3 && isValid(grid, row - 1, col + 1, o) && grid[row - 2][col + 2] == 0) return true;
            // Diagonal down-left
            if (row <= boardSize - 3 && col >= 2 && isValid(grid, row + 1, col - 1, o) && grid[row + 2][col - 2] == 0) return true;
            // Diagonal down-right
            if (row <= boardSize - 3 && col <= boardSize - 3 && isValid(grid, row + 1, col + 1, o) && grid[row + 2][col + 2] == 0) return true;
        } else if (getSymbol() == 'O') {
            // Check if placing O creates a potential S-O-S pattern
            // Vertical
            if (row > 0 && row < boardSize - 1 && isValid(grid, row - 1, col, s) && grid[row + 1][col] == 0) return true;
            if (row > 0 && row < boardSize - 1 && isValid(grid, row + 1, col, s) && grid[row - 1][col] == 0) return true;
            // Horizontal
            if (col > 0 && col < boardSize - 1 && isValid(grid, row, col - 1, s) && grid[row][col + 1] == 0) return true;
            if (col > 0 && col < boardSize - 1 && isValid(grid, row, col + 1, s) && grid[row][col - 1] == 0) return true;
            // Diagonal \
            if (row > 0 && col > 0 && row < boardSize - 1 && col < boardSize - 1 &&
                    isValid(grid, row - 1, col - 1, s) && grid[row + 1][col + 1] == 0) return true;
            if (row > 0 && col > 0 && row < boardSize - 1 && col < boardSize - 1 &&
                    isValid(grid, row + 1, col + 1, s) && grid[row - 1][col - 1] == 0) return true;
            // Diagonal /
            if (row > 0 && col > 0 && row < boardSize - 1 && col < boardSize - 1 &&
                    isValid(grid, row - 1, col + 1, s) && grid[row + 1][col - 1] == 0) return true;
            if (row > 0 && col > 0 && row < boardSize - 1 && col < boardSize - 1 &&
                    isValid(grid, row + 1, col - 1, s) && grid[row - 1][col + 1] == 0) return true;
        }
        return false;
    }

    private boolean canFormSOS(int[][] grid, int row, int col) {
        int boardSize = grid.length;
        int s = 1; // S is represented as 1
        int o = 2; // O is represented as 2

        if (getSymbol() == 'S') {
            return (isValid(grid, row - 1, col, o) && isValid(grid, row - 2, col, s)) || // Vertical up
                    (isValid(grid, row + 1, col, o) && isValid(grid, row + 2, col, s)) || // Vertical down
                    (isValid(grid, row, col - 1, o) && isValid(grid, row, col - 2, s)) || // Horizontal left
                    (isValid(grid, row, col + 1, o) && isValid(grid, row, col + 2, s)) || // Horizontal right
                    (isValid(grid, row - 1, col - 1, o) && isValid(grid, row - 2, col - 2, s)) || // Diagonal up-left
                    (isValid(grid, row + 1, col + 1, o) && isValid(grid, row + 2, col + 2, s)) || // Diagonal down-right
                    (isValid(grid, row - 1, col + 1, o) && isValid(grid, row - 2, col + 2, s)) || // Diagonal up-right
                    (isValid(grid, row + 1, col - 1, o) && isValid(grid, row + 2, col - 2, s));   // Diagonal down-left
        } else if (getSymbol() == 'O') {
            return (isValid(grid, row - 1, col, s) && isValid(grid, row + 1, col, s)) || // Vertical
                    (isValid(grid, row, col - 1, s) && isValid(grid, row, col + 1, s)) || // Horizontal
                    (isValid(grid, row - 1, col - 1, s) && isValid(grid, row + 1, col + 1, s)) || // Diagonal \
                    (isValid(grid, row - 1, col + 1, s) && isValid(grid, row + 1, col - 1, s));   // Diagonal /
        }
        return false;
    }

    private boolean isValid(int[][] grid, int row, int col, int expectedValue) {
        int boardSize = grid.length;
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize && grid[row][col] == expectedValue;
    }

    public void makeMove(int[][] grid, int row, int col) {
        int[] bestMove = findBestMove(grid);
        chosenRow = bestMove[0];
        chosenCol = bestMove[1];
        grid[bestMove[0]][bestMove[1]] = (getSymbol() == 'S') ? 1 : 2;
        System.out.println(getName() + " made a move at (" + bestMove[0] + ", " + bestMove[1] + ") with symbol " + getSymbol());
    }
}
