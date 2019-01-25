package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 27-Dec-18.
 */
public enum Level {

    NATIONAL("National", 40),
    BFO("Federational", 30),
    REGIONAL("Regional", 20),
    SUB_REGIONAL("Sub-regional", 10);

    private String displayName;

    private int k;

    Level(String displayName, int k) {
        this.displayName = displayName;
        this.k = k;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getK() {
        return k;
    }
}
