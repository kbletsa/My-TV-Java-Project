package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Movie} class represents a movie in the system, extending the {@link MediaItem} abstract class.
 *
 * <p>A movie has additional attributes such as the release year, duration, and a list of relevant movies.</p>
 *
 * @see MediaItem
 */
public class Movie extends MediaItem implements Serializable {

    /** The release year of the movie. */
    private int year;

    /** The duration of the movie in minutes. */
    private int duration;

    /** The list of relevant movies associated with this movie. */
    private List<Movie> relevantMovies;

    /**
     * Constructs a new movie with the specified title, description, suitability for underage audiences,
     * category, actors, release year, and duration.
     *
     * @param title                  the title of the movie
     * @param description            the description of the movie
     * @param isSuitableForUnderage  indicates whether the movie is suitable for underage audiences
     * @param category               the category of the movie
     * @param actors                 the list of actors associated with the movie
     * @param year                   the release year of the movie
     * @param duration               the duration of the movie in minutes
     */
    public Movie(String title, String description, boolean isSuitableForUnderage, Category category, String actors, int year, int duration) {
        super(title, description, isSuitableForUnderage, category, actors);
        this.year = year;
        this.duration = duration;
        this.relevantMovies = new ArrayList<>();
    }

    /**
     * Adds a relevant movie to the list of relevant movies for this movie.
     *
     * @param movie the relevant movie to add
     */
    public void addRelevantMovie(Movie movie) {
        relevantMovies.add(movie);
    }

    /**
     * Gets the release year of the movie.
     *
     * @return the release year of the movie
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the duration of the movie in minutes.
     *
     * @return the duration of the movie
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the list of relevant movies associated with this movie.
     *
     * @return the list of relevant movies
     */
    public List<Movie> getRelevantMovies() {
        return relevantMovies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRelevant(MediaItem item) {
        relevantMovies.remove(item);
    }
}
