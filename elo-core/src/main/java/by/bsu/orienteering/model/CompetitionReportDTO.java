package by.bsu.orienteering.model;

import java.math.BigDecimal;

/**
 * Created by alexey.memelov on 11-Jan-19.
 */
public class CompetitionReportDTO {

    private int id;

    private String name;

    private BigDecimal rating;

    private BigDecimal ratingChange;

    private int place;

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

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

}
