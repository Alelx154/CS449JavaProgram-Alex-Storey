package mainFiles;

public abstract class GeneralGame extends CommonFunctions {
    static int player1Score = 0;
    private static int player2Score = 0;
    private char currentPlayerCharacter;
    private int lastRow = -1;
    private int lastCol = -1;


    public GeneralGame(int boardSize, char player1choice, char player2choice) {
        super(boardSize, player1choice, player2choice);
        this.currentPlayerCharacter = player1choice;

    }

    //Check win method checks if any new SOS patterns have been made in the current game
    //and updates the score of the player who made the SOS pattern. If the board is full
    //the game will declare a winner based on the scores of the players

    public boolean checkWin() {
        //Variables used in the method
        boolean foundNewSOS = false;
        int totalCells = board_Size * board_Size;
        int filledCells = 0;

        // Count filled cells and adds to the filledCells variable
        for (int row = 0; row < board_Size; row++) {
            for (int col = 0; col < board_Size; col++) {
                if (grid[row][col] != 0) {
                    filledCells++;
                }
            }
        }

        // Check for new SOS patterns only around the last move
        if (checkSOS(lastRow, lastCol)) {
            foundNewSOS = true;
            if (isPlayer1Turn) {
                player1Score++;
            } else {
                player2Score++;
            }
        }

        // If board is full, print final scores in the console interface
        // This was more for debugging purposes. May remove later
        if (filledCells == totalCells) {
            System.out.println("Game Over!");
            System.out.println("Final Scores:");
            System.out.println("Player 1: " + player1Score);
            System.out.println("Player 2: " + player2Score);

            if (player1Score > player2Score) {
                System.out.println("Player 1 wins!");
            } else if (player2Score > player1Score) {
                System.out.println("Player 2 wins!");
            } else {
                System.out.println("It's a tie!");
            }
        }
        //Return a foundNewSOS
        return foundNewSOS;
    }

    //Method to return the current players turn
    public char getTurn(){
        return currentPlayerCharacter;
    }

    //Used to make a move in the game along with tracking the position of the last move and if the move
    //made an SOS pattern or not. If an SOS pattern was made, the score of the player who made it is
    //increased using the check win method

    public void makeMove(int row, int column) {
        System.out.println("Making move at (" + row + ", " + column + ") by player " + (isPlayer1Turn ? "1" : "2") + " with character " + currentPlayerCharacter);
        if (row >= 0 && row < board_Size && column >= 0 && column < board_Size && grid[row][column] == 0) {
            lastRow = row;
            lastCol = column;
            grid[row][column] = (getTurn() == 'S') ? 1 : 2;

            // Check if this move created an SOS
            boolean madeSOSNow = checkWin();
            //Debug statement
            System.out.println("Made SOS: " + madeSOSNow);

            // Switch turns only if no SOS was made
            if (!madeSOSNow) {
                isPlayer1Turn = !isPlayer1Turn;
                currentPlayerCharacter = isPlayer1Turn ? player1choice : player2choice;
            }
            //Also a debug statement
            System.out.println("Next turn: Player " + (isPlayer1Turn ? "1" : "2") + " with character " + currentPlayerCharacter);
        }
    }

    //Was having trouble with the first implementation of checkSOS, so I
    //decided to implement a new one that checks for SOS patterns in all directions
    //and diagonals. This made it easier to debug and implement in the
    //CheckSOS method

    private boolean checkSOSForS_upDirection(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row - 1][col] == 2 && grid[row - 2][col] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_downDirection(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row + 1][col] == 2 && grid[row + 2][col] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_leftDirection(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row][col - 1] == 2 && grid[row][col - 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_rightDirection(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row][col + 1] == 2 && grid[row][col + 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalUpRight(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row + 1][col + 1] == 2 && grid[row + 2][col + 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalUpLeft(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row + 1][col - 1] == 2 && grid[row + 2][col - 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalDownRight(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row - 1][col + 1] == 2 && grid[row - 2][col + 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForS_DiagonalDownLeft(int row, int col) {
        try{
            return (grid[row][col] == 1 && grid[row - 1][col - 1] == 2 && grid[row - 2][col - 2] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_Vertical(int row, int col) {
        try{
            return (grid[row][col] == 2 && grid[row - 1][col] == 1 && grid[row + 1][col] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_Horizontal(int row, int col) {
        try{
            return (grid[row][col] == 2 && grid[row][col - 1] == 1 && grid[row][col + 1] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_DiagonalToRight(int row, int col){
        try{
            return (grid[row][col] == 2 && grid[row - 1][col - 1] == 1 && grid[row + 1][col + 1] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean checkSOSForO_DiagonalToLeft(int row, int col){
        try{
            return (grid[row][col] == 2 && grid[row - 1][col + 1] == 1 && grid[row + 1][col - 1] == 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    //S == 1
    //O == 2

    protected boolean checkSOS(int row, int col) {
        //Debug statement
        System.out.println("Checking SOS at (" + row + ", " + col + ")");
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

    public static int getPlayer1Score() {
        return player1Score;
    }

    public static int getPlayer2Score() {
        return player2Score;
    }
}
