package by.bsu.orienteering.parser;

import by.bsu.orienteering.model.PersonResult;

import java.util.List;

/**
 * Created by alexey.memelov on 20-Dec-18.
 */
public interface IParser {

    List<PersonResult> parseResults(String data);
}
