package by.bsu.orienteering.parser;

import by.bsu.orienteering.model.PersonResult;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 20-Dec-18.
 */
public class OBelarusParser extends AbstractParser {

    protected ParseResult getParseResult(String line) {
        ParseResult parseResult = new ParseResult();
        line = line.substring(line.indexOf(' ') + 1);
        parseResult.surname = line.substring(0, line.indexOf(' '));
        line = line.substring(line.indexOf(' ') + 1);
        parseResult.name = line.substring(0, line.indexOf(' '));
        parseResult.rest = line.substring(line.indexOf(' ') + 1);
        return parseResult;
    }

}
