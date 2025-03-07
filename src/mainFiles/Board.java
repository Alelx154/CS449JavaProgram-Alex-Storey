package mainFiles;

public class Board {

    private final int[][] grid;
    private char turn = 'S';
    private final int board_Size;

    public Board(int board_Size) {
        this.board_Size = board_Size;
        grid = new int[this.board_Size][this.board_Size];
    }

    public int getCell(int row, int column) {
        if (row >= 0 && row < this.board_Size && column >= 0 && column < this.board_Size)
            return grid[row][column];
        else
            return -1;
    }

    public char getTurn() {
        return turn;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < this.board_Size && column >= 0 && column < this.board_Size
                && grid[row][column] == 0) {
            grid[row][column] = (turn == 'S')? 1 : 2;
            turn = (turn == 'S')? 'O' : 'S';
        }
    }

}
