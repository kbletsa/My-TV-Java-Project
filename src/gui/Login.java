package gui;

import api.Main;
import api.model.Admin;
import api.model.Subscriber;
import api.model.User;
import api.service.MediaItemFileHandler;
import api.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel mainPanel;
    private JTextField usernameTxt;
    private JButton loginBtn;
    private JButton registerBtn;
    private JPasswordField passwordTxt;

    public Login() {
        setContentPane(mainPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close the current frame
                dispose();
                // open a new Register frame
                new Register();
            }
        });


        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTxt.getText();
                String password = passwordTxt.getText();

                User loggedUser = UserService.loginUser(username, password);

                // if username and password are correct
                if(loggedUser != null) {

                    Main.loggedInUser = loggedUser; // set the loggedInUser
                    dispose(); // dispose current window and open new window according to the user type

                    // if user is an admin
                    if(loggedUser instanceof Admin) {
                        new AdminMainFrame();
                    }
                    // if user is a subscriber
                    else {
                        new SubscriberMainFrame();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(Login.this, "Login failed. Try again.");
                }
            }
        });
    }

}
