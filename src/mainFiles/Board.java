package mainFiles;

public class Board {

    private int[][] grid;
    private char turn = 'X';

    public Board() {
        grid = new int[10][10];
    }

    public int getCell(int row, int column) {
        if (row >= 0 && row < 10 && column >= 0 && column < 10)
            return grid[row][column];
        else
            return -1;
    }

    public char getTurn() {
        return turn;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < 10 && column >= 0 && column < 10
                && grid[row][column] == 0) {
            grid[row][column] = (turn == 'S')? 1 : 2;
            turn = (turn == 'S')? 'O' : 'S';
        }
    }

}
