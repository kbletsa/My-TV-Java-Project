package api.service;

import api.model.MediaItem;
import api.model.Review;
import api.model.Subscriber;
import api.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserFileHandler} class provides methods for managing user-related data, including user addition, retrieval,
 * file I/O operations, and updating user favorites with reviews.
 *
 * <p>This class is responsible for handling user-related data, such as adding users, reading and saving user data to a file,
 * managing user favorites, and updating user favorites with reviews.</p>
 *
 * @see User
 * @see Subscriber
 * @see MediaItem
 * @see Review
 * @see Utils
 */
public class UserFileHandler {

    /** The filename for storing user data. */
    public static String USERS_FILENAME = "users.dat";

    /** The list containing all user objects. */
    public static List<User> allUsers = new ArrayList<>();

    /**
     * Adds a user to the list of users and saves the updated list to the user data file.
     *
     * @param user the user to be added
     * @throws IOException if an I/O error occurs while saving the user data to the file
     */
    public static void addUserAndSave(User user) throws IOException {
        addUser(user);
        saveUsersToFile();
    }

    /**
     * Adds a user to the list of users.
     *
     * @param user the user to be added
     */
    public static void addUser(User user) {
        allUsers.add(user);
    }

    /**
     * Retrieves the list of all users, reading from the user data file.
     *
     * @return the list of all users
     */
    public static List<User> getAllUsers() {
        readUsersFromFile();
        return allUsers;
    }

    /**
     * Reads user data from the file and updates the list of all users.
     */
    public static void readUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILENAME))) {
            allUsers =  (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the list of users to the user data file.
     *
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public static boolean saveUsersToFile() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(USERS_FILENAME));
            oos.writeObject(allUsers);
            System.out.println("Users have been written to file users.dat");
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the specified media item from the favorites of all subscribers and saves the updated user data to the file.
     *
     * @param item the media item to be removed from favorites
     */
    public static void deleteFromFavoritesAndSave(MediaItem item) {
        for(User u : allUsers) {
            if(u instanceof Subscriber) {
                Subscriber s = (Subscriber) u;
                s.removeFavorite(item);
            }
        }

        saveUsersToFile();
    }

    /**
     * Removes the specified media item from the favorites of the user with the given username and saves the updated user data to the file.
     *
     * @param username the username of the user
     * @param item the media item to be removed from favorites
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public static boolean removeFromUserFavorites(String username, MediaItem item) {
        for(User u : allUsers) {
            if(u.getUsername().equals(username) && u instanceof Subscriber) {
                ((Subscriber) u).removeFavorite(item);
                break;
            }
        }
        return saveUsersToFile();
    }

    /**
     * Updates the favorites of the user with the given username by adding the specified media item, and saves the updated user data to the file.
     *
     * @param username the username of the user
     * @param item the media item to be added to favorites
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public static boolean updateUserFavorites(String username, MediaItem item) {
        for(User u : allUsers) {
            if(u.getUsername().equals(username) && u instanceof Subscriber) {
                // if the item is not already in the favorites list of the user
                if(!((Subscriber) u).getFavorites().contains(item)){
                    ((Subscriber) u).addFavorite(item);
                }
                break;
            }
        }
        return saveUsersToFile();
    }

    /**
     * Updates the favorites of the user with the given username by adding or deleting the specified review for the specified media item,
     * and saves the updated user data to the file.
     *
     * @param username the username of the user
     * @param item the media item associated with the review
     * @param review the review to be added or deleted
     * @param delete {@code true} if the review should be deleted, {@code false} if it should be added
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public static boolean updateFavoritesWithReview(String username, MediaItem item, Review review, boolean delete) {
        for(User u : allUsers) {
            if(u.getUsername().equals(username) && u instanceof Subscriber) {
                Utils.updateWithReview(((Subscriber)u).getFavorites(), item, review, delete);
            }
        }

        return saveUsersToFile();
    }
}
