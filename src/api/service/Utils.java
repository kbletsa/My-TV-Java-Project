package api.service;

import api.model.MediaItem;
import api.model.Review;

import java.util.List;

/**
 * The {@code Utils} class provides utility methods for handling operations related to media items and reviews.
 *
 * <p>This class contains static methods that assist in updating lists of media items with reviews. It ensures proper handling
 * of adding, updating, and deleting reviews associated with media items.</p>
 *
 * @see MediaItem
 * @see Review
 */
public class Utils {

    /**
     * Updates a list of media items with a review.
     *
     * <p>This method ensures that the provided review is correctly added, updated, or deleted within the list of media items.
     * It considers whether the operation is an addition, an update, or a deletion of the review.</p>
     *
     * @param items  the list of media items to update
     * @param item   the media item to associate the review with
     * @param review the review to add, update, or delete
     * @param delete {@code true} if the review should be deleted, {@code false} otherwise
     */
    public static void updateWithReview(List<MediaItem> items, MediaItem item, Review review, boolean delete) {
        for(MediaItem mi : items) {
            if(mi.equals(item)) {
                // if this is a new review
                if (!mi.getReviews().contains(review)) {
                    mi.addReview(review);
                }
                // if this is an update of an old review
                else {
                    // remove previous data of the review
                    mi.getReviews().remove(review);

                    if(!delete) {
                        // and re-add it if this is an update of an existing review
                        mi.addReview(review);
                    }
                }
                break;
            }
        }
    }
}
