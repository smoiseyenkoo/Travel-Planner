package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Frame extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JButton load;
    private static final String loadString = "Load";
    private JInternalFrame mainPanel;
    private JDesktopPane desktop;


    public Frame() {
        desktop = new JDesktopPane();
        mainPanel = new JInternalFrame("Main menu", false, false, false,
                false);
        mainPanel.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("Travel Planner!");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();
        mainPanel.setVisible(true);
        initializeGraphics();
        desktop.add(mainPanel);



    }


    // EFFECTS: creates instance of the Travel Planner app
    public static void main(String[] args) {
        new Frame();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.pink);
        getContentPane().setBackground(Color.PINK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addButtonPanel();
        setVisible(true);
    }

//    private void newButton() {
//        //Create a panel that uses BoxLayout.
//        JPanel buttonPanel = new JPanel();
//        load = new JButton(loadString);
//        load.setFocusable(false);
//        buttonPanel.add(load);
//    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,2));
        load = new JButton(loadString);
        buttonPanel.add(load);
        buttonPanel.add(new JButton("1"));
        mainPanel.getContentPane().add(buttonPanel, BorderLayout.CENTER);
    }
}
