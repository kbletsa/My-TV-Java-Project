package api.model;

import java.io.Serializable;

/**
 * The {@code Category} enum represents the different categories of media items in the system.
 *
 * <p>Each category has a display name, and instances of this enum can be used to categorize movies and series.</p>
 *
 * @see Movie
 * @see Series
 */
public enum Category implements Serializable {
    /** Represents all categories. */
    ALL("ALL"),

    /** Represents the comedy category. */
    COMEDY("COMEDY"),

    /** Represents the horror category. */
    HORROR("HORROR"),

    /** Represents the sci-fi category. */
    SCI_FI("SCI-FI"),

    /** Represents the action category. */
    ACTION("ACTION"),

    /** Represents the drama category. */
    DRAMA("DRAMA");

    /** The display name of the category. */
    private final String displayName;

    /**
     * Constructs a new category with the specified display name.
     *
     * @param displayName the display name of the category
     */
    Category(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the category.
     *
     * @return the display name of the category
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets a {@code Category} enum from a string representation.
     *
     * @param text the string representation of the category
     * @return the corresponding {@code Category} enum
     * @throws IllegalArgumentException if no constant with the given text is found in the {@code Category} enum
     */
    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.displayName.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in Category enum");
    }
}
