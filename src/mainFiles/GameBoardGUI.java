package mainFiles;

//Split up stuff into folders
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;

public class GameBoardGUI extends JFrame {
    private final int board_size;
    private final Game someGame;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private List<String> gameMoves;

    public static final int cell_size = 100;
    public static final int cell_padding = cell_size/6;
    public static final int symbol_stroke_width = 8;
    public static final int symbol_size = cell_size - cell_padding * 2;

    private JRadioButton player1RadioButton, player2RadioButton, computer1RadioButton, computer2RadioButton;
    private JRadioButton simple_game,general_game;

    private int rowSelected;
    private int colSelected;

    public GameBoardGUI(Game game, int newBoardSize) {
        this.someGame = game;
        this.board_size = newBoardSize;
        this.gameMoves = new ArrayList<>();
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("SOS Game");
        setVisible(true);
        setLocationRelativeTo(null);
        HandleComputerTurn();
    }

    public GameBoardGUI(){
        this.someGame = null;
        this.board_size = 0;
    }

    private void setContentPane() {
        GameBoardCanvas gameBoardCanvas = new GameBoardCanvas();
        gameBoardCanvas.setPreferredSize(new Dimension(board_size * 100, board_size * 100));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);

        JPanel mainControlPanel = new JPanel(new BorderLayout());

