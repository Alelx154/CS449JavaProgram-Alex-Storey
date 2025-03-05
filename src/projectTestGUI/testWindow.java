package projectTestGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testWindow {
    private static int rows = 10;
    private static int columns = 10;
    private final javax.swing.ButtonGroup ButtonGroup;

    private JPanel PanelMain;
    private JButton buttonmsg;
    private JCheckBox checkBox1;
    private JRadioButton TestButton;
    private JTable gameBoard;
    private JTextField SOSGAMETextField;
    private ButtonGroup RadioButtons;

    public testWindow() {

        PanelMain = new JPanel();
        buttonmsg = new JButton("Click me!");
        checkBox1 = new JCheckBox("Sample CheckBox");
        TestButton = new JRadioButton("Test button");
        gameBoard = new JTable(new DefaultTableModel(rows, columns));
        ButtonGroup = new ButtonGroup();
        SOSGAMETextField = new JTextField("SOS Game");

        JPanel buttonPanel = new JPanel();


        PanelMain.setLayout(new BorderLayout());
        PanelMain.add(buttonmsg, BorderLayout.NORTH);
        PanelMain.add(new JScrollPane(gameBoard), BorderLayout.CENTER);
        PanelMain.add(checkBox1, BorderLayout.SOUTH);

        buttonmsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PanelMain, "Hello World");
            }
        });
        checkBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
            }
        });
        TestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("testWindow");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testWindow window = new testWindow();
            frame.setContentPane(window.PanelMain);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}