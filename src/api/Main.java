package api;

import api.model.*;
import api.service.MediaItemFileHandler;
import api.service.UserFileHandler;
import gui.AdminMainFrame;
import gui.Login;
import gui.SubscriberMainFrame;

import java.io.IOException;

public class Main {

    public static User loggedInUser;

    public static void main(String[] args) {

        // call this method only the first time, so that the users.dat and items.dat files are created and filled with values
//        initData();
        MediaItemFileHandler.readItemsFromFile();
        UserFileHandler.readUsersFromFile();
        new Login();
    }

    private static void initData() {
        // Create Admins
        User u1 = new Admin("admin1", "password1");
        User u2 = new Admin("admin2", "password2");

        // Create Subscribers
        Subscriber u3 = new Subscriber("user1", "password1", "User1", "Surname1");
        Subscriber u4 = new Subscriber("user2", "password2", "User2", "Surname2");
        Subscriber u5 = new Subscriber("user3", "password3", "User3", "Surname3");

        // Create movies and add reviews to each one of them
        Movie movie1 = new Movie("Harry Potter and the Philosopher's Stone", "An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.", true, Category.ACTION, "Daniel Radcliffe, Emma Watson", 2001, 152);
        Review r11 = new Review("Good film", 3, u3);
        Review r12 = new Review("Amazing", 5, u4);
        movie1.addReview(r11);
        movie1.addReview(r12);

        Movie movie2 = new Movie("Harry Potter and the Chamber of Secrets", "An ancient prophecy seems to be coming true when a mysterious presence begins stalking the corridors of a school of magic and leaving its victims paralyzed.", true, Category.ACTION, "Daniel Radcliffe, Emma Watson", 2002, 135);
        Review r21 = new Review("Better than the first", 4, u3);
        Review r22 = new Review("Also amazing", 5, u4);
        movie2.addReview(r21);
        movie2.addReview(r22);

        Movie movie3 = new Movie("Harry Potter and the Prisoner of Azkaban", "Harry Potter, Ron and Hermione return to Hogwarts School of Witchcraft and Wizardry for their third year of study, where they delve into the mystery surrounding an escaped prisoner who poses a dangerous threat to the young wizard.", true, Category.ACTION, "Daniel Radcliffe, Emma Watson", 2002, 143);
        Review r31 = new Review("Another fantastic installment", 5, u3);
        Review r32 = new Review("Great storyline", 4, u5);
        movie3.addReview(r31);
        movie3.addReview(r32);

        movie1.addRelevantMovie(movie2);
        movie1.addRelevantMovie(movie3);

        movie2.addRelevantMovie(movie1);
        movie2.addRelevantMovie(movie3);

        movie3.addRelevantMovie(movie1);
        movie3.addRelevantMovie(movie2);

        Movie movie4 = new Movie("Amélie", "Despite being caught in her imaginative world, Amelie, a young waitress, decides to help people find happiness. Her quest to spread joy leads her on a journey where she finds true love.", true, Category.COMEDY, "Audrey Tautou, Mathieu Kassovitz", 2001, 122);
        Review r41 = new Review("Quirky, I got bored", 2, u5);
        Review r42 = new Review("Charming and heartwarming", 3, u4);
        movie4.addReview(r41);
        movie4.addReview(r42);

        Movie movie5 = new Movie("A Clockwork Orange", "In the future, a sadistic gang leader is imprisoned and volunteers for a conduct-aversion experiment, but it doesn't go as planned.", false, Category.SCI_FI, "Malcolm McDowell, Patrick Magee", 1971, 134);
        Review r51 = new Review("A dark and thought-provoking masterpiece", 5, u4);
        Review r52 = new Review("Intense and disturbing", 2, u5);
        movie5.addReview(r51);
        movie5.addReview(r52);

        // Fleabag
        Series series1 = new Series("Fleabag", "Series adapted from the award-winning play about a young woman trying to cope with life in London whilst coming to terms with a recent tragedy.", false, Category.COMEDY, "Phoebe Waller Bridge");
        // seasons
        Season s1s1 = new Season(1, 2016);
        Episode e1 = new Episode(19);
        Episode e2 = new Episode(20);
        Episode e3 = new Episode(19);
        Episode e4 = new Episode(19);
        Episode e5 = new Episode(19);
        s1s1.addEpisode(e1);
        s1s1.addEpisode(e2);
        s1s1.addEpisode(e3);
        s1s1.addEpisode(e4);
        s1s1.addEpisode(e5);
        series1.addSeason(s1s1);

        Season s1s2 = new Season(2, 2019);
        Episode e6 = new Episode(19);
        Episode e7 = new Episode(19);
        Episode e8 = new Episode(19);
        Episode e9 = new Episode(21);
        Episode e10 = new Episode(19);
        s1s2.addEpisode(e6);
        s1s2.addEpisode(e7);
        s1s2.addEpisode(e8);
        s1s2.addEpisode(e9);
        s1s2.addEpisode(e10);
        series1.addSeason(s1s2);

        // Reviews
        Review r1s1 = new Review("Best series of the century!", 5, u3);
        Review r2s1 = new Review("Phoebe Waller Bridge is a genious!", 5, u4);
        series1.addReview(r1s1);
        series1.addReview(r2s1);

        // The office
        Series series2 = new Series("The Office", "A mockumentary on a group of typical office workers, where the workday consists of ego clashes, inappropriate behavior, and tedium.", true, Category.COMEDY, "Steve Carell");
        // seasons
        Season s1s3 = new Season(1, 2005);
        Episode e11 = new Episode(22);
        Episode e12 = new Episode(22);
        Episode e13 = new Episode(22);
        Episode e14 = new Episode(22);
        s1s3.addEpisode(e11);
        s1s3.addEpisode(e12);
        s1s3.addEpisode(e13);
        s1s3.addEpisode(e14);
        series2.addSeason(s1s3);

        Season s1s4 = new Season(2, 2006);
        Episode e15 = new Episode(21);
        Episode e16 = new Episode(21);
        Episode e17 = new Episode(21);
        Episode e18 = new Episode(23);
        s1s4.addEpisode(e15);
        s1s4.addEpisode(e16);
        s1s4.addEpisode(e17);
        s1s4.addEpisode(e18);
        series2.addSeason(s1s4);

        // Reviews
        Review r1s2 = new Review("Michael Scott is the funniest", 4, u5);
        Review r2s2 = new Review("I didn't like it", 1, u4);
        series2.addReview(r1s2);
        series2.addReview(r2s2);

        // The crown
        Series series3 = new Series("The Crown", "Follows the political rivalries and romances of Queen Elizabeth II's reign and the events that shaped Britain for the second half of the 20th century.", true, Category.DRAMA, "Imelda Staunton");
        // Season 1
        Season s3s1 = new Season(1, 2016);
        s3s1.addEpisode(new Episode(10));
        s3s1.addEpisode(new Episode(10));
        s3s1.addEpisode(new Episode(10));
        s3s1.addEpisode(new Episode(10));
        s3s1.addEpisode(new Episode(10));
        series3.addSeason(s3s1);

        // Season 2
        Season s3s2 = new Season(2, 2017);
        s3s2.addEpisode(new Episode(10));
        s3s2.addEpisode(new Episode(10));
        s3s2.addEpisode(new Episode(10));
        s3s2.addEpisode(new Episode(10));
        s3s2.addEpisode(new Episode(10));
        series3.addSeason(s3s2);

        // Reviews
        Review r1s3 = new Review("Great photography", 3, u5);
        Review r2s3 = new Review("Amazing actors", 4, u3);
        series3.addReview(r1s3);
        series3.addReview(r2s3);

        // Bojack Horseman
        Series series4 = new Series("Bojack Horseman", "BoJack Horseman was the star of the hit television show \"Horsin' Around\" in the '80s and '90s, but now he's washed up, living in Hollywood, complaining about everything, and wearing colorful sweaters.", false, Category.COMEDY, "Aaron Paul");
        // Season 1
        Season s4s1 = new Season(1, 2014);
        s4s1.addEpisode(new Episode(12));
        s4s1.addEpisode(new Episode(12));
        s4s1.addEpisode(new Episode(12));
        s4s1.addEpisode(new Episode(12));
        s4s1.addEpisode(new Episode(12));
        series4.addSeason(s4s1);

        // Season 2
        Season s4s2 = new Season(2, 2015);
        s4s2.addEpisode(new Episode(12));
        s4s2.addEpisode(new Episode(12));
        s4s2.addEpisode(new Episode(12));
        s4s2.addEpisode(new Episode(12));
        s4s2.addEpisode(new Episode(12));
        series4.addSeason(s4s2);

        // Reviews
        Review r1s4 = new Review("Quite dark for my taste", 2, u5);
        Review r2s4 = new Review("Very deep", 4, u4);
        series4.addReview(r1s4);
        series4.addReview(r2s4);

        // Euphoria
        Series series5 = new Series("Euphoria", "A look at life for a group of high school students as they grapple with issues of drugs, sex, and violence.", false, Category.DRAMA, "Zendaya, Hunter Schafer");
        // Season 1
        Season s5s1 = new Season(1, 2019);
        s5s1.addEpisode(new Episode(8));
        s5s1.addEpisode(new Episode(8));
        s5s1.addEpisode(new Episode(8));
        s5s1.addEpisode(new Episode(8));
        s5s1.addEpisode(new Episode(8));
        series5.addSeason(s5s1);

        // Season 2
        Season s45s2 = new Season(2, 2022);
        s45s2.addEpisode(new Episode(8));
        s45s2.addEpisode(new Episode(8));
        s45s2.addEpisode(new Episode(8));
        s45s2.addEpisode(new Episode(8));
        s45s2.addEpisode(new Episode(8));
        series5.addSeason(s45s2);

        // Reviews
        Review r1s5 = new Review("Quite dark for my taste", 2, u3);
        Review r2s5 = new Review("Zendaya is giving the performance of her life", 4, u4);
        series5.addReview(r1s5);
        series5.addReview(r2s5);

        // add favorites to users
        u3.addFavorite(movie1);
        u3.addFavorite(movie2);
        u3.addFavorite(movie3);

        u4.addFavorite(series1);
        u4.addFavorite(series4);

        u5.addFavorite(movie3);
        u5.addFavorite(series2);

        // Add them to the list
        UserFileHandler.addUser(u1);
        UserFileHandler.addUser(u2);
        UserFileHandler.addUser(u3);
        UserFileHandler.addUser(u4);
        UserFileHandler.addUser(u5);

        UserFileHandler.saveUsersToFile();

        MediaItemFileHandler.addItem(movie1);
        MediaItemFileHandler.addItem(movie2);
        MediaItemFileHandler.addItem(movie3);
        MediaItemFileHandler.addItem(movie4);
        MediaItemFileHandler.addItem(movie5);

        MediaItemFileHandler.addItem(series1);
        MediaItemFileHandler.addItem(series2);
        MediaItemFileHandler.addItem(series3);
        MediaItemFileHandler.addItem(series4);
        MediaItemFileHandler.addItem(series5);

        MediaItemFileHandler.saveItemsToFile();
    }

}
