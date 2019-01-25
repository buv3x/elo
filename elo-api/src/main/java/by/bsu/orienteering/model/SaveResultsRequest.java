package by.bsu.orienteering.model;

import java.util.List;

/**
 * Created by alexey.memelov on 29-Dec-18.
 */
public class SaveResultsRequest {

    private Integer id;

    private Gender gender;

    private List<PersonResultRequest> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<PersonResultRequest> getResults() {
        return results;
    }

    public void setResults(List<PersonResultRequest> results) {
        this.results = results;
    }
}
