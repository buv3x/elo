package by.bsu.orienteering.parser;

import by.bsu.orienteering.model.PersonResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.WordUtils;

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
 * Created by alexey.memelov on 04-Jan-19.
 */
public abstract class AbstractParser implements IParser {

    protected static final Logger logger = Logger.getLogger(AbstractParser.class.getName());
    private final String NR = "в/к";
    private final Set<String> foreignCountries = new HashSet<>(Arrays.asList("UKR", "RUS", "LTU", "LAT", "GB", "USA", "MDA", "POL", "EST"));

    @Override
    public List<PersonResult> parseResults(String data) {
        List<PersonResult> result = new ArrayList<>();
        List<String> lines;
        try {
            lines = IOUtils.readLines(new StringReader(data));
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Collections.emptyList();
        }

        int place = 1;
        for (String line : lines) {
            line = line.trim();

            ParseResult parseResult = getParseResult(line);

            line = parseResult.rest;
            boolean foreign = false;
            for (String foreignCountry : foreignCountries) {
                if(line.contains(foreignCountry)) {
                    foreign = true;
                    break;
                }
            }
            if(foreign) {
                continue;
            }

            if(line.contains(NR)) {
                continue;
            }

            PersonResult personResult = new PersonResult();
            personResult.setSurname(transformString(parseResult.surname));
            personResult.setName(transformString(parseResult.name));
            if(line.contains(getNC())) {
                personResult.setPlace(0);
            } else {
                personResult.setPlace(place++);
            }
            result.add(personResult);
        }

        return result;
    }

    protected String transformString(String string) {
        return WordUtils.capitalizeFully(string).replace('ё', 'е');
    }

    protected String getNC() {
        return "снят";
    }

    protected abstract ParseResult getParseResult(String line);

    class ParseResult {

        String name;

        String surname;

        String rest;

    }

}
