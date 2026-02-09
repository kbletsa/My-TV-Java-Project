package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Series} class represents a series in the system, extending the {@link MediaItem} abstract class.
 *
 * <p>A series has additional attributes such as a list of seasons and a list of relevant series.</p>
 *
 * @see MediaItem
 * @see Season
 */
public class Series extends MediaItem implements Serializable {

    /** The list of seasons associated with the series. */
    private List<Season> seasons;

    /** The list of relevant series associated with this series. */
    private List<Series> relevantSeries;

    /**
     * Constructs a new series with the specified title, description, suitability for underage audiences,
     * category, and actors.
     *
     * @param title                  the title of the series
     * @param description            the description of the series
     * @param isSuitableForUnderage  indicates whether the series is suitable for underage audiences
     * @param category               the category of the series
     * @param actors                 the list of actors associated with the series
     */
    public Series(String title, String description, boolean isSuitableForUnderage, Category category, String actors) {
        super(title, description, isSuitableForUnderage, category, actors);
        this.seasons = new ArrayList<>();
        this.relevantSeries = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRelevant(MediaItem item) {
        relevantSeries.remove(item);
    }

    /**
     * Adds a season to the list of seasons for this series.
     *
     * @param season the season to add
     */
    public void addSeason(Season season) {
        seasons.add(season);
    }

    /**
     * Adds a relevant series to the list of relevant series for this series.
     *
     * @param series the relevant series to add
     */
    public void addRelevantSeries(Series series) {
        relevantSeries.add(series);
    }

    /**
     * Gets the list of seasons associated with this series.
     *
     * @return the list of seasons
     */
    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     * Gets the list of relevant series associated with this series.
     *
     * @return the list of relevant series
     */
    public List<Series> getRelevantSeries() {
        return relevantSeries;
    }
}
