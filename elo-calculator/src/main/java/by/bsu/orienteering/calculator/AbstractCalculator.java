package by.bsu.orienteering.calculator;

import by.bsu.orienteering.model.Calculation;
import by.bsu.orienteering.model.CalculationDTO;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 05-Jan-19.
 */
public abstract class AbstractCalculator implements ICalculator {

    protected static final Logger logger = Logger.getLogger(AbstractCalculator.class.getName());

    @Override
    public void calculate(CalculationDTO calculationDTO) {
        for (Calculation calculation : calculationDTO.getCalculations()) {
            if(skipByDate(calculationDTO.getDate())) {
                calculation.setNewRating(calculation.getPreviousRating());
                calculation.setNewFactor(calculation.getPreviousFactor());
                continue;
            }

            double totalRating = 0;
            double calculatedSize = 0d;
            boolean isFresh = isFresh(calculation.getPreviousFactor());
            for (Calculation opponentCalculation : calculationDTO.getCalculations()) {
                if (opponentCalculation.getPersonId() == calculation.getPersonId()) {
                    continue;
                }

                boolean isOpponentFresh = !isFresh && isFresh(opponentCalculation.getPreviousFactor());
                double actualResult = calculateActualResult(calculation, opponentCalculation);
                double expectedResult = calculateExpectedResult(calculation, opponentCalculation);
                double difference = (actualResult - expectedResult);
                calculatedSize += (isOpponentFresh ? 0.5 : 1);
                totalRating += difference * (isOpponentFresh ? 0.5 : 1);
            }

            double adjustedRating = calculateAdjusted(totalRating, calculatedSize);

            calculation.setNewRating(calculation.getPreviousRating() +
                    (adjustedRating * calculationDTO.getLevel().getK() * (isFresh ? 2 : 1)));
            calculation.setNewFactor(calculation.getPreviousFactor() + calculationDTO.getLevel().getK());
        }
    }

    protected boolean skipByDate(Date date) {
        return false;
    }

    protected abstract boolean isFresh(int previousFactor);

    protected abstract double getD();

    protected abstract double calculateAdjusted(double totalRating, double calculatedSize);

    private double calculateExpectedResult(Calculation calculation, Calculation opponentCalculation) {
        double ratingDiff = opponentCalculation.getPreviousRating() - calculation.getPreviousRating();
        return 1 / (1 + Math.pow(10, ratingDiff / getD()));
    }

    private double calculateActualResult(Calculation calculation, Calculation opponentCalculation) {
        if(calculation.getResult() == opponentCalculation.getResult()) {
            return 0.5;
        }

        if(opponentCalculation.getResult() == 0) {
            return 1;
        }

        if(calculation.getResult() == 0) {
            return 0;
        }

        return calculation.getResult() < opponentCalculation.getResult() ? 1 : 0;
    }

}
