package api.model;

import java.io.Serializable;

/**
 * The {@code Episode} class represents an episode of a series in the system.
 *
 * <p>Each episode has a duration, and instances of this class can be used to represent individual episodes within a series.</p>
 *
 * @see Series
 */
public class Episode implements Serializable {

    /** The duration of the episode in minutes. */
    private int duration;

    /**
     * Constructs a new episode with the specified duration.
     *
     * @param duration the duration of the episode in minutes
     */
    public Episode(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the duration of the episode.
     *
     * @return the duration of the episode in minutes
     */
    public int getDuration() {
        return duration;
    }
}
