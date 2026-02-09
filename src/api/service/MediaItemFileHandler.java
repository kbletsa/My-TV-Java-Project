package api.service;

import api.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MediaItemFileHandler} class provides methods for handling the serialization and deserialization
 * of media items to and from a file. It also manages the collection of all media items in the system.
 *
 * @see MediaItem
 * @see Review
 */
public class MediaItemFileHandler {

    /** The filename for storing serialized media items. */
    public static String ITEMS_FILENAME = "items.dat";

    /** The collection of all media items in the system. */
    public static List<MediaItem> allItems = new ArrayList<>();

    /**
     * Adds a new media item to the collection.
     *
     * @param item the media item to add
     */
    public static void addItem(MediaItem item) {
        allItems.add(item);
    }

    /**
     * Gets the list of all media items in the system.
     *
     * @return the list of all media items
     */
    public static List<MediaItem> getAllItems() {
        return allItems;
    }

    /**
     * Reads media items from the file and populates the collection.
     *
     * @return the list of media items read from the file
     */
    public static List<MediaItem> readItemsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ITEMS_FILENAME))) {
            allItems =  (ArrayList<MediaItem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allItems;
    }

    /**
     * Saves the current collection of media items to the file.
     *
     * @return {@code true} if the save operation is successful, {@code false} otherwise
     */
    public static boolean saveItemsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ITEMS_FILENAME))) {
            oos.writeObject(allItems);
            System.out.println("Items have been written to file " + ITEMS_FILENAME);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a media item from the collection and saves the updated collection to the file.
     *
     * @param item the media item to delete
     */
    public static void deleteAndSave(MediaItem item) {
        List<MediaItem> newItems = new ArrayList<>(allItems);
        allItems.remove(item);

        for(MediaItem mi : allItems) {

            // remove the item from relevants list
            mi.removeRelevant(item);
        }

        // method call to also delete the item from users' favorites
        UserFileHandler.deleteFromFavoritesAndSave(item);
        // save new items list
        saveItemsToFile();
    }

    /**
     * Updates a media item in the collection and saves the updated collection to the file.
     *
     * @param item      the updated media item
     * @param oldTitle  the previous title of the media item
     * @return {@code true} if the update and save operation is successful, {@code false} otherwise
     */
    public static boolean updateAndSave(MediaItem item, String oldTitle) {

        int index = -1;
        for(MediaItem mi : allItems) {
            if(mi.getTitle().equals(oldTitle)) {
                index = allItems.indexOf(mi);
                break;
            }
        }

        allItems.remove(index);
        allItems.add(item);
        return saveItemsToFile();
    }

    /**
     * Updates a media item in the collection with a review and saves the updated collection to the file.
     *
     * @param item   the media item to update
     * @param review the review to add or delete
     * @param delete {@code true} if the review should be deleted, {@code false} to update the review
     * @return {@code true} if the update and save operation is successful, {@code false} otherwise
     */
    public static boolean updateWithReview(MediaItem item, Review review, boolean delete) {

        Utils.updateWithReview(allItems, item, review, delete);
        return saveItemsToFile();
    }
}
