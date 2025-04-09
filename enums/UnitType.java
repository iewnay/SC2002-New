package enums;

public enum UnitType {
    TWO_ROOM("2-Room"),
    THREE_ROOM("3-Room");

    private final String displayName;

    UnitType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}