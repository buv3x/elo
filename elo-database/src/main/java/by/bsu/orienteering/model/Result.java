package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 29-Dec-18.
 */
public class Result {

    private int personId;

    private int competitionId;

    private int result;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
