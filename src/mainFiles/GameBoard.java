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

    private final Board board;

    public GameBoard(Board board, int newBoardSize){
        this.board = board;
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
        this.board = null;
        this.board_size = 0;
    }

    public Board getBoard(){
        return board;
    }

    private void setContentPane(){
        gameBoardCanvas = new GameBoardCanvas();
        canvas_width = cell_size * board_size;
        canvas_height = cell_size * board_size;
        gameBoardCanvas.setPreferredSize(new Dimension(canvas_width, canvas_height));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
    }

    class GameBoardCanvas extends JPanel {
        GameBoardCanvas(){
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int rowSelected = e.getY() / cell_size;
                    int colSelected = e.getX() / cell_size;
                    board.makeMove(rowSelected, colSelected);
                    repaint();
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
                    if (board.getCell(row,col) == 1){
                        g2d.setColor(Color.YELLOW);
                        g2d.drawString("S", x1, y1);
                    }else if (board.getCell(row,col) == 2){
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1 - symbol_size, cell_size - 2 *cell_padding, cell_size - 2 * cell_padding);
                    }
                }
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









        /*playerSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player1.isSelected()) {
                    System.out.println("Player 1 is a human player");
                    JOptionPane.showMessageDialog(frame, "Player 1 is a human player");
                }
                if (computer1.isSelected()) {
                    System.out.println("Player 1 is a computer player");
                    JOptionPane.showMessageDialog(frame, "Player 1 is a computer player");
                }
                if (player2.isSelected()) {
                    System.out.println("Player 2 is a human player");
                    JOptionPane.showMessageDialog(frame, "Player 2 is a human player");
                }
                if (computer2.isSelected()) {
                    System.out.println("Player 2 is a computer player");
                    JOptionPane.showMessageDialog(frame, "Player 2 is a human player");
                }
            }
        });*/


        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    int newBoardSize = Integer.parseInt(textField.getText());
                    if (newBoardSize >= 3){
                        frame.dispose();
                        Board board = new Board(newBoardSize);
                        new GameBoard(board, newBoardSize);
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
