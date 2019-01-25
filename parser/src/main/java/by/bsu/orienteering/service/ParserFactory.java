package by.bsu.orienteering.service;

import by.bsu.orienteering.model.Source;
import by.bsu.orienteering.parser.IParser;
import by.bsu.orienteering.parser.KronanParser;
import by.bsu.orienteering.parser.OBelarusParser;
import by.bsu.orienteering.parser.SFRParser;

/**
 * Created by alexey.memelov on 20-Dec-18.
 */
public class ParserFactory {

    public static IParser getInstance(Source source) {
        switch (source) {
            case OBELARUS: return new OBelarusParser();
            case SFR: return new SFRParser();
            case KRONAN: return new KronanParser();
            default: return new OBelarusParser();
        }
    }


}
