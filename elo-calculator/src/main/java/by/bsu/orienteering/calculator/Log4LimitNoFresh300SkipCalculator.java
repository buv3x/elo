package by.bsu.orienteering.calculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 04-Jan-19.
 */
public class Log4LimitNoFresh300SkipCalculator extends AbstractCalculator {

    protected static final Logger logger = Logger.getLogger(Log4LimitNoFresh300SkipCalculator.class.getName());

    private static double D = 300;

    @Override
    protected boolean isFresh(int previousFactor) {
        return false;
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

    protected boolean skipByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsed = sdf.parse("2014-01-01");
            return parsed.getTime() > date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    private double log(double base, double number) {
        return Math.log(number) / Math.log(base);
    }


}
