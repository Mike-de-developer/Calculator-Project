// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("My Calculator");
        window.setResizable(false);
        window.setSize(500, 500);

        // Components
        // JPanel for calculator buttons
        JPanel calcButtons = new JPanel();
        calcButtons.setLayout(new GridLayout(4,4));

        // Text Box for displaying numbers
        JTextArea textArea = new JTextArea(2, 20); // 2 rows, 20 columns
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setEditable(false); // Prevent direct typing
        textArea.setLineWrap(true); // Wrap lines if text exceeds the width
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setBounds(0, 0, 500, 100);
        textArea.setFont(new Font("Arial", Font.PLAIN, 24));
        textArea.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);

        // Add buttons for numbers and operations
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        String[] buttons = {"Cls", "BSpc", "%", "!",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "+", "="};

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.setBackground(Color.BLACK);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (buttonText.contains("BSpc")){
                        String bSpaced = textArea.getText().split("\n")[0];
                        String newText = bSpaced.substring(0, bSpaced.length() - 1);
                        textArea.setText(newText);
                    }
                    else if (buttonText.contains("Cls")){
                        textArea.setText("");
                    }
                    else if (buttonText.contains("=")){
                        String currentText = textArea.getText();

                        // Evaluate the expression
                        try {
                            double result = evaluateExpression(currentText.split("\n")[0]); // Get the first line (expression)
                            textArea.setText(currentText + "\n= " + result);
                        } catch (Exception ex) {
                            textArea.setText("Error");
                        }
                    }
                    else {
                        textArea.setText(textArea.getText().split("\n")[0] + buttonText); // Append button text to the text field
                    }
                }

            });
            buttonPanel.add(button);
        }

        // Adding components
        window.add(buttonPanel, BorderLayout.CENTER);
        window.add(textArea, BorderLayout.NORTH);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    // Method to evaluate the mathematical expression
    private static double evaluateExpression(String expression) {
        // Tokenize the input
        List<String> tokens = tokenize(expression);

        // Convert infix to postfix
        List<String> postfix = infixToPostfix(tokens);

        // Evaluate the postfix expression
        return evaluatePostfix(postfix);
    }

    // Tokenize the input expression
    // Credit to Deep Seek AI
    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                number.append(c); // Build multi-digit numbers or decimals
            } else {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0); // Reset the number builder
                }
                if (!Character.isWhitespace(c)) {
                    tokens.add(String.valueOf(c)); // Add operators or parentheses
                }
            }
        }

        // Add the last number if it exists
        if (number.length() > 0) {
            tokens.add(number.toString());
        }

        return tokens;
    }

    // Convert infix to postfix using the Shunting Yard Algorithm
    private static List<String> infixToPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);

        for (String token : tokens) {
            if (token.matches("\\d+(\\.\\d+)?")) { // If it's a number
                output.add(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop(); // Remove the "("
            } else { // It's an operator
                while (!operators.isEmpty() && precedence.get(operators.peek()) >= precedence.get(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }

        // Add remaining operators
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    // Evaluate the postfix expression
    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (token.matches("\\d+(\\.\\d+)?")) { // If it's a number
                stack.push(Double.parseDouble(token));
            } else { // It's an operator
                double num2 = stack.pop();
                double num1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        if (num2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        stack.push(num1 / num2);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + token);
                }
            }
        }

        return stack.pop();
    }
}