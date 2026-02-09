package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code MediaItem} class is an abstract base class representing a generic
 * media item in the system, such as a movie or series.
 *
 * <p>This class defines common attributes shared by all media items, including title,
 * description, suitability for underage audiences, category, actors, and a list of reviews.</p>
 *
 * <p>Concrete subclasses, such as {@link Movie} and {@link Series}, should extend
 * this class and provide additional details specific to their respective types.</p>
 *
 * @see Movie
 * @see Series
 * @see Review
 * @see Category
 */
public abstract class MediaItem implements Serializable {

    /** The title of the media item. */
    private String title;

    /** The description of the media item. */
    private String description;

    /** Indicates whether the media item is suitable for underage audiences. */
    private boolean isSuitableForUnderage;

    /** The category of the media item. */
    private Category category;

    /** The list of actors associated with the media item. */
    private String actors;

    /** The list of reviews for the media item. */
    private List<Review> reviews;

    /**
     * Constructs a new media item with the specified title, description, suitability for underage audiences,
     * category, actors, and an empty list of reviews.
     *
     * @param title                  the title of the media item
     * @param description            the description of the media item
     * @param isSuitableForUnderage  indicates whether the media item is suitable for underage audiences
     * @param category               the category of the media item
     * @param actors                 the list of actors associated with the media item
     */
    public MediaItem(String title, String description, boolean isSuitableForUnderage, Category category, String actors) {
        this.title = title;
        this.description = description;
        this.isSuitableForUnderage = isSuitableForUnderage;
        this.category = category;
        this.actors = actors;
        this.reviews = new ArrayList<>();
    }

    /**
     * Calculates and returns the average rating based on the reviews for the media item.
     *
     * @return the average rating of the media item, or 0 if there are no reviews
     */
    public double getAverageRating(){
        double sumRatings = 0.0;

        for(Review r : reviews) {
            sumRatings += r.getRating();
        }

        return sumRatings / reviews.size();
    }

    /**
     * Adds a review to the list of reviews for the media item.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        reviews.add(review);
    }

    /**
     * Gets the title of the media item.
     *
     * @return the title of the media item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the media item.
     *
     * @return the description of the media item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the media item is suitable for underage audiences.
     *
     * @return {@code true} if the media item is suitable for underage audiences, {@code false} otherwise
     */
    public boolean isSuitableForUnderage() {
        return isSuitableForUnderage;
    }

    /**
     * Gets the category of the media item.
     *
     * @return the category of the media item
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the media item.
     *
     * @param category the category of the media item
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the list of actors associated with the media item.
     *
     * @return the list of actors
     */
    public String getActors() {
        return actors;
    }

    /**
     * Gets the list of reviews for the media item.
     *
     * @return the list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Abstract method to be implemented by concrete subclasses.
     * Removes the given media item from the list of the relevant media items.
     *
     * @param item the media item to be removed from the relevant media items list
     */
    public abstract void removeRelevant(MediaItem item);

    /**
     * Returns a string representation of the media item.
     *
     * @return a string representation including only the title
     */
    @Override
    public String toString() {
        return  title;
    }

    /**
     * Checks if this media item is equal to another object.
     * Two media items are considered equal if they have the same title.
     *
     * @param o the object to compare to
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaItem mediaItem = (MediaItem) o;
        return Objects.equals(title, mediaItem.title);
    }

    /**
     * Generates a hash code for the media item based on its title.
     *
     * @return the hash code for the media item
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
