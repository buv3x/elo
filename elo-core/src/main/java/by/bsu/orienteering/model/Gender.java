package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 22-Dec-18.
 */
public enum Gender {

    MEN("Men"),
    WOMEN("Women");

    private String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
