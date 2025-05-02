package mainFiles;

import java.util.Random;

public class ComputerPlayer extends Player {
    private final Random random = new Random();
    public int chosenRow;
    public int chosenCol;
    private char lastChosenSymbol;  // Store the last chosen symbol

    public ComputerPlayer(String name) {
        super(name);
    }

    @Override
    public void makeMove(int[][] grid, int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == 0) {
            grid[row][col] = (getSymbol() == 'S') ? 1 : 2;
            System.out.println(getName() + " made a move at (" + row + ", " + col + ") with symbol " + getSymbol());
        } else {
            throw new IllegalArgumentException("Invalid move. Cell is already occupied or out of bounds.");
        }
    }

    public int[] findBestMove(int[][] grid) {
        int boardSize = grid.length;
        int[] bestMove = null;
        int bestScore = -1;
        char bestSymbol = 'S';

        // First, check if we can make an SOS
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (grid[row][col] == 0) {
                    // Try S
                    setSymbol('S');
                    grid[row][col] = 1;
                    if (canFormSOS(grid, row, col)) {
                        grid[row][col] = 0;
                        lastChosenSymbol = 'S';
                        return new int[]{row, col};
                    }
                    grid[row][col] = 0;

                    // Try O
                    setSymbol('O');
                    grid[row][col] = 2;
                    if (canFormSOS(grid, row, col)) {
                        grid[row][col] = 0;
                        lastChosenSymbol = 'O';
                        return new int[]{row, col};
                    }
                    grid[row][col] = 0;
                }
            }
        }

        // If no SOS is possible, find the best move
        for (char symbol : new char[]{'S', 'O'}) {
            setSymbol(symbol);
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (grid[row][col] == 0) {
                        grid[row][col] = (symbol == 'S') ? 1 : 2;
                        int score = evaluateMove(grid, row, col);
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

        lastChosenSymbol = bestSymbol;
        setSymbol(bestSymbol);
        return bestMove;
    }

    @Override
    public char getSymbol() {
        // If we've just found a best move, use that symbol
        if (lastChosenSymbol != 0) {
            char symbol = lastChosenSymbol;
            lastChosenSymbol = 0;  // Reset for next move
            return symbol;
        }
        return super.getSymbol();
    }

    private int evaluateMove(int[][] grid, int row, int col) {
        int score = 0;
        if (canFormSOS(grid, row, col)) {
            score += 100;
        }
        if (canCreateSOSOpportunity(grid, row, col)) {
            score += 50;
        }
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
            // Check if placing S creates a potential SOS pattern
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
            // Check if placing O creates a potential SOS pattern
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
}
