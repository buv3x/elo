package by.bsu.orienteering.service;

import by.bsu.orienteering.calculator.ICalculator;
import by.bsu.orienteering.calculator.Log4LimitFresh300Calculator;
import by.bsu.orienteering.calculator.Log4LimitFresh300SkipCalculator;
import by.bsu.orienteering.calculator.Log4LimitNoFresh300Calculator;
import by.bsu.orienteering.calculator.Log4LimitNoFresh300SkipCalculator;
import by.bsu.orienteering.calculator.Log6NoLimitNoFresh250Calculator;
import by.bsu.orienteering.model.Calculator;

/**
 * Created by alexey.memelov on 20-Dec-18.
 */
public class CalculatorFactory {

    public static ICalculator getInstance(Calculator calculator) {
        switch (calculator) {
            case LOG4_LIMIT_FRESH_300: return new Log4LimitFresh300Calculator();
//            case LOG4_LIMIT_NOFRESH_300_SKIP: return new Log4LimitNoFresh300SkipCalculator();
//            case LOG4_LIMIT_FRESH_300_SKIP: return new Log4LimitFresh300SkipCalculator();
            default: return new Log4LimitFresh300Calculator();
        }
    }


}
