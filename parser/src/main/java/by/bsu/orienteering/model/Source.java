package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 20-Dec-18.
 */
public enum Source {

    OBELARUS("OBelarus.net"),
    SFR("SFR"),
    KRONAN("Kronan");

    private String displayName;

    Source(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
