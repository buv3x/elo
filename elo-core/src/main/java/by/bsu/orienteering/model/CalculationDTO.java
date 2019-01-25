package by.bsu.orienteering.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexey.memelov on 03-Jan-19.
 */
public class CalculationDTO {

    private Level level;

    private Type type;

    private Calculator calculator;

    private Date date;

    private List<Calculation> calculations = new ArrayList<>();

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
