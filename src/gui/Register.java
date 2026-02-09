package gui;

import api.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame{
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField nameTextField;
    private JTextField usernameTextField;
    private JTextField surnameTextField;
    private JPasswordField passwordTextField;

    public Register() {
        setContentPane(mainPanel);
        setTitle("Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close current frame
                dispose();

                // open a login frame
                new Login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();

                if(name.trim().isEmpty() || surname.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(Register.this, "All fields are required.");
                }
                else {
                    boolean registered = UserService.registerUser(name, surname, username, password);
                    if(registered) {
                        JOptionPane.showMessageDialog(Register.this, "Registered Successfully.");
                    }
                    else {
                        JOptionPane.showMessageDialog(Register.this, "Registration failed. Try again");
                    }

                    // clear fields
                    nameTextField.setText("");
                    surnameTextField.setText("");
                    usernameTextField.setText("");
                    passwordTextField.setText("");
                }
            }
        });
    }
}
