package by.bsu.orienteering.model;

import java.util.List;

/**
 * Created by alexey.memelov on 27-Dec-18.
 */
public class SaveCompetitionResponse {

    private Integer id;

    private Gender gender;

    private List<PersonResult> personResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PersonResult> getPersonResults() {
        return personResults;
    }

    public void setPersonResults(List<PersonResult> personResults) {
        this.personResults = personResults;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
