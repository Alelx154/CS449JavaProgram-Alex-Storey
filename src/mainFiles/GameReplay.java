package mainFiles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameReplay extends JFrame {
    private int board_size;
    private int[][] grid;
    private int currentMoveIndex = 0;
    private List<String> moves;
    private Timer replayTimer;
    private JButton playButton;
    private JButton pauseButton;
    private JButton nextButton;
    private JButton prevButton;
    private boolean isPlaying = false;
    private String gameType;
    private String winnerInfo;

    public static final int cell_size = 100;
    public static final int cell_padding = cell_size/6;
    public static final int symbol_stroke_width = 8;
    public static final int symbol_size = cell_size - cell_padding * 2;

    public GameReplay(String replayName) {
        moves = ReplayManager.loadReplay(replayName);
        if (moves.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No moves found in replay file.");
            dispose();
            return;
        }


        String boardSizeLine = moves.get(0);
        board_size = Integer.parseInt(boardSizeLine.split(":")[1]);
        moves.remove(0);

        String gameTypeLine = moves.get(0);
        gameType = gameTypeLine.split(":")[1];
        moves.remove(0);


        String winnerLine = moves.get(moves.size() - 1);
        winnerInfo = winnerLine.split(":")[1];
        moves.remove(moves.size() - 1);

        grid = new int[board_size][board_size];
        setContentPane();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setTitle("SOS Game Replay - " + replayName);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setContentPane() {
        ReplayBoardCanvas gameBoardCanvas = new ReplayBoardCanvas();
        gameBoardCanvas.setPreferredSize(new Dimension(board_size * cell_size, board_size * cell_size));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");

        buttonPanel.add(prevButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(nextButton);
        

        JLabel gameTypeLabel = new JLabel(gameType + " Game", SwingConstants.CENTER);
        controlPanel.add(gameTypeLabel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPane.add(controlPanel, BorderLayout.SOUTH);

        playButton.addActionListener(e -> startReplay());
        pauseButton.addActionListener(e -> pauseReplay());
        nextButton.addActionListener(e -> nextMove());
        prevButton.addActionListener(e -> previousMove());

        pauseButton.setEnabled(false);
    }

    private void startReplay() {
        if (!isPlaying) {
            isPlaying = true;
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);

            replayTimer = new Timer();
            replayTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (currentMoveIndex < moves.size()) {
                        SwingUtilities.invokeLater(() -> {
                            nextMove();
                            if (currentMoveIndex >= moves.size()) {
                                pauseReplay();
                                showWinnerDialog();
                            }
                        });
                    } else {
                        pauseReplay();
                        showWinnerDialog();
                    }
                }
            }, 0, 1000); // Play one move per second
        }
    }

    private void showWinnerDialog() {
        JOptionPane.showMessageDialog(this, winnerInfo, "Game Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void pauseReplay() {
        if (isPlaying) {
            isPlaying = false;
            playButton.setEnabled(true);
            pauseButton.setEnabled(false);
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
            if (replayTimer != null) {
                replayTimer.cancel();
                replayTimer = null;
            }
        }
    }

    private void nextMove() {
        if (currentMoveIndex < moves.size()) {
            String move = moves.get(currentMoveIndex);
            String[] parts = move.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            char symbol = parts[2].charAt(0);
            String player = parts[3];

            grid[row][col] = (symbol == 'S') ? 1 : 2;
            currentMoveIndex++;
            repaint();
            
            if (currentMoveIndex >= moves.size() && !isPlaying) {
                showWinnerDialog();
            }
        }
    }

    private void previousMove() {
        if (currentMoveIndex > 0) {
            currentMoveIndex--;
            String move = moves.get(currentMoveIndex);
            String[] parts = move.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            
            grid[row][col] = 0;
            repaint();
        }
    }

    class ReplayBoardCanvas extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
        }

        private void drawGridLines(Graphics g) {
            g.setColor(Color.BLACK);
            for (int row = 1; row < board_size; row++) {
                g.fillRect(0, row * cell_size - 4, board_size * cell_size, 8);
            }
            for (int col = 1; col < board_size; col++) {
                g.fillRect(col * cell_size - 4, 0, 8, board_size * cell_size);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(symbol_stroke_width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, symbol_size));
            
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth("S");
            int textHeight = fm.getAscent();
            
            for (int row = 0; row < board_size; row++) {
                for (int col = 0; col < board_size; col++) {
                    int cellValue = grid[row][col];
                    if (cellValue == 1) {
                        g2d.setColor(Color.YELLOW);
                        int x = col * cell_size + (cell_size - textWidth) / 2;
                        int y = row * cell_size + (cell_size + textHeight) / 2;
                        g2d.drawString("S", x, y);
                    } else if (cellValue == 2) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(col * cell_size + cell_padding, 
                                    row * cell_size + cell_padding, 
                                    symbol_size, symbol_size);
                    }
                }
            }
        }
    }
} 