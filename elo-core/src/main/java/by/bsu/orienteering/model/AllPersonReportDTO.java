package by.bsu.orienteering.model;

import java.math.BigDecimal;

/**
 * Created by alexey.memelov on 08-Jan-19.
 */
public class AllPersonReportDTO {

    private int id;

    private String displayName;

    private BigDecimal rating;

    private BigDecimal lastYearChange;

    private int factor;

    private int lastYearFactor;

    private int place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public BigDecimal getLastYearChange() {
        return lastYearChange;
    }

    public void setLastYearChange(BigDecimal lastYearChange) {
        this.lastYearChange = lastYearChange;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getLastYearFactor() {
        return lastYearFactor;
    }

    public void setLastYearFactor(int lastYearFactor) {
        this.lastYearFactor = lastYearFactor;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
