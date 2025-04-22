package enums;

/**
 * Enum representing different types of units.
 * This enum defines various unit types, each associated with a display name:
 * {@link #TWO_ROOM} - Represents a unit with 2 rooms.
 * {@link #THREE_ROOM} - Represents a unit with 3 rooms.
 * Each enum constant has a corresponding display name which is returned when
 * calling {@link #toString()}.
 */
public enum UnitType {
    TWO_ROOM("2-Room"),
    THREE_ROOM("3-Room");

    private final String displayName;

    /**
     * Constructs a UnitType with the specified display name.
     *
     * @param displayName The display name for the unit type.
     */
    UnitType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name associated with this unit type.
     *
     * @return The display name of the unit type.
     */
    @Override
    public String toString() {
        return displayName;
    }
}
