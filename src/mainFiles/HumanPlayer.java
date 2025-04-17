package mainFiles;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void makeMove(int[][] grid, int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == 0) {
            grid[row][col] = (symbol == 'S') ? 1 : 2;
            System.out.println("Human player " + name + " made a move at (" + row + ", " + col + ")");
        } else {
            throw new IllegalArgumentException("Invalid move. Cell is already occupied or out of bounds.");
        }
    }
}
