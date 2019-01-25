package by.bsu.orienteering.service;

import by.bsu.orienteering.dao.CompetitionDAO;
import by.bsu.orienteering.model.Gender;
import by.bsu.orienteering.model.ParseResultsRequest;
import by.bsu.orienteering.model.Person;
import by.bsu.orienteering.model.PersonResult;
import by.bsu.orienteering.model.PersonResultRequest;
import by.bsu.orienteering.model.Result;
import by.bsu.orienteering.model.SaveCompetitionRequest;
import by.bsu.orienteering.model.SaveCompetitionResponse;
import by.bsu.orienteering.model.SaveResultsRequest;
import by.bsu.orienteering.model.Source;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by alexey.memelov on 26-Dec-18.
 */
@Path("/competition")
public class CompetitionService {

    @POST
    @Path("/saveCompetition")
    @Produces(MediaType.APPLICATION_JSON)
    public SaveCompetitionResponse saveCompetition(SaveCompetitionRequest competition) throws Exception {
        CompetitionDAO dao = new CompetitionDAO();

        ParseResultsRequest parseRequest = competition.getParseRequest();
        List<PersonResult> personResults =
                ParserFactory.getInstance(Source.valueOf(parseRequest.getSource())).parseResults(parseRequest.getData());

        for (PersonResult personResult : personResults) {
            Integer id = dao.getPerson(
                    personResult.getSurname(), personResult.getName(), competition.getCompetition().getGender());
            if(id != null) {
                personResult.setId(id);
            }
        }
        SaveCompetitionResponse response = new SaveCompetitionResponse();
        Integer id = dao.saveCompetition(competition.getCompetition());
        response.setId(id);
        response.setPersonResults(personResults);
        response.setGender(competition.getCompetition().getGender());

        return response;
    }

    @POST
    @Path("/saveResults")
    @Produces(MediaType.APPLICATION_JSON)
    public void saveResults(SaveResultsRequest request) throws Exception {
        CompetitionDAO dao = new CompetitionDAO();
        for (PersonResultRequest personResult : request.getResults()) {
            Integer personId = personResult.getId() > 0 ?
                    personResult.getId() : createPerson(request, dao, personResult);
            Result result = new Result();
            result.setPersonId(personId);
            result.setCompetitionId(request.getId());
            result.setResult(personResult.getPlace());
            dao.saveResult(result);
        }
    }

    @GET
    @Path("/getPersons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons(@QueryParam("gender") String gender) throws Exception {
        CompetitionDAO dao = new CompetitionDAO();
        return dao.getPersons(Gender.valueOf(gender.toUpperCase()));
    }

    private Integer createPerson(SaveResultsRequest request, CompetitionDAO dao, PersonResultRequest result) throws Exception {
        Person person = new Person();
        person.setName(result.getName());
        person.setSurname(result.getSurname());
        person.setGender(request.getGender());
        return dao.savePerson(person);
    }

}
