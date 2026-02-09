package gui;

import api.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainFrame extends JFrame {

    private JPanel mainPanel;
    private JButton newButton;
    private SearchPanel searchPanel;
    private JButton logOutButton;


    public AdminMainFrame() {

        setContentPane(mainPanel);

        setTitle("Welcome " + Main.loggedInUser.getUsername());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);


        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open new NewMediaItem Frame
                new NewMediaItem(null);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main.loggedInUser = null;
                new Login();
            }
        });


    }
}