        // Score Panel at the top
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 2));
        player1ScoreLabel = new JLabel(someGame.player1.getName() + " Score: 0");
        player2ScoreLabel = new JLabel(someGame.player2.getName() + " Score: 0");
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);
        mainControlPanel.add(scorePanel, BorderLayout.NORTH);

        // Player 1 Symbol Panel on the left
        JPanel player1SymbolPanel = new JPanel();
        player1SymbolPanel.setLayout(new GridLayout(2, 1));
        JLabel player1Label = new JLabel(someGame.player1.getName());
        JPanel player1ButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton player1SButton = new JButton("S");
        JButton player1OButton = new JButton("O");
        player1ButtonPanel.add(player1SButton);
        player1ButtonPanel.add(player1OButton);
        player1SymbolPanel.add(player1Label);
        player1SymbolPanel.add(player1ButtonPanel);
        mainControlPanel.add(player1SymbolPanel, BorderLayout.WEST);

        // Player 2 Symbol Panel on the right
        JPanel player2SymbolPanel = new JPanel();
        player2SymbolPanel.setLayout(new GridLayout(2, 1));
        JLabel player2Label = new JLabel(someGame.player2.getName());
        JPanel player2ButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton player2SButton = new JButton("S");
        JButton player2OButton = new JButton("O");
        player2ButtonPanel.add(player2SButton);
        player2ButtonPanel.add(player2OButton);
        player2SymbolPanel.add(player2Label);
        player2SymbolPanel.add(player2ButtonPanel);
        mainControlPanel.add(player2SymbolPanel, BorderLayout.EAST);

        contentPane.add(mainControlPanel, BorderLayout.NORTH);

        // Add action listeners for player 1 buttons
        player1SButton.addActionListener(e -> {
            if (someGame.getTurn() == someGame.player1) {
                someGame.player1.setSymbol('S');
                updateButtonStates();
            }
        });

        player1OButton.addActionListener(e -> {
            if (someGame.getTurn() == someGame.player1) {
                someGame.player1.setSymbol('O');
                updateButtonStates();
            }
        });

        // Add action listeners for player 2 buttons
        player2SButton.addActionListener(e -> {
            if (someGame.getTurn() == someGame.player2) {
                someGame.player2.setSymbol('S');
                updateButtonStates();
            }
        });

        player2OButton.addActionListener(e -> {
            if (someGame.getTurn() == someGame.player2) {
                someGame.player2.setSymbol('O');
                updateButtonStates();
            }
        });

        updateButtonStates();
    }

    private void updateButtonStates() {
        boolean isPlayer1Turn = someGame.getTurn() == someGame.player1;
        boolean isPlayer2Turn = someGame.getTurn() == someGame.player2;

        Component[] components = getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                Component[] panelComponents = ((JPanel) comp).getComponents();
                for (Component panelComp : panelComponents) {
                    if (panelComp instanceof JButton) {
                        JButton button = (JButton) panelComp;
                        button.setEnabled((isPlayer1Turn && button.getText().startsWith("Player 1")) ||
                                        (isPlayer2Turn && button.getText().startsWith("Player 2")));
                    }
                }
            }
        }
    }

    private void updateScores() {
        player1ScoreLabel.setText(someGame.player1.getName() + " Score: " + someGame.player1.score);
        player2ScoreLabel.setText(someGame.player2.getName() + " Score: " + someGame.player2.score);
    }

    class GameBoardCanvas extends JPanel {
        GameBoardCanvas() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (someGame.getTurn() instanceof HumanPlayer) {
                        rowSelected = e.getY() / cell_size;
                        colSelected = e.getX() / cell_size;
                        if (someGame.getCell(rowSelected, colSelected) == 0) {
                            char symbol = someGame.getTurn().getSymbol();
                            String player = someGame.getTurn().getName();
                            someGame.makeMove(rowSelected, colSelected);
                            addMove(rowSelected, colSelected, symbol, player);
                            repaint();
                            updateScores();
                            checkForWinGUI();
                            HandleComputerTurn();
                        }
                    }
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
            for (int row = 1; row < board_size; row++) {
                g.fillRect(0, row * 100 - 4, board_size * 100, 8);
            }
            for (int col = 1; col < board_size; col++) {
                g.fillRect(col * 100 - 4, 0, 8, board_size * 100);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(symbol_stroke_width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, symbol_size));
            
            // Calculate the center offset for text
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth("S");
            int textHeight = fm.getAscent();
            
            for (int row = 0; row < board_size; row++) {
                for (int col = 0; col < board_size; col++) {
                    int cellValue = someGame.getCell(row, col);
                    if (cellValue == 1) {
                        g2d.setColor(Color.YELLOW);
                        int x = col * cell_size + (cell_size - textWidth) / 2;
                        int y = row * cell_size + (cell_size + textHeight) / 2;
                        g2d.drawString("S", x, y);
                    } else if (cellValue == 2) { // O
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(col * cell_size + cell_padding, 
                                    row * cell_size + cell_padding, 
                                    symbol_size, symbol_size);
                    }
                }
            }
        }
    }

    private void HandleComputerTurn() {
        Player currentPlayer = someGame.getTurn();
        if (currentPlayer instanceof ComputerPlayer) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
                        int[] bestMove = computerPlayer.findBestMove(someGame.getGrid());
                        int row = bestMove[0];
                        int col = bestMove[1];
                        // Record the symbol before making the move
                        char symbol = computerPlayer.getSymbol();
                        String playerName = computerPlayer.getName();
                        
                        someGame.makeMove(row, col);
                        addMove(row, col, symbol, playerName);
                        repaint();
                        updateScores();
                        checkForWinGUI();

                        if (someGame.getTurn() == currentPlayer) {
                            HandleComputerTurn();
                        } else if (someGame.getTurn() instanceof ComputerPlayer) {
                            HandleComputerTurn();
                        }
                    });
                }
            }, 300);
        }
    }

    //Update functions
    //Use for checking for win and printing out to the GUI
    private void checkForWinGUI(){
        if (someGame instanceof SimpleGame && someGame.checkWin()) {
            String winner = someGame.getTurn().getName();
            System.out.println("Winner detected: " + winner);
            int option = JOptionPane.showConfirmDialog(this, 
                winner + " wins!\nWould you like to save this game as a replay?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                saveReplay();
            }
            
            option = JOptionPane.showConfirmDialog(this, 
                "Would you like to play again?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                this.dispose();
                new GameBoardGUI().SubmitButtonLogic();
            } else {
                System.exit(0);
            }
        }
        else if (someGame instanceof SimpleGame && someGame.checkTie()){
            int option = JOptionPane.showConfirmDialog(this, 
                "Game is a tie!\nWould you like to save this game as a replay?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                saveReplay();
            }
            
            option = JOptionPane.showConfirmDialog(this, 
                "Would you like to play again?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                this.dispose();
                new GameBoardGUI().SubmitButtonLogic();
            } else {
                System.exit(0);
            }
        }
        else if (someGame instanceof GeneralGame && ((GeneralGame) someGame).IsBoardFull()){
            int player1Score = someGame.player1.score;
            int player2Score = someGame.player2.score;
            System.out.println("Player 1 score: " + player1Score + " Player 2 score: " + player2Score);

            String message;
            if (someGame.player1.score > someGame.player2.score){
                message = "Player 1 wins with " + player1Score + " points!\nWould you like to save this game as a replay?";
            }
            else if (someGame.player2.score > someGame.player1.score){
                message = "Player 2 wins with " + player2Score + " points!\nWould you like to save this game as a replay?";
            }
            else {
                message = "Game is a tie with " + player1Score + " points each!\nWould you like to save this game as a replay?";
            }

            int option = JOptionPane.showConfirmDialog(this, 
                message, 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                saveReplay();
            }
            
            option = JOptionPane.showConfirmDialog(this, 
                "Would you like to play again?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                this.dispose();
                new GameBoardGUI().SubmitButtonLogic();
            } else {
                System.exit(0);
            }
        }
    }

    private void saveReplay() {
        String replayName = JOptionPane.showInputDialog(this, "Enter a name for this replay:");
        if (replayName != null && !replayName.trim().isEmpty()) {
            String gameType = (someGame instanceof SimpleGame) ? "Simple" : "General";
            String winnerInfo;
            
            if (someGame instanceof SimpleGame) {
                // For Simple game, the current player is the winner (they made the SOS)
                winnerInfo = someGame.getTurn().getName() + " won by making an SOS!";
            } else {
                // For General game, compare scores
                int player1Score = someGame.player1.score;
                int player2Score = someGame.player2.score;
                if (player1Score > player2Score) {
                    winnerInfo = someGame.player1.getName() + " won with " + player1Score + " points! (vs " + player2Score + " points)";
                } else if (player2Score > player1Score) {
                    winnerInfo = someGame.player2.getName() + " won with " + player2Score + " points! (vs " + player1Score + " points)";
                } else {
                    winnerInfo = "Game ended in a tie with " + player1Score + " points each!";
                }
            }
            
            ReplayManager.saveReplay(replayName, board_size, gameMoves, gameType, winnerInfo);
        }
    }

    private void addMove(int row, int col, char symbol, String player) {
        gameMoves.add(row + "," + col + "," + symbol + "," + player);
    }

    private void SetUp(JFrame frame, JPanel mainPanel, GridBagConstraints gbc, JTextField textField, JLabel resultLabel, JButton submitButton) {
        JLabel SimpleORGeneral = new JLabel("Simple or General?");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(SimpleORGeneral, gbc);

        resultLabel.setText("<-- Enter board size");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(resultLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        mainPanel.add(submitButton, gbc);

        JButton viewReplaysButton = new JButton("View Replays");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        mainPanel.add(viewReplaysButton, gbc);

        viewReplaysButton.addActionListener(e -> {
            List<String> replays = ReplayManager.getAvailableReplays();
            if (replays.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No replays available.");
                return;
            }

            String[] replayArray = replays.toArray(new String[0]);
            String selectedReplay = (String) JOptionPane.showInputDialog(
                frame,
                "Select a replay to view:",
                "View Replays",
                JOptionPane.PLAIN_MESSAGE,
                null,
                replayArray,
                replayArray[0]);

            if (selectedReplay != null) {
                new GameReplay(selectedReplay);
            }
        });
    }

    private void PlayerChoices(JPanel mainPanel, GridBagConstraints gbc, ButtonGroup group1, ButtonGroup group2, ButtonGroup GeneralOrSimple) {
        JLabel playerLabel = new JLabel("Player 1:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        mainPanel.add(playerLabel, gbc);

        player1RadioButton = new JRadioButton("Human");
        computer1RadioButton = new JRadioButton("Computer");
        group1.add(player1RadioButton);
        group1.add(computer1RadioButton);

        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(player1RadioButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(computer1RadioButton, gbc);

        JLabel player2Label = new JLabel("Player 2:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(player2Label, gbc);

        player2RadioButton = new JRadioButton("Human");
        computer2RadioButton = new JRadioButton("Computer");
        group2.add(player2RadioButton);
        group2.add(computer2RadioButton);

        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(player2RadioButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(computer2RadioButton, gbc);

        general_game = new JRadioButton("General Game");
        simple_game = new JRadioButton("Simple Game");
        GeneralOrSimple.add(general_game);
        GeneralOrSimple.add(simple_game);
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(general_game, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        mainPanel.add(simple_game, gbc);
    }

    public void SubmitButtonLogic() {
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

        JTextField textField = new JTextField(15);
        JLabel resultLabel = new JLabel();
        JButton submitButton = new JButton("Submit");

        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();
        ButtonGroup GeneralOrSimple = new ButtonGroup();

        SetUp(frame, mainPanel, gbc, textField, resultLabel, submitButton);
        PlayerChoices(mainPanel, gbc, group1, group2, GeneralOrSimple);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int newBoardSize = Integer.parseInt(textField.getText());
                    if (newBoardSize >= 3) {
                        Player player1 = player1RadioButton.isSelected()
                                ? new HumanPlayer("Player 1")
                                : new ComputerPlayer("Computer 1");

                        Player player2 = player2RadioButton.isSelected()
                                ? new HumanPlayer("Player 2")
                                : new ComputerPlayer("Computer 2");

                        Game gameInstance;
                        if (simple_game.isSelected()) {
                            gameInstance = new SimpleGame(newBoardSize, player1, player2);
                        } else {
                            gameInstance = new GeneralGame(newBoardSize, player1, player2);
                        }
                        frame.dispose();
                        new GameBoardGUI(gameInstance, newBoardSize);
                    } else {
                        resultLabel.setText("Please enter a number larger than 2.");
                    }
                } catch (NumberFormatException ex) {
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
        SwingUtilities.invokeLater(() -> {
            GameBoardGUI gameBoardGUI = new GameBoardGUI();
            gameBoardGUI.SubmitButtonLogic();
        });
    }
}
