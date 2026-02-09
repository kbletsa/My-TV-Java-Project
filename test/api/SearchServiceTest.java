package api;

import api.model.*;
import api.service.MediaItemFileHandler;
import api.service.SearchService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchServiceTest {

    private static final String TEST_ITEMS_FILENAME = "test_items.dat";

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @AfterEach
    public void tearDown() {
        MediaItemFileHandler.allItems.clear();
        MediaItemFileHandler.saveItemsToFile();
    }

    @BeforeEach
    public void setUp() {

        MediaItemFileHandler.ITEMS_FILENAME = TEST_ITEMS_FILENAME;

        // Create test items
        MediaItem movie1 = new Movie("Movie 1", "Description 1", true, Category.ACTION, "Actor 1", 2022, 120);
        MediaItem movie2 = new Movie("Movie 2", "Description 2", false, Category.COMEDY, "Actor 2", 2023, 110);
        MediaItem series1 = new Series("Series 1", "Description 3", true, Category.DRAMA, "Actor 3");

        // Add test items to the list
        MediaItemFileHandler.addItem(movie1);
        MediaItemFileHandler.addItem(movie2);
        MediaItemFileHandler.addItem(series1);

        // Save test items to a file
        MediaItemFileHandler.saveItemsToFile();
    }

    @Test
    public void searchByType() {
        SearchService searchService = new SearchService();
        searchService.setType("MOVIE");

        List<MediaItem> results = searchService.search();
        assertEquals(2, results.size());
    }

    @Test
    public void searchByUnderageSuitability() {
        SearchService searchService = new SearchService();
        searchService.setSuitableForUnderage(true);

        List<MediaItem> results = searchService.search();
        assertEquals(2, results.size());
    }

    @Test
    public void searchByTitle() {
        SearchService searchService = new SearchService();
        searchService.setTitle("Movie 1");

        List<MediaItem> results = searchService.search();
        assertEquals(1, results.size());
    }

    @Test
    public void searchByActors() {
        SearchService searchService = new SearchService();
        searchService.setActors("Actor 3");

        List<MediaItem> results = searchService.search();
        assertEquals(1, results.size());
    }

    @Test
    public void searchByCategory() {
        SearchService searchService = new SearchService();
        searchService.setCategory(Category.DRAMA);

        List<MediaItem> results = searchService.search();
        assertEquals(1, results.size());
    }

    @Test
    public void searchByMinRating() {
        // Add reviews to items for testing ratings
        MediaItem movie1 = MediaItemFileHandler.allItems.get(0);
        MediaItemFileHandler.updateWithReview(movie1, new Review("Good movie", 4, null),  false);

        MediaItem movie2 = MediaItemFileHandler.allItems.get(1);
        MediaItemFileHandler.updateWithReview(movie2, new Review("Average movie", 3, null),  false);

        MediaItem series1 = MediaItemFileHandler.allItems.get(2);
        MediaItemFileHandler.updateWithReview(series1, new Review("Excellent series", 5, null),  false);

        SearchService searchService = new SearchService();
        searchService.setMinRating(4);

        List<MediaItem> results = searchService.search();
        assertEquals(2, results.size());
    }
}
