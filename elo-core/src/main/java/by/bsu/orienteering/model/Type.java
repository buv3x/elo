package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 22-Dec-18.
 */
public enum Type {

    SPRINT("Спринт"),
    NON_SPRINT("Лес");

    private String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
