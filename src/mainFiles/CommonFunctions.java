package mainFiles;

public abstract class CommonFunctions {

    protected final int[][] grid;
    protected final int board_Size;
    protected final char player1choice;
    protected final char player2choice;
    protected boolean isPlayer1Turn = true;

    public CommonFunctions(int board_Size, char player1choice, char player2choice) {
        this.board_Size = board_Size;
        this.player1choice = player1choice;
        this.player2choice = player2choice;
        grid = new int[this.board_Size][this.board_Size];
    }

    public int getCell(int row, int column) {
        if (row >= 0 && row < this.board_Size && column >= 0 && column < this.board_Size)
            return grid[row][column];
        else
            return -1;
    }

    public char getTurn() {
        return isPlayer1Turn ? player1choice : player2choice;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < this.board_Size && column >= 0 && column < this.board_Size
                && grid[row][column] == 0) {
            grid[row][column] = (getTurn() == 'S')? 1 : 2;
            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    public abstract boolean checkWin();

    public abstract boolean checkSOS();

    protected abstract boolean checkSOS(int row, int col);

    public abstract boolean checkTie();
}