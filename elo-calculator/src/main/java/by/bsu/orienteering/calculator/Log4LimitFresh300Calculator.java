package by.bsu.orienteering.calculator;

import by.bsu.orienteering.model.Calculation;
import by.bsu.orienteering.model.CalculationDTO;

import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 04-Jan-19.
 */
public class Log4LimitFresh300Calculator extends AbstractCalculator {
    protected static final Logger logger = Logger.getLogger(Log4LimitFresh300Calculator.class.getName());

    private static double D = 300;
    private static int FRESH = 201;

    @Override
    protected boolean isFresh(int previousFactor) {
        return previousFactor < FRESH;
    }

    @Override
    protected double getFreshness(int previousFactor) {
        if(!isFresh(previousFactor)) {
            return 1;
        }
        return 3 - ((double) previousFactor / 100);
    }

    @Override
    protected double getD() {
        return D;
    }

    @Override
    protected double calculateAdjusted(double totalRating, double calculatedSize) {
        double averageRating = totalRating / calculatedSize;
        double averageRatingAdjusted = averageRating * log(4, calculatedSize + 1);
        if(averageRatingAdjusted > 1) {
            averageRatingAdjusted = 1;
        }

        if(averageRatingAdjusted < -1) {
            averageRatingAdjusted = -1;
        }
        return averageRatingAdjusted;
    }

    private double log(double base, double number) {
        return Math.log(number) / Math.log(base);
    }


}
