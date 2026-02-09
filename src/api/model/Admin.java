package api.model;

import java.io.Serializable;

/**
 * The {@code Admin} class represents an administrative user in the system.
 * It extends the {@link User} abstract class and inherits the basic user
 * attributes such as username and password.
 *
 * @see User
 */
public class Admin extends User implements Serializable {

    /**
     * Constructs a new admin with the specified username and password.
     *
     * @param username the unique username of the admin
     * @param password the password associated with the admin's account
     */
    public Admin(String username, String password) {
        super(username, password);
    }
}
