package by.bsu.orienteering.model;

import java.util.List;

/**
 * Created by alexey.memelov on 29-Dec-18.
 */
public class SaveResultsRequest {

    private Competition competition;

    private List<PersonResultRequest> results;

    public List<PersonResultRequest> getResults() {
        return results;
    }

    public void setResults(List<PersonResultRequest> results) {
        this.results = results;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
