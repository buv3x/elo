package by.bsu.orienteering.model;

import java.util.Date;

/**
 * Created by alexey.memelov on 11-Jan-19.
 */
public class CompetitionNameDTO {

    private String name;

    private Date date;

    private String gender;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
