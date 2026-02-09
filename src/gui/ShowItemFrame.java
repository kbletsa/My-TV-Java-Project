package gui;

import api.Main;
import api.model.*;
import api.service.UserFileHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ShowItemFrame extends JFrame {

    private static final int LABEL_WIDTH = 300;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JPanel mainPanel;
    private JLabel actorsLabel;
    private JLabel categoryLabel;
    private JLabel underageLabel;
    private JPanel moviePanel;
    private JPanel seriesPanel;
    private JLabel yearLabel;
    private JLabel durationLabel;
    private JTree seasonsTree;
    private JList<MediaItem> relevantsList;
    private JLabel ratingLabel;
    private JButton addToFavoritesButton;
    private JList<Review> reviewsList;
    private MediaItem item;

    public ShowItemFrame(MediaItem item) {
        this.item = item;


        titleLabel.setText("<html><div style='width:" + LABEL_WIDTH + "px;'>" + item.getTitle() + "</div></html>");
        descriptionLabel.setText("<html><div style='width:" + LABEL_WIDTH + "px;'>" + item.getDescription() + "</div></html>");
        actorsLabel.setText(item.getActors());
        categoryLabel.setText(item.getCategory().toString());
        if(item.isSuitableForUnderage()) {
            underageLabel.setText("Suitable for underage");
        } else {
            underageLabel.setText("Not suitable for underage");
        }

        if(Double.isNaN(item.getAverageRating())) {
            ratingLabel.setText("Not rated");
        }
        else {
            ratingLabel.setText(String.valueOf(item.getAverageRating()));
        }

        if(Main.loggedInUser instanceof Admin) {
            addToFavoritesButton.setVisible(false);
        }

        DefaultListModel<MediaItem> relevantsListModel = new DefaultListModel<>();

        if(item instanceof Movie) {
            moviePanel.setVisible(true);
            seriesPanel.setVisible(false);

            Movie movie = (Movie) item;
            durationLabel.setText(String.valueOf(movie.getDuration()) + " min");
            yearLabel.setText(String.valueOf(movie.getYear()));

            relevantsListModel.addAll(movie.getRelevantMovies());
        } else {
            moviePanel.setVisible(false);
            seriesPanel.setVisible(true);

            Series series = (Series) item;
            DefaultTreeModel treeModel = new DefaultTreeModel(createSeasonsTree(series));
            seasonsTree.setModel(treeModel);

            relevantsListModel.addAll(series.getRelevantSeries());
        }

        relevantsList.setModel(relevantsListModel);

        DefaultListModel<Review> reviewsListModel = new DefaultListModel<>();
        reviewsListModel.addAll(item.getReviews());
        reviewsList.setModel(reviewsListModel);

        addToFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // update to file
                if(UserFileHandler.updateUserFavorites(Main.loggedInUser.getUsername(), item)) {
                    JOptionPane.showMessageDialog(ShowItemFrame.this, "Item added to favorites", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(ShowItemFrame.this, "Error adding item to favorites", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setContentPane(mainPanel);
        setTitle(item.getTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private DefaultMutableTreeNode createSeasonsTree(Series series) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Seasons");

        for(Season s : series.getSeasons()) {
            DefaultMutableTreeNode seasonNode = new DefaultMutableTreeNode("Season " + s.getNumber() + ", " + s.getYear());
            for(Episode e : s.getEpisodes()) {
                DefaultMutableTreeNode episodeNode = new DefaultMutableTreeNode(e.getDuration() + " min");
                seasonNode.add(episodeNode);
            }
            rootNode.add(seasonNode);
        }

        return rootNode;
    }

}
