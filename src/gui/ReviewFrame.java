package gui;

import api.Main;
import api.model.MediaItem;
import api.model.Review;
import api.model.Subscriber;
import api.service.MediaItemFileHandler;
import api.service.UserFileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewFrame extends JFrame {
    private JComboBox<Integer> ratingCombo;
    private JTextArea commentsTextArea;
    private JButton saveButton;
    private JPanel mainPanel;
    private JButton deleteReviewButton;
    private MediaItem item;
    private Subscriber subscriber;

    // true if the review already exists
    private boolean update;
    private Review existingReview;

    public ReviewFrame(MediaItem item) {
        this.item = item;
        this.subscriber = (Subscriber) Main.loggedInUser;

        commentsTextArea.setLineWrap(true);
        commentsTextArea.setWrapStyleWord(true);

        update = false;

        // if the current user has already reviewed the selected item
        // fill the fields with the review's data
        for(Review r : item.getReviews()) {
            if(r.getSubscriber().equals(subscriber)) {
                commentsTextArea.setText(r.getText());
                ratingCombo.setSelectedIndex(r.getRating() - 1);
                saveButton.setText("Update Review");
                deleteReviewButton.setEnabled(true);
                update = true;
                existingReview = r;
            }
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rating = ratingCombo.getSelectedIndex() + 1;
                String comments = commentsTextArea.getText();
                Review review = new Review(comments, rating, subscriber);
                if(MediaItemFileHandler.updateWithReview(item, review, false) && UserFileHandler.updateFavoritesWithReview(Main.loggedInUser.getUsername(), item, review, false)) {
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Review saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Error updating review", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(existingReview != null && MediaItemFileHandler.updateWithReview(item, existingReview, true) && UserFileHandler.updateFavoritesWithReview(Main.loggedInUser.getUsername(), item, existingReview, true)) {
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Review deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Error deleting review", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setTitle(item.getTitle());
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        setVisible(true);
    }
}
