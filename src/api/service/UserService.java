package api.service;

import api.model.Subscriber;
import api.model.User;

import java.io.IOException;
import java.util.List;

/**
 * The {@code UserService} class provides functionality related to user registration and login.
 *
 * <p>This class allows users to register new accounts by providing essential details such as name, surname, username, and password.
 * It also supports user login, verifying the provided credentials against the existing user database.</p>
 *
 * @see UserFileHandler
 * @see User
 * @see Subscriber
 */
public class UserService {

    /**
     * Registers a new user with the provided details.
     *
     * @param name     the user's first name
     * @param surname  the user's last name
     * @param username the chosen username
     * @param password the chosen password
     * @return {@code true} if the user is successfully registered, {@code false} if the username already exists or an error occurs
     */
    public static boolean registerUser(String name, String surname, String username, String password ) {
        boolean userRegistered = true;

        List<User> allUsers = UserFileHandler.getAllUsers();

        // check if username already exists;
        for(User user : allUsers) {
            if(user.getUsername().equals(username)) {
                userRegistered = false;
                break;
            }
        }

        // if username doesn't exist, register the user
        if(userRegistered) {
            User newUser = new Subscriber(username, password, name, surname);
            try {
                UserFileHandler.addUserAndSave(newUser);
            } catch(IOException e) {
                userRegistered = false;
            }

        }

        return userRegistered;
    }

    /**
     * Logs in a user with the provided credentials.
     *
     * @param username the username to log in
     * @param password the password associated with the username
     * @return the logged-in user if the credentials are valid, {@code null} otherwise
     */
    public static User loginUser(String username, String password) {

        List<User> allUsers = UserFileHandler.getAllUsers();

        // check if user with give username and password exists
        for(User user : allUsers) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;

    }
}
