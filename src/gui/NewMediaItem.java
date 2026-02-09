package gui;

import api.model.*;
import api.service.MediaItemFileHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class NewMediaItem extends JFrame {

    private JPanel mainPanel;
    private JTextField titleTxt;
    private JTextArea actorsTxt;
    private JCheckBox underageCheckBox;
    private JComboBox categoryCombo;
    private JTextArea descriptionTxt;
    private JRadioButton movieRadioButton;
    private JRadioButton seriesRadioButton;
    private JPanel moviePanel;
    private JTextField movieYearTxt;
    private JTextField movieDurationTxt;
    private JPanel seriesPanel;
    private JButton saveButton;
    private JTree seasonsTree;
    private JTextField seasonNoTxt;
    private JTextField episodeDurationTxt;
    private JButton removeEpisodeBtn;
    private JButton addEpisodeBtn;
    private JButton addSeasonBtn;
    private JList episodesList;
    private JTextField seasonYearTxt;
    private JPanel relevantItemsPanel;
    private JList<MediaItem> allItemsList;
    private JList<MediaItem> relevantItemsList;
    private JButton removeFromRelevantList;
    private JButton addToRelevantList;
    private DefaultListModel<MediaItem> allItemsModel;
    private DefaultListModel<MediaItem> relevantItemsModel;
    private ButtonGroup radiosGroup;
    private DefaultMutableTreeNode seasonsRoot;
    private DefaultListModel<Integer> episodesListModel;
    private DefaultTreeModel treeModel;
    private MediaItem item;

    public NewMediaItem(MediaItem item) {
        this.item = item;

        setContentPane(mainPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);

        descriptionTxt.setLineWrap(true);
        descriptionTxt.setWrapStyleWord(true);
        actorsTxt.setLineWrap(true);
        actorsTxt.setWrapStyleWord(true);

        radiosGroup = new ButtonGroup();
        radiosGroup.add(movieRadioButton);
        radiosGroup.add(seriesRadioButton);

        movieRadioButton.setSelected(true);
        seriesRadioButton.setSelected(false);

        seriesPanel.setVisible(false);

        episodesListModel = new DefaultListModel<>();
        episodesList.setModel(episodesListModel);

        allItemsModel = new DefaultListModel<>();
        allItemsList.setModel(allItemsModel);
        allItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if(item == null) {
            // in the all items list, add all movies (because at first, the movie radio button is selected)
            fillAllItemsList(true);
        }

        relevantItemsModel = new DefaultListModel<>();
        relevantItemsList.setModel(relevantItemsModel);
        relevantItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        seasonsRoot = new DefaultMutableTreeNode("Seasons");
        treeModel = new DefaultTreeModel(seasonsRoot);
        seasonsTree.setModel(treeModel);
        addContextMenuToTree();

        if (this.item != null) {
            initEdit();

        } else {
            setTitle("Add New Media");
        }

        addActionListeners();

        setVisible(true);
    }

    private void initEdit() {
        setTitle("Edit " + item.getTitle());

        if(item instanceof Movie) {
            movieRadioButton.setSelected(true);
            seriesRadioButton.setEnabled(false);
            movieYearTxt.setText(String.valueOf(((Movie) item).getYear()));
            movieDurationTxt.setText(String.valueOf(((Movie) item).getDuration()));
            relevantItemsModel.removeAllElements();
            relevantItemsModel.addAll(((Movie)item).getRelevantMovies());

            fillAllItemsList(true);

            // remove relevant items from the all items list
            for(MediaItem mi : ((Movie)item).getRelevantMovies()) {
                allItemsModel.removeElement(mi);
            }
        } else {
            seriesRadioButton.setSelected(true);
            movieRadioButton.setEnabled(false);

            moviePanel.setVisible(false);
            seriesPanel.setVisible(true);

            Series series = (Series) item;
            relevantItemsModel.removeAllElements();
            relevantItemsModel.addAll(series.getRelevantSeries());

            fillAllItemsList(false);

            // remove relevant items from the all items list
            for(MediaItem mi : series.getRelevantSeries()) {
                allItemsModel.removeElement(mi);
            }

            for(Season season : series.getSeasons()) {
                DefaultMutableTreeNode seasonNode = new DefaultMutableTreeNode(season.getNumber() + ":" + season.getYear());
                for(Episode episode : season.getEpisodes()) {
                    DefaultMutableTreeNode episodeNode = new DefaultMutableTreeNode(episode.getDuration());
                    seasonNode.add(episodeNode);
                }

                seasonsRoot.add(seasonNode);
            }
        }

        // from the all items list remove the current item
        allItemsModel.removeElement(item);

        titleTxt.setText(item.getTitle());
        descriptionTxt.setText(item.getDescription());
        actorsTxt.setText(item.getActors());
        categoryCombo.setSelectedItem(item.getCategory().getDisplayName());
        underageCheckBox.setSelected(item.isSuitableForUnderage());

    }

    private void addActionListeners(){

        // when movie radio button is selected the series panel must be invisible and vice versa
        movieRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Movie radio button selected, show movie panel
                    moviePanel.setVisible(true);
                    seriesPanel.setVisible(false);

                    // in the all items list add all movies
                    fillAllItemsList(true);
                    relevantItemsModel.removeAllElements();
                }
            }
        });

        seriesRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Series radio button selected, show series panel
                    moviePanel.setVisible(false);
                    seriesPanel.setVisible(true);

                    // in the relevant items list add all series
                    fillAllItemsList(false);
                    relevantItemsModel.removeAllElements();
                }
            }
        });

        addEpisodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String durationStr = episodeDurationTxt.getText();
                if(!durationStr.isBlank()) {
                    try {
                        int duration = Integer.parseInt(durationStr);
                        episodesListModel.addElement(duration);
                    } catch(NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(NewMediaItem.this, "Duration must be an integer number.");
                    }
                }
            }
        });

        removeEpisodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if an episode is selected
                if(episodesList.getSelectedIndex() != -1) {
                    // remove it from the list
                    episodesListModel.removeElementAt(episodesList.getSelectedIndex());
                }
            }
        });

        addSeasonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String seasonNoStr = seasonNoTxt.getText();
                String seasonYearStr = seasonYearTxt.getText();
                try {
                    int seasonNo = Integer.parseInt(seasonNoStr);
                    int seasonYear = Integer.parseInt(seasonYearStr);

                    DefaultMutableTreeNode seasonNode = new DefaultMutableTreeNode(seasonNo + ":" + seasonYear);

                    for(int i=0; i<episodesListModel.getSize(); i++) {
                        int episodeDuration = episodesListModel.getElementAt(i);
                        DefaultMutableTreeNode episodeNode = new DefaultMutableTreeNode(episodeDuration);
                        seasonNode.add(episodeNode);
                    }

                    seasonsRoot.add(seasonNode);
                    treeModel.setRoot(seasonsRoot);
                    seasonsTree.setModel(treeModel);

                } catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(NewMediaItem.this, "All fields must be integer numbers.");
                }

                // clear series form
                seasonNoTxt.setText("");
                seasonYearTxt.setText("");
                episodeDurationTxt.setText("");
                episodesListModel.removeAllElements();
            }
        });

        // add an item from the left jlist (allItems) to the right jlist (relevant items)
        addToRelevantList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!relevantItemsModel.contains(allItemsList.getSelectedValue())) {
                    relevantItemsModel.addElement(allItemsList.getSelectedValue());
                    allItemsModel.remove(allItemsList.getSelectedIndex());
                }
            }
        });

        removeFromRelevantList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allItemsModel.addElement(relevantItemsList.getSelectedValue());
                relevantItemsModel.remove(relevantItemsList.getSelectedIndex());
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleTxt.getText();
                String description = descriptionTxt.getText();
                String actors = actorsTxt.getText();
                Category category = Category.fromString((String) categoryCombo.getSelectedItem());
                boolean suitableForUnderage = underageCheckBox.isSelected();

                if(!isValidField(title) || !isValidField(description) || !isValidField(actors) || !isValidField(category.getDisplayName())) {
                    // Display an error message
                    JOptionPane.showMessageDialog(NewMediaItem.this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(movieRadioButton.isSelected()) {
                    try {
                        int movieYear = Integer.parseInt(movieYearTxt.getText());
                        int movieDuration = Integer.parseInt(movieDurationTxt.getText());

                        // create new movie
                        Movie movie = new Movie(title, description, suitableForUnderage, category, actors, movieYear, movieDuration);

                        // add relevant items
                        for(int i=0; i<relevantItemsModel.getSize(); i++) {
                            movie.addRelevantMovie((Movie) relevantItemsModel.getElementAt(i));
                        }

                        // save movie to file
                        if(item == null) {
                            MediaItemFileHandler.addItem(movie);
                            if(MediaItemFileHandler.saveItemsToFile()) {
                                JOptionPane.showMessageDialog(NewMediaItem.this, "Movie saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(NewMediaItem.this, "Error saving the movie", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        // update movie
                        else {
                            if(MediaItemFileHandler.updateAndSave(movie, item.getTitle())) {
                                JOptionPane.showMessageDialog(NewMediaItem.this, "Movie updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(NewMediaItem.this, "Error updating the movie", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    } catch(NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(NewMediaItem.this, "Please fill in all required fields or make sure that numeric fields are filled correctly", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                else if(seriesRadioButton.isSelected()) {
                    // create new series
                    Series series = new Series(title, description, suitableForUnderage, category, actors);

                    // add  relevant items
                    for(int i=0; i<relevantItemsModel.getSize(); i++) {
                        series.addRelevantSeries((Series) relevantItemsModel.getElementAt(i));
                    }

                    // if there are seasons
                    if(seasonsRoot.getChildCount() > 0) {
                        Enumeration<TreeNode> seasons = seasonsRoot.children();

                        while(seasons.hasMoreElements()) {
                            TreeNode seasonNode = seasons.nextElement();
                            String[] seasonInfo = seasonNode.toString().split(":");
                            Season season = new Season(Integer.parseInt(seasonInfo[0]),Integer.parseInt(seasonInfo[1]));

                            if(seasonNode.getChildCount() > 0) {
                                Enumeration<? extends TreeNode> episodes = seasonNode.children();
                                while(episodes.hasMoreElements()) {
                                    TreeNode episodeNode = episodes.nextElement();
                                    Episode episode = new Episode(Integer.parseInt(episodeNode.toString()));

                                    season.addEpisode(episode);
                                }
                            }

                            series.addSeason(season);
                        }
                    }

                    // save series to file
                    if(item == null) {
                        MediaItemFileHandler.addItem(series);
                        if(MediaItemFileHandler.saveItemsToFile()) {
                            JOptionPane.showMessageDialog(NewMediaItem.this, "Series saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(NewMediaItem.this, "Error saving the series", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    // update series
                    else {
                        if(MediaItemFileHandler.updateAndSave(series, item.getTitle())) {
                            JOptionPane.showMessageDialog(NewMediaItem.this, "Series updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(NewMediaItem.this, "Error updating the series", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        });
    }

    private void addContextMenuToTree() {
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");

        deleteItem.addActionListener(e -> {
            // Perform action when the context menu item is clicked
            TreePath path = seasonsTree.getSelectionPath();
            if (path != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();

                if (parent != null) {
                    // Remove the selected node from its parent
                    DefaultTreeModel model = (DefaultTreeModel) seasonsTree.getModel();
                    model.removeNodeFromParent(selectedNode);
                }
            }
        });

        contextMenu.add(deleteItem);

        seasonsTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = seasonsTree.getClosestRowForLocation(e.getX(), e.getY());
                    seasonsTree.setSelectionRow(row);
                    contextMenu.show(seasonsTree, e.getX(), e.getY());
                }
            }
        });
    }

    private boolean isValidField(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private void fillAllItemsList(boolean movies) {
        List<MediaItem> items = MediaItemFileHandler.getAllItems();

        if(movies) {
            items = items.stream().filter(m -> m instanceof Movie).collect(Collectors.toList());
        } else {
            items = items.stream().filter(m -> m instanceof Series).collect(Collectors.toList());
        }

        allItemsModel.removeAllElements();
        allItemsModel.addAll(items);
    }

}
