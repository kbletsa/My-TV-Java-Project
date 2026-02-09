package api.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Review} class represents a review of a media item in the system.
 *
 * <p>Each review has text, a rating, and is associated with a subscriber who provided the review.</p>
 *
 * @see MediaItem
 * @see Subscriber
 */
public class Review implements Serializable {

    /** The text of the review. */
    private String text;

    /** The rating given in the review. */
    private int rating;

    /** The subscriber who provided the review. */
    private Subscriber subscriber;

    /**
     * Constructs a new review with the specified text, rating, and subscriber.
     *
     * @param text       the text of the review
     * @param rating     the rating given in the review
     * @param subscriber the subscriber who provided the review
     */
    public Review(String text, int rating, Subscriber subscriber) {
        this.text = text;
        this.rating = rating;
        this.subscriber = subscriber;
    }

    /**
     * Gets the text of the review.
     *
     * @return the text of the review
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the rating given in the review.
     *
     * @return the rating of the review
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the subscriber who provided the review.
     *
     * @return the subscriber associated with the review
     */
    public Subscriber getSubscriber() {
        return subscriber;
    }

    /**
     * Sets the subscriber who provided the review.
     *
     * @param subscriber the subscriber to set
     */
    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    /**
     * Returns a string representation of the review, including the name, surname, text, and rating of the subscriber.
     *
     * @return a string representation of the review
     */
    @Override
    public String toString() {
        return subscriber.getName() + " " + subscriber.getSurname() + ": " + text + ", " + rating;
    }

    /**
     * Checks if this review is equal to another object.
     * Two reviews are considered equal if they are associated with the same subscriber.
     *
     * @param o the object to compare to
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(subscriber, review.subscriber);
    }

    /**
     * Generates a hash code for the review based on the associated subscriber.
     *
     * @return the hash code for the review
     */
    @Override
    public int hashCode() {
        return Objects.hash(subscriber);
    }
}
