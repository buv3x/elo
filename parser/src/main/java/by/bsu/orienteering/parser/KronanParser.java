package by.bsu.orienteering.parser;

/**
 * Created by alexey.memelov on 04-Jan-19.
 */
public class KronanParser extends AbstractParser {

    @Override
    protected ParseResult getParseResult(String line) {
        ParseResult parseResult = new ParseResult();
        line = line.substring(line.indexOf('\t') + 1);
        parseResult.surname = line.substring(0, line.indexOf(' '));
        line = line.substring(line.indexOf(' ') + 1);
        parseResult.name = line.substring(0, line.indexOf('\t'));
        parseResult.rest = line.substring(line.indexOf('\t') + 1);
        return parseResult;
    }

}
