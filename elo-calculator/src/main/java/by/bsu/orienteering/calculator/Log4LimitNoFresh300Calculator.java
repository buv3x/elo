package by.bsu.orienteering.calculator;

import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 04-Jan-19.
 */
public class Log4LimitNoFresh300Calculator extends AbstractCalculator {

    protected static final Logger logger = Logger.getLogger(Log4LimitNoFresh300Calculator.class.getName());

    private static double D = 300;

    @Override
    protected boolean isFresh(int previousFactor) {
        return false;
    }

    @Override
    protected double getFreshness(int previousFactor) {
        return 1;
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
