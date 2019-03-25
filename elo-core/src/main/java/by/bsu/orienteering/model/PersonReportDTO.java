package by.bsu.orienteering.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by alexey.memelov on 11-Jan-19.
 */
public class PersonReportDTO {

    private int id;

    private String name;

    private Date date;

    private String type;

    private int factor;

    private int factorChange;

    private BigDecimal rating;

    private BigDecimal ratingChange;

    private int place;

    private int placeCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getFactorChange() {
        return factorChange;
    }

    public void setFactorChange(int factorChange) {
        this.factorChange = factorChange;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public BigDecimal getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(BigDecimal ratingChange) {
        this.ratingChange = ratingChange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }
}
