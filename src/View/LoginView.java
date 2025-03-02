package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private final JLabel usernameLabel = new JLabel("Username:",SwingConstants.CENTER);
    private final JLabel passwordLabel = new JLabel("Password:",SwingConstants.CENTER);
    private final JLabel messageLabel = new JLabel("If you don't have an account it will be automatically created.",SwingConstants.CENTER);
    private final JTextField usernameField = new JTextField();
    private final JTextField passwordField = new JTextField();

    private final JButton enterButton = new JButton("Enter");

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2,5,10));
        setSize(700,200);
        setResizable(false);
        setLocationRelativeTo(null);
        add(usernameLabel);
        add(usernameField );
        add(passwordLabel);
        add(passwordField);
        add(messageLabel);
        add(enterButton);
        setVisible(true);
    }

    public void wrongPasswordMessage() { messageLabel.setText("Incorrect Password"); }

    public void close() { setVisible(false); dispose(); }

    public void addEnterButtonActionListener(ActionListener actionListener) {
        enterButton.addActionListener(actionListener);
    }

    public String getUserName(){
        return usernameField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }
}
