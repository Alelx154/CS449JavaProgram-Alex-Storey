package mainFiles;

public abstract class Player {
    protected char symbol; // S or O
    protected String name;
    public int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void setSymbol(char symbol) {
        if (symbol != 'S' && symbol != 'O') {
            throw new IllegalArgumentException("Symbol must be either 'S' or 'O'");
        }
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public abstract void makeMove(int[][] grid, int row, int col);
}
