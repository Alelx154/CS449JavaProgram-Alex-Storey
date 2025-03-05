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
    private int board_size;

    private int canvas_width;
    private int canvas_height;

    private GameBoardCanvas gameBoardCanvas;

    private Board board;

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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.setVisible(true);
        frame.setTitle("SOS Game");
        frame.setLayout(new FlowLayout());

        JTextField textField = new JTextField(15);
        JButton submitButton = new JButton("Submit");
        JLabel resultLabel = new JLabel("Enter board size");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    int newBoardSize = Integer.parseInt(textField.getText());
                    if (newBoardSize > 0){
                        frame.dispose();
                        Board board = new Board();
                        new GameBoard(board, newBoardSize);
                    }else{
                        resultLabel.setText("Please enter a positive number.");
                    }
                }catch (NumberFormatException ex){
                    resultLabel.setText("Please enter a valid number.");
                }
            }
        });

        frame.add(resultLabel);
        frame.add(textField);
        frame.add(submitButton);
        frame.pack();
        frame.setVisible(true);
    }

}
