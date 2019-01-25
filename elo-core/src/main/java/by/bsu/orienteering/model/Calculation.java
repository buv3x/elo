package by.bsu.orienteering.model;

import java.util.Date;

/**
 * Created by alexey.memelov on 03-Jan-19.
 */
public class Calculation {

    private int resultId;

    private int personId;

    private int result;

    private double previousRating;

    private double newRating;

    private int previousFactor;

    private int newFactor;

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public double getPreviousRating() {
        return previousRating;
    }

    public void setPreviousRating(double previousRating) {
        this.previousRating = previousRating;
    }

    public double getNewRating() {
        return newRating;
    }

    public void setNewRating(double newRating) {
        this.newRating = newRating;
    }

    public int getPreviousFactor() {
        return previousFactor;
    }

    public void setPreviousFactor(int previousFactor) {
        this.previousFactor = previousFactor;
    }

    public int getNewFactor() {
        return newFactor;
    }

    public void setNewFactor(int newFactor) {
        this.newFactor = newFactor;
    }

    public boolean skipByDate() {
        return false;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
