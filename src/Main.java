// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        // Text Box for displaying numbers
        JTextField textField = new JTextField();
        textField.setEditable(false); // Prevent direct typing (optional)
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // Add buttons for numbers and operations
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        String[] buttons = {"7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "+", "="};

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textField.setText(textField.getText() + buttonText); // Append button text to the text field
                }
            });
            buttonPanel.add(button);
        }

        // Adding components
        window.add(buttonPanel, BorderLayout.CENTER);
        window.add(textField, BorderLayout.NORTH);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}