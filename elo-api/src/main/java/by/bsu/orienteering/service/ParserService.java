package by.bsu.orienteering.service;

import by.bsu.orienteering.model.ParseResultsRequest;
import by.bsu.orienteering.model.PersonResult;
import by.bsu.orienteering.model.Source;
import by.bsu.orienteering.model.EnumResult;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 21-Dec-18.
 */
@Path("/parser")
public class ParserService {

    protected static final Logger logger = Logger.getLogger(ParserService.class.getName());

    @POST
    @Path("/parseResults")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonResult> parseResults(ParseResultsRequest request) {
        String data = request.getData();
        List<PersonResult> personResults = ParserFactory.getInstance(Source.valueOf(request.getSource())).parseResults(data);
        return personResults;
    }

    @GET
    @Path("/getSources")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumResult> getSources() {
        List<EnumResult> result = new ArrayList<>();
        for (Source source : Source.values()) {
            EnumResult sourceResult = new EnumResult();
            sourceResult.setName(source.name());
            sourceResult.setDisplayName(source.getDisplayName());
            result.add(sourceResult);
        }

        return result;
    }

}
