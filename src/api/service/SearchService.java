package api.service;

import api.model.Category;
import api.model.MediaItem;
import api.model.Movie;
import api.model.Series;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code SearchService} class provides functionality for searching media items based on various criteria.
 *
 * <p>This class allows users to perform searches on the collection of media items stored in the system. Searches
 * can be conducted based on the type of media item, suitability for underage viewers, title, actors, category, and
 * minimum rating.</p>
 *
 * @see MediaItem
 * @see Movie
 * @see Series
 * @see Category
 */
public class SearchService {

    /** The type of media item to search for (MOVIE or SERIES). */
    private String type;

    /** Flag indicating whether the media item should be suitable for underage viewers. */
    private boolean suitableForUnderage;

    /** The title to search for in media items. */
    private String title;

    /** The actors to search for in media items. */
    private String actors;

    /** The category to search for in media items. */
    private Category category;

    /** The minimum rating to filter media items. */
    private int minRating;

    /** The list of all media items in the system. */
    private List<MediaItem> allItems;

    /**
     * Constructs a new SearchService instance, initializing the list of media items from the file.
     */
    public SearchService() {
        allItems = MediaItemFileHandler.readItemsFromFile();
    }

    /**
     * Performs a search based on the specified criteria and returns the list of matching media items.
     *
     * @return the list of matching media items
     */
    public List<MediaItem> search() {
        List<MediaItem> results = allItems;

        // check media type
        if(type != null) {
            if(type.equals("MOVIE")) {
                results = results.stream().filter(mi -> mi instanceof Movie).collect(Collectors.toList());
            }
            else if(type.equals("SERIES")) {
                results = results.stream().filter(mi -> mi instanceof Series).collect(Collectors.toList());
            }
        }

        // check underage suitability
        if(suitableForUnderage) {
            results = results.stream().filter(MediaItem::isSuitableForUnderage).collect(Collectors.toList());
        }

        // check title
        if(title != null && !title.isBlank()) {
            results = results.stream().filter(mi -> mi.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
        }

        // check actors
        if(actors!= null && !actors.isBlank()) {
            results = results.stream().filter(mi -> mi.getActors().toLowerCase().contains(actors.toLowerCase())).collect(Collectors.toList());
        }

        // check category
        if(category!= null && !category.equals(Category.ALL)) {
            results = results.stream().filter(mi -> mi.getCategory().equals(category)).collect(Collectors.toList());
        }

        // check minimum rating
        if(minRating != 0) {
            results = results.stream().filter(mi -> mi.getAverageRating() >= minRating).collect(Collectors.toList());
        }

        return results;
    }

    // Setters for search criteria

    /**
     * Sets the type of media item to search for.
     *
     * @param type the type of media item (MOVIE or SERIES)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets whether the media item should be suitable for underage viewers.
     *
     * @param suitableForUnderage {@code true} if the media item should be suitable for underage viewers, {@code false} otherwise
     */
    public void setSuitableForUnderage(boolean suitableForUnderage) {
        this.suitableForUnderage = suitableForUnderage;
    }

    /**
     * Sets the title to search for in media items.
     *
     * @param title the title to search for
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the actors to search for in media items.
     *
     * @param actors the actors to search for
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     * Sets the category to search for in media items.
     *
     * @param category the category to search for
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Sets the minimum rating to filter media items.
     *
     * @param minRating the minimum rating to filter media items
     */
    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }
}
