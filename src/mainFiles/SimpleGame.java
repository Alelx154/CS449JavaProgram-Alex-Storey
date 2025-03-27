package mainFiles;

public abstract class SimpleGame extends CommonFunctions {

    public SimpleGame(int boardSize, char player1Choice, char player2Choice) {
        super(boardSize, player1Choice, player2Choice);
    }


    public boolean checkWin() {
        // Check for SOS pattern in rows, columns, and diagonals
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (checkSOS(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkTie() {
        // Check if all cells are filled
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (grid[row][col] == 0) {
                    return false; // Found an empty cell
                }
            }
        }
        // No empty cells found, check if no player has won
        return !checkWin();
    }


    protected boolean checkSOS(int row, int col) {
        //Debug statements
        System.out.println("Checking SOS");
        System.out.println(checkDirection(row, col, 0, 1));
        System.out.println(checkDirection(row, col, 1, 0));
        System.out.println(checkDirection(row, col, 1, 1));
        System.out.println(checkDirection(row, col, 1, -1));

        return (checkDirection(row, col, 0, 1) ||
                checkDirection(row, col, 1, 0) ||
                checkDirection(row, col, 1, 1) ||
                checkDirection(row, col, 1, -1));
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir) {
        try {
            return (grid[row][col] == 1 && grid[row + rowDir][col + colDir] == 2 && grid[row + 2 * rowDir][col + 2 * colDir] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
