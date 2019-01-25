package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 27-Dec-18.
 */
public class SaveCompetitionRequest {

    private ParseResultsRequest parseRequest;

    private Competition competition;

    public ParseResultsRequest getParseRequest() {
        return parseRequest;
    }

    public void setParseRequest(ParseResultsRequest parseRequest) {
        this.parseRequest = parseRequest;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
