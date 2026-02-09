package api;

import api.model.*;
import api.service.UserFileHandler;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserFileHandlerTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }

    private static final String TEST_USERS_FILENAME = "test_users.dat";

    @Before
    public void setUp() {
        UserFileHandler.USERS_FILENAME = TEST_USERS_FILENAME;
        Path path = Paths.get("usersTest.dat");

        try {
            Files.deleteIfExists(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @BeforeEach
    void setUpTest() {
        UserFileHandler.allUsers = new ArrayList<>();
    }
    @Test
    public void testHierarchy() {
        Admin admin = new Admin("admin1", "password1");
        assertTrue(admin instanceof Admin);
        Subscriber subscriber = new Subscriber("user1", "password1", "name1", "surname1");
        assertTrue(subscriber instanceof Subscriber);
    }

    @Test
    public void addUserAndSave() throws IOException {
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        UserFileHandler.addUserAndSave(subscriber);

        List<User> allUsers = UserFileHandler.getAllUsers();
        assertTrue(allUsers.contains(subscriber));
    }

    @Test
    public void getAllUsers() {
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        UserFileHandler.addUser(subscriber);

        List<User> allUsers = UserFileHandler.getAllUsers();
        assertTrue(allUsers.contains(subscriber));
    }

    @Test
    public void readUsersFromFile() {
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        UserFileHandler.addUser(subscriber);
        UserFileHandler.saveUsersToFile();

        UserFileHandler.allUsers = new ArrayList<>();
        UserFileHandler.readUsersFromFile();

        List<User> allUsers = UserFileHandler.getAllUsers();
        assertTrue(allUsers.contains(subscriber));
        assertTrue(allUsers.get(0) instanceof Subscriber);
        assertEquals("user123", allUsers.get(0).getUsername());
        assertEquals("password", allUsers.get(0).getPassword());
        assertEquals("John", ((Subscriber)allUsers.get(0)).getName());
        assertEquals("Doe", ((Subscriber)allUsers.get(0)).getSurname());
    }

    @Test
    public void deleteFromFavoritesAndSave() throws IOException{
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        MediaItem mediaItem = new Movie("Test Item", "Description", true, Category.DRAMA, "Actor", 2019, 145);

        subscriber.addFavorite(mediaItem);
        UserFileHandler.addUserAndSave(subscriber);

        UserFileHandler.deleteFromFavoritesAndSave(mediaItem);
        UserFileHandler.readUsersFromFile();

        for(User u : UserFileHandler.allUsers) {
            if(u instanceof Subscriber) {
                assertFalse(((Subscriber)u).getFavorites().contains(mediaItem));
            }
        }
    }

    @Test
    public void removeFromUserFavorites() throws IOException{
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        MediaItem mediaItem = new Movie("Test Item", "Description", true, Category.DRAMA, "Actor", 2019, 145);

        subscriber.addFavorite(mediaItem);
        UserFileHandler.addUserAndSave(subscriber);

        // re-read the users from the file
        UserFileHandler.getAllUsers();

        assertTrue(UserFileHandler.removeFromUserFavorites(subscriber.getUsername(), mediaItem));
        for(User u : UserFileHandler.allUsers) {
            if(u.equals(subscriber)) {
                assertFalse(((Subscriber)u).getFavorites().contains(mediaItem));
                break;
            }
        }
    }

    @Test
    public void updateUserFavorites() {
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        MediaItem mediaItem = new Movie("Test Item", "Description", true, Category.DRAMA, "Actor", 2019, 145);

        UserFileHandler.addUser(subscriber);

        assertTrue(UserFileHandler.updateUserFavorites(subscriber.getUsername(), mediaItem));

        for(User u : UserFileHandler.allUsers) {
            if(u.equals(subscriber)) {
                assertTrue(((Subscriber)u).getFavorites().contains(mediaItem));
                break;
            }
        }
    }

    @Test
    public void updateFavoritesWithReview() {
        Subscriber subscriber = new Subscriber("user123", "password", "John", "Doe");
        MediaItem mediaItem = new Movie("Test Item", "Description", true, Category.DRAMA, "Actor", 2019, 145);
        Review review = new Review("Great item!", 5, subscriber);

        subscriber.addFavorite(mediaItem);
        UserFileHandler.addUser(subscriber);

        // save new review
        assertTrue(UserFileHandler.updateFavoritesWithReview(subscriber.getUsername(), mediaItem, review, false));
        UserFileHandler.getAllUsers();

        for(User u : UserFileHandler.allUsers) {
            if(u.equals(subscriber)) {
                assertTrue(((Subscriber)u).getFavorites().stream().anyMatch(item -> item.getReviews().contains(review)));
                break;
            }
        }
    }

}
