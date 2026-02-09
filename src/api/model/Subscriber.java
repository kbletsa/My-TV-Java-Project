package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Subscriber} class represents a subscriber user in the system.
 * It extends the {@link User} abstract class and introduces additional
 * attributes such as name, surname, and a list of favorite media items.
 *
 * @see User
 */
public class Subscriber extends User implements Serializable {

    /** The name of the subscriber. */
    private String name;

    /** The surname of the subscriber. */
    private String surname;

    /** The list of favorite media items associated with the subscriber. */
    private List<MediaItem> favorites;

    /**
     * Constructs a new subscriber with the specified username, password, name, and surname.
     *
     * @param username the unique username of the subscriber
     * @param password the password associated with the subscriber's account
     * @param name     the name of the subscriber
     * @param surname  the surname of the subscriber
     */
    public Subscriber(String username, String password, String name, String surname) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.favorites = new ArrayList<>();
    }

    /**
     * Adds a media item to the list of favorite items for the subscriber.
     *
     * @param mediaItem the media item to add to the favorites
     */
    public void addFavorite(MediaItem mediaItem) {
        favorites.add(mediaItem);
    }

    /**
     * Removes a media item from the list of favorite items for the subscriber.
     *
     * @param mediaItem the media item to remove from the favorites
     */
    public void removeFavorite(MediaItem mediaItem) {
        favorites.remove(mediaItem);
    }

    /**
     * Gets the name of the subscriber.
     *
     * @return the name of the subscriber
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the surname of the subscriber.
     *
     * @return the surname of the subscriber
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the list of favorite media items associated with the subscriber.
     *
     * @return the list of favorite media items
     */
    public List<MediaItem> getFavorites() {
        return favorites;
    }
}
