// Imports
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("My Calculator");
        window.setSize(500, 500);

        // Components
        // JPanel for calculator buttons
        JPanel calcButtons = new JPanel();
        calcButtons.setLayout(new GridLayout(4,4));

        int numButtons = 16;
        JButton[] buttons = new JButton[numButtons]; // Array to store buttons
        String[] board = new String[numButtons]; // To track the current state of each button ("X", "O", or "")

        // Buttons setup
        for (int i = 0; i < numButtons; i++) {
            JButton button = new JButton();
            button.setBackground(Color.BLACK);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            button.setName("Button_" + i); // Custom name for each button

            // Initialize board state
            board[i] = ""; // Empty initially

            // Add an ActionListener to each button
            final int index = i; // Capture index for event listener
            buttons[i] = button;
            calcButtons.add(button);
        };

        // Add Components to window
        window.add(calcButtons, BorderLayout.CENTER);

        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}