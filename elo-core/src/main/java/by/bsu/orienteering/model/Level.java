package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 27-Dec-18.
 */
public enum Level {

    NATIONAL("Национальный", 40),
    BFO("БФО", 30),
    REGIONAL("Региональный", 20),
    SUB_REGIONAL("Местный", 10);

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
