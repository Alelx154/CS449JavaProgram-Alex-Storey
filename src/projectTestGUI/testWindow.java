package projectTestGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testWindow {
    private JPanel PannelMain;
    private JButton buttonmsg;
    private JCheckBox checkBox1;
    private JRadioButton TestButtom;
    private JTable table1;
    private JTextField SOSGAMETextField;

    public testWindow() {
        buttonmsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PannelMain, "Hello World");
            }
        });
        checkBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
            }
        });
        createUIComponents();

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("testWindow");
        JTable table = new JTable();
        frame.setContentPane(new testWindow().PannelMain);
        table.setModel(new DefaultTableModel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        table1.setModel(new DefaultTableModel(
                null,
                new String[]{"Sometjing", "Sometjing", "Sometjing", "Sometjing"}
        ));
    }
}