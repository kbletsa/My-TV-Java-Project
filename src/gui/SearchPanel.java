package gui;

import api.Main;
import api.model.Admin;
import api.model.Category;
import api.model.MediaItem;
import api.model.Subscriber;
import api.service.MediaItemFileHandler;
import api.service.SearchService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SearchPanel extends JPanel {
    private JPanel mainPanel;
    private JPanel searchPane;
    private JRadioButton movieRadioButton;
    private JRadioButton seriesRadioButton;
    private JTextField titleTextField;
    private JCheckBox suitableForUnderageCheckBox;
    private JTextField actorsTextField;
    private JComboBox<Category> categoryComboBox;
    private JButton searchButton;
    private JList<MediaItem> resultsList;
    private JComboBox<Integer> ratingComboBox;
    private JRadioButton allRadioButton;
    private ButtonGroup radiosGroup;

    private DefaultListModel<MediaItem> itemsListModel;
    private List<MediaItem> resultsItems;

    public SearchPanel() {

        radiosGroup = new ButtonGroup();
        radiosGroup.add(movieRadioButton);
        radiosGroup.add(seriesRadioButton);
        radiosGroup.add(allRadioButton);

        itemsListModel = new DefaultListModel<>();
        resultsList.setModel(itemsListModel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchService searchService = new SearchService();

                // set media item type
                if(movieRadioButton.isSelected()) {
                    searchService.setType("MOVIE");
                } else if(seriesRadioButton.isSelected()) {
                    searchService.setType("SERIES");
                }

                searchService.setTitle(titleTextField.getText());
                searchService.setActors(actorsTextField.getText());
                searchService.setSuitableForUnderage(suitableForUnderageCheckBox.isSelected());
                searchService.setCategory(Category.fromString((String) categoryComboBox.getSelectedItem()));
                searchService.setMinRating(Integer.parseInt((String) ratingComboBox.getSelectedItem()));

                resultsItems = searchService.search();
                itemsListModel.removeAllElements();
                itemsListModel.addAll(resultsItems);
                resultsList.setModel(itemsListModel);
            }
        });

        resultsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(e.getX(), e.getY());
                }
            }
        });
    }

    private void showPopupMenu(int x, int y) {
        // Create a context menu (pop-up menu)
        JPopupMenu popupMenu = new JPopupMenu();

        // Add menu items to the context menu according to user type
        if(Main.loggedInUser instanceof Subscriber) {
            JMenuItem addReviewItem = new JMenuItem("Review");

            // add listener to item
            addReviewItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReviewFrame(resultsList.getSelectedValue());
                }
            });

            // add the item to the menu
            popupMenu.add(addReviewItem);

        } else if(Main.loggedInUser instanceof Admin) {
            JMenuItem editItem = new JMenuItem("Edit");
            JMenuItem deleteItem = new JMenuItem("Delete");

            editItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // open new NewMediaItem window with the selected item
                    new NewMediaItem(resultsList.getSelectedValue());
                }
            });

            deleteItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showOptionDialog(
                            SearchPanel.this,
                            "Are you sure you want to delete the selected item?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, // Use default icon
                            new Object[]{"Yes", "No"}, // Button options
                            "No" // Default button
                    );

                    if(result == JOptionPane.YES_OPTION) {
                        // delete item
                        resultsList.remove(resultsList.getSelectedIndex());
                        MediaItemFileHandler.deleteAndSave(resultsList.getSelectedValue());
                        // also remove it from the results list

                    }
                }
            });

            popupMenu.add(editItem);
            popupMenu.add(deleteItem);
        }

        JMenuItem showItem = new JMenuItem("Show");
        showItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowItemFrame(resultsList.getSelectedValue());
            }
        });

        // Add menu items to the context menu
        popupMenu.add(showItem);

        // Show the context menu at the specified location
        popupMenu.show(resultsList, x, y);
    }
}
