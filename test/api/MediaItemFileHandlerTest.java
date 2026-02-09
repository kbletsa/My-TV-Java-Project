package api;

import api.model.*;
import api.service.MediaItemFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.Media;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

class MediaItemFileHandlerTest {

    private static final String TEST_ITEMS_FILENAME = "test_items.dat";

    @BeforeEach
    void setUp() {
        MediaItemFileHandler.ITEMS_FILENAME = TEST_ITEMS_FILENAME;
        MediaItemFileHandler.allItems.clear();
    }

    @Test
    void addItem() {
        Movie movie = new Movie("Test Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        MediaItemFileHandler.addItem(movie);

        // Assert
        assertEquals(1, MediaItemFileHandler.allItems.size(), "One media item should be added");
        assertTrue(MediaItemFileHandler.allItems.contains(movie), "The added item should be in the list");
    }

    @Test
    void readItemsFromFile() {
        Movie movie = new Movie("Test Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        MediaItemFileHandler.addItem(movie);
        MediaItemFileHandler.saveItemsToFile();

        List<MediaItem> readItems = MediaItemFileHandler.readItemsFromFile();

        // Assert
        assertEquals(1, readItems.size(), "One media item should be read");
        assertTrue(readItems.get(0) instanceof Movie, "The read item should be an instance of Movie");
        assertEquals(movie.getTitle(), readItems.get(0).getTitle(), "The read item should have the correct title");
    }

    @Test
    void deleteAndSaveMovie() {
        Movie movie = new Movie("Test Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        MediaItemFileHandler.addItem(movie);

        MediaItemFileHandler.deleteAndSave(movie);
        MediaItemFileHandler.readItemsFromFile();

        // Assert
        assertEquals(0, MediaItemFileHandler.allItems.size(), "The media item should be deleted");
    }

    @Test
    public void updateAndSaveMovie() {
        Movie movie = new Movie("Test Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        MediaItemFileHandler.addItem(movie);
        MediaItemFileHandler.saveItemsToFile();

        Movie updatedMovie = new Movie("Updated Movie", "Updated Description", false, Category.COMEDY, "Updated Actor", 2022, 130);
        Movie relevantM1 = new Movie("Test Relevant Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        Movie relevantM2 = new Movie("Test Relevant Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        updatedMovie.addRelevantMovie(relevantM1);
        updatedMovie.addRelevantMovie(relevantM2);
        MediaItemFileHandler.updateAndSave(updatedMovie, movie.getTitle());

        // Assert
        assertEquals(1, MediaItemFileHandler.allItems.size(), "One media item should be updated");
        assertEquals("Updated Movie", MediaItemFileHandler.allItems.get(0).getTitle());
        assertEquals("Updated Description", MediaItemFileHandler.allItems.get(0).getDescription());
        assertEquals(Category.COMEDY, MediaItemFileHandler.allItems.get(0).getCategory());
        assertEquals("Updated Actor", MediaItemFileHandler.allItems.get(0).getActors());
        assertEquals(2022, ((Movie)MediaItemFileHandler.allItems.get(0)).getYear());
        assertEquals(130, ((Movie)MediaItemFileHandler.allItems.get(0)).getDuration());
        assertEquals(2, ((Movie)MediaItemFileHandler.allItems.get(0)).getRelevantMovies().size());
        assertTrue(MediaItemFileHandler.allItems.contains(updatedMovie), "The updated item should be in the list");
    }

    @Test
    public void updateAndSaveSeries() {
        Series series = new Series("New series", "Series description", true, Category.SCI_FI, "Actors");

        MediaItemFileHandler.addItem(series);
        MediaItemFileHandler.saveItemsToFile();

        Series updatedSeries = new Series("Updated series", "Updated description", false, Category.DRAMA, "Updated actors");
        Series relevantS1 = new Series("Test Relevant Series 1", "Description", true, Category.ACTION, "Actor");
        Series relevantS2 = new Series("Test Relevant Series 2", "Description", true, Category.ACTION, "Actor");
        updatedSeries.addRelevantSeries(relevantS1);
        updatedSeries.addRelevantSeries(relevantS2);

        updatedSeries.removeRelevant(relevantS2);

        Season s1s1 = new Season(1, 2016);
        Episode e1 = new Episode(19);
        Episode e2 = new Episode(20);
        Episode e3 = new Episode(19);
        s1s1.addEpisode(e1);
        s1s1.addEpisode(e2);
        s1s1.addEpisode(e3);
        updatedSeries.addSeason(s1s1);

        MediaItemFileHandler.updateAndSave(updatedSeries, series.getTitle());

        // Assert
        assertEquals(1, MediaItemFileHandler.allItems.size(), "One media item should be updated");
        assertEquals("Updated series", MediaItemFileHandler.allItems.get(0).getTitle());
        assertEquals("Updated description", MediaItemFileHandler.allItems.get(0).getDescription());
        assertEquals(Category.DRAMA, MediaItemFileHandler.allItems.get(0).getCategory());
        assertEquals("Updated actors", MediaItemFileHandler.allItems.get(0).getActors());
        assertEquals(1, ((Series)MediaItemFileHandler.allItems.get(0)).getRelevantSeries().size());
        assertEquals(1, ((Series)MediaItemFileHandler.allItems.get(0)).getSeasons().size());
        assertEquals(3, ((Series)MediaItemFileHandler.allItems.get(0)).getSeasons().get(0).getEpisodes().size());
        assertTrue(MediaItemFileHandler.allItems.contains(updatedSeries), "The updated item should be in the list");

    }

    @Test
    public void updateWithReview() {
        Movie movie = new Movie("Test Movie", "Description", true, Category.ACTION, "Actor", 2023, 120);
        MediaItemFileHandler.addItem(movie);
        MediaItemFileHandler.saveItemsToFile();

        Review review = new Review("Great movie!", 5, new Subscriber("user123", "password123", "John", "Doe"));
        MediaItemFileHandler.updateWithReview(movie, review, false);

        MediaItemFileHandler.readItemsFromFile();

        // Assert
        assertEquals(1, MediaItemFileHandler.allItems.size(), "One media item should be updated with a review");
        assertEquals(1, MediaItemFileHandler.allItems.get(0).getReviews().size(), "The media item should have one review");
        assertTrue(MediaItemFileHandler.allItems.get(0).getReviews().contains(review), "The review should be added to the media item");
    }
}

