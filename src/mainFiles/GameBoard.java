package mainFiles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GameBoard extends JFrame {
    public static final int cell_size = 100;
    public static final int grid_width = 8;
    public static final int grid_width_half = grid_width / 2;

    public static final int cell_padding = cell_size/6;
    public static final int symbol_size = cell_size - cell_padding * 2;
    public static final int symbol_stroke_width = 8;
    private final int  board_size;

    private int canvas_width;
    private int canvas_height;

    private GameBoardCanvas gameBoardCanvas;

    private final CommonFunctions game;

    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;

    public GameBoard(CommonFunctions game, int newBoardSize){
        this.game = game;
        this.board_size = newBoardSize;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("SOS Game");
        setVisible(true);
        setLocationRelativeTo(null);
        add(gameBoardCanvas);
        setVisible(true);
    }

    public GameBoard(){
        this.game = null;
        this.board_size = 0;
    }

    public CommonFunctions getGame(){
        return game;
    }

    private void setContentPane(){
        gameBoardCanvas = new GameBoardCanvas();
        canvas_width = cell_size * board_size;
        canvas_height = cell_size * board_size;
        gameBoardCanvas.setPreferredSize(new Dimension(canvas_width, canvas_height));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 2));
        player1ScoreLabel = new JLabel("Player 1 Score: 0");
        player2ScoreLabel = new JLabel("Player 2 Score: 0");
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);
        contentPane.add(scorePanel, BorderLayout.NORTH);
    }

    private void updateScores(){
        player1ScoreLabel.setText("Player 1 Score: " + GeneralGame.getPlayer1Score());
        player2ScoreLabel.setText("Player 2 Score: " + GeneralGame.getPlayer2Score());
    }

    class GameBoardCanvas extends JPanel {
        GameBoardCanvas(){
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int rowSelected = e.getY() / cell_size;
                    int colSelected = e.getX() / cell_size;
                    game.makeMove(rowSelected, colSelected);
                    repaint();
                    updateScores();
                    checkForWin();
                }
            });
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
        }

        private void drawGridLines(Graphics g) {
            g.setColor(Color.BLACK);
            for (int row = 1; row < board_size; row++){
                g.fillRoundRect(0, cell_size * row - grid_width_half, canvas_width - 1, grid_width, grid_width, grid_width);
            }
            for (int col = 1; col < board_size; col++){
                g.fillRoundRect(cell_size * col - grid_width_half, 0, grid_width, canvas_height - 1, grid_width, grid_width);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(symbol_stroke_width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, symbol_size));
            for (int row = 0; row < board_size; row++){
                for (int col = 0; col < board_size; col++){
                    int x1 = col * cell_size + cell_padding;
                    int y1 = row * cell_size + cell_padding + symbol_size;
                    if (game.getCell(row,col) == 1){
                        g2d.setColor(Color.YELLOW);
                        g2d.drawString("S", x1, y1);
                    }else if (game.getCell(row,col) == 2){
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1 - symbol_size, cell_size - 2 *cell_padding, cell_size - 2 * cell_padding);
                    }
                }
            }
        }
    }

    private void checkForWin(){
        if (game instanceof SimpleGame && game.checkWin()) {
            String winner = game.isPlayer1Turn ? "Player 2" : "Player 1";
            JOptionPane.showMessageDialog(this, winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        else if (game instanceof SimpleGame && game.checkTie()){
            JOptionPane.showMessageDialog(this, "Game is a tie! Game over");
            System.exit(0);
        }
        if (game instanceof GeneralGame){
            int totalCells = game.board_Size * game.board_Size;
            int filledCells = 0;

            for (int row = 0; row < game.board_Size; row++){
                for (int col = 0; col < game.board_Size; col++){
                    if (game.getCell(row, col) != 0){
                        filledCells++;
                    }
                }
            }

            if (filledCells == totalCells){
                String message;
                if (GeneralGame.getPlayer1Score() > GeneralGame.getPlayer2Score()){
                    message = "Player 1 wins!";
                }
                else if (GeneralGame.getPlayer2Score() > GeneralGame.getPlayer1Score()){
                    message = "Player 2 wins!";
                }
                else{
                    message = "It's a tie!";
                }
                JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    public void submit_and_player_or_computer_selection(){
        JFrame frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel SimpleORGeneral = new JLabel("Simple or General?");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(SimpleORGeneral, gbc);

        JLabel resultLabel = new JLabel("<-- Enter board size");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(resultLabel, gbc);

        JTextField textField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(textField, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        mainPanel.add(submitButton, gbc);

        JLabel playerLabel = new JLabel("Player 1:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        mainPanel.add(playerLabel, gbc);

        JRadioButton player1 = new JRadioButton("Human");
        JRadioButton computer1 = new JRadioButton("Computer");
        JRadioButton Player1_is_S = new JRadioButton("S");
        JRadioButton Player1_is_O = new JRadioButton("O");

        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group1_A = new ButtonGroup();

        group1.add(player1);
        group1.add(computer1);
        group1_A.add(Player1_is_S);
        group1_A.add(Player1_is_O);

        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(player1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(computer1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(Player1_is_S, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(Player1_is_O, gbc);

        JLabel player2Label = new JLabel("Player 2:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(player2Label, gbc);

        JRadioButton player2 = new JRadioButton("Human");
        JRadioButton computer2 = new JRadioButton("Computer");
        JRadioButton Player2_is_S = new JRadioButton("S");
        JRadioButton Player2_is_O = new JRadioButton("O");
        ButtonGroup group2 = new ButtonGroup();
        ButtonGroup group2_A = new ButtonGroup();
        group2.add(player2);
        group2.add(computer2);
        group2_A.add(Player2_is_S);
        group2_A.add(Player2_is_O);
        gbc.gridx = 1;
        gbc.gridy = 9;
        mainPanel.add(player2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 10;
        mainPanel.add(computer2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 11;
        mainPanel.add(Player2_is_S, gbc);
        gbc.gridx = 1;
        gbc.gridy = 12;
        mainPanel.add(Player2_is_O, gbc);

        JRadioButton general_game = new JRadioButton("General Game");
        JRadioButton simple_game = new JRadioButton("Simple Game");
        ButtonGroup GeneralOrSimple = new ButtonGroup();
        GeneralOrSimple.add(general_game);
        GeneralOrSimple.add(simple_game);
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(general_game, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        mainPanel.add(simple_game, gbc);


        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    int newBoardSize = Integer.parseInt(textField.getText());
                    if (newBoardSize >= 3){
                        char player1Choice = Player1_is_S.isSelected() ? 'S' : 'O';
                        char player2Choice = Player2_is_S.isSelected() ? 'S' : 'O';
                        frame.dispose();
                        CommonFunctions game;
                        if (player1Choice == player2Choice){
                            player1Choice = 'S';
                            player2Choice = 'O';
                        }
                        if (simple_game.isSelected()) {
                            game = new SimpleGame(newBoardSize, player1Choice, player2Choice) {
                                @Override
                                public boolean checkSOS() {
                                    return false;
                                }
                            };
                        } else {
                            game = new GeneralGame(newBoardSize, player1Choice, player2Choice) {
                                @Override
                                public boolean checkSOS() {
                                    return false;
                                }

                                @Override
                                public boolean checkTie() {
                                    return false;
                                }
                            };
                        }
                        new GameBoard(game, newBoardSize);
                    }else{
                        resultLabel.setText("Please enter a number larger than 2.");
                    }
                }catch (NumberFormatException ex){
                    resultLabel.setText("Please enter a valid number.");
                }
            }
        });

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                GameBoard gameBoard = new GameBoard();
                gameBoard.submit_and_player_or_computer_selection();
            }
        });
    }
}
