package by.bsu.orienteering.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by alexey.memelov on 11-Jan-19.
 */
public class PersonGraphReportDTO {

    private Date date;

    private BigDecimal rating;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

}
