package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 04-Jan-19.
 */
public class CalculateCompetitionRequest {

    private int competitionId;

    private Calculator calculator;

    private Type type;

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
