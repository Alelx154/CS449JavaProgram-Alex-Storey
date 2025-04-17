package mainFiles;

public abstract class Game {

    public final int[][] grid;
    public final int board_Size;
    protected final Player player1;
    protected final Player player2;
    protected Player currentPlayer;

    public Game(int board_Size, Player player1, Player player2) {
        this.board_Size = board_Size;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        grid = new int[this.board_Size][this.board_Size];
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getCell(int row, int column) {
        if (row >= 0 && row < this.board_Size && column >= 0 && column < this.board_Size)
            return grid[row][column];
        else
            return -1;
    }

    public Player getTurn() {
        return currentPlayer;
    }

    public void makeMove(int row, int col) {
        if (row >= 0 && row < this.board_Size && col >= 0 && col < this.board_Size && grid[row][col] == 0) {
            currentPlayer.makeMove(grid, row, col);
            if (!checkSOS(row, col)){
                switchTurn();
            }
        }
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Player " + currentPlayer.getName() + "'s turn.");
    }

    public abstract boolean checkWin();

    public abstract boolean checkTie();

    public boolean checkSOS(int row, int col) {
        if (grid[row][col] == 1) {
            return (checkSOSForS_upDirection(row, col) ||
                    checkSOSForS_downDirection(row, col) ||
                    checkSOSForS_rightDirection(row, col) ||
                    checkSOSForS_leftDirection(row, col) ||
                    checkSOSForS_DiagonalUpRight(row, col) ||
                    checkSOSForS_DiagonalDownRight(row, col) ||
                    checkSOSForS_DiagonalUpLeft(row, col) ||
                    checkSOSForS_DiagonalDownLeft(row, col));
        } else if (grid[row][col] == 2) {
            return (checkSOSForO_DiagonalToLeft(row, col) ||
                    checkSOSForO_DiagonalToRight(row, col) ||
                    checkSOSForO_DiagonalToLeft(row, col) ||
                    checkSOSForO_DiagonalToRight(row, col) ||
                    checkSOSForO_Horizontal(row, col) ||
                    checkSOSForO_Vertical(row, col));
        }
        return false;
    }

    private boolean checkSOSForS_upDirection(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row - 1][col] == 2 && grid[row - 2][col] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_downDirection(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row + 1][col] == 2 && grid[row + 2][col] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_leftDirection(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row][col - 1] == 2 && grid[row][col - 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_rightDirection(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row][col + 1] == 2 && grid[row][col + 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalUpRight(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row + 1][col + 1] == 2 && grid[row + 2][col + 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalUpLeft(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row + 1][col - 1] == 2 && grid[row + 2][col - 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalDownRight(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row - 1][col + 1] == 2 && grid[row - 2][col + 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalDownLeft(int row, int col) {
        try {
            return (grid[row][col] == 1 && grid[row - 1][col - 1] == 2 && grid[row - 2][col - 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_Vertical(int row, int col) {
        try {
            return (grid[row][col] == 2 && grid[row - 1][col] == 1 && grid[row + 1][col] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_Horizontal(int row, int col) {
        try {
            return (grid[row][col] == 2 && grid[row][col - 1] == 1 && grid[row][col + 1] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_DiagonalToRight(int row, int col) {
        try {
            return (grid[row][col] == 2 && grid[row - 1][col - 1] == 1 && grid[row + 1][col + 1] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_DiagonalToLeft(int row, int col) {
        try {
            return (grid[row][col] == 2 && grid[row - 1][col + 1] == 1 && grid[row + 1][col - 1] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}