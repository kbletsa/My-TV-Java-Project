package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Season} class represents a season of a series in the system.
 *
 * <p>Each season has a number, a release year, and a list of episodes. Instances of this class can be used to
 * represent individual seasons within a series.</p>
 *
 * @see Series
 * @see Episode
 */
public class Season implements Serializable {

    /** The number of the season. */
    private int number;

    /** The release year of the season. */
    private int year;

    /** The list of episodes associated with the season. */
    private List<Episode> episodes;

    /**
     * Constructs a new season with the specified number and release year and an empty episodes list.
     *
     * @param number the number of the season
     * @param year   the release year of the season
     */
    public Season(int number, int year) {
        this.number = number;
        this.year = year;
        this.episodes = new ArrayList<>();
    }

    /**
     * Adds an episode to the list of episodes for this season.
     *
     * @param episode the episode to add
     */
    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    /**
     * Gets the number of the season.
     *
     * @return the number of the season
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the release year of the season.
     *
     * @return the release year of the season
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the list of episodes associated with this season.
     *
     * @return the list of episodes
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }
}
