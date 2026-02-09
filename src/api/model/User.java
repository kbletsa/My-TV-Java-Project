package api.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code User} abstract class serves as a base class for all user types in the system.
 * It defines common attributes such as username and password, as well as basic behavior
 * applicable to all users.
 *
 * @see Admin
 * @see Subscriber
 */
public abstract class User implements Serializable {

    /** The unique username of the user. */
    private String username;

    /** The password associated with the user's account. */
    private String password;

    /**
     * Constructs a new user with the specified username and password.
     *
     * @param username the unique username of the user
     * @param password the password associated with the user's account
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password associated with the user's account.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
