package gui;

import api.Main;
import api.model.MediaItem;
import api.model.Subscriber;
import api.service.MediaItemFileHandler;
import api.service.UserFileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubscriberMainFrame extends JFrame {

    private JPanel mainPanel;
    private JToolBar toolBar;
    private JButton logOutButton;
    private JList<MediaItem> favoritesList;
    private JButton refreshButton;

    private DefaultListModel<MediaItem> favoritesListModel;
    private DefaultListModel<MediaItem> resultsListModel;
    private Subscriber user;

    // user parameter : the logged-in user
    public SubscriberMainFrame() {
        this.user = (Subscriber) Main.loggedInUser;

        setTitle("Welcome " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        favoritesListModel = new DefaultListModel<>();
        favoritesListModel.addAll(user.getFavorites());
        favoritesList.setModel(favoritesListModel);

        favoritesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {

                    showPopupMenu(e.getX(), e.getY());
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favoritesListModel.removeAllElements();
                favoritesListModel.addAll(user.getFavorites());
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

        setContentPane(mainPanel);
        setVisible(true);
    }


                private void showPopupMenu(int x, int y) {
                    // Create a context menu (pop-up menu)
                    JPopupMenu popupMenu = new JPopupMenu();

                    // Add menu items to the context menu
                    JMenuItem addReviewItem = new JMenuItem("Review");
                    JMenuItem showItem = new JMenuItem("Show");
                    JMenuItem deleteItem = new JMenuItem("Remove");

                    // Add action listeners to the menu items
                    addReviewItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new ReviewFrame(favoritesList.getSelectedValue());
                        }
                    });

                    showItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new ShowItemFrame(favoritesList.getSelectedValue());
                        }
                    });

                    deleteItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int result = JOptionPane.showOptionDialog(
                                    SubscriberMainFrame.this,
                                    "Are you sure you want to remove the selected item from your favorites?",
                                    "Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null, // Use default icon
                                    new Object[]{"Yes", "No"}, // Button options
                                    "Yes" // Default button
                );

                if(result == JOptionPane.YES_OPTION) {
                    // delete item from file
                    UserFileHandler.removeFromUserFavorites(Main.loggedInUser.getUsername(), favoritesList.getSelectedValue());
                }
            }
        });

        // Add menu items to the context menu
        popupMenu.add(addReviewItem);
        popupMenu.add(showItem);
        popupMenu.add(deleteItem);

        // Show the context menu at the specified location
        popupMenu.show(favoritesList, x, y);
    }

    public void updateFavorites() {

    }
}
