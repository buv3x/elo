package by.bsu.orienteering.service;

import by.bsu.orienteering.dao.ReportDAO;
import by.bsu.orienteering.model.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by alexey.memelov on 08-Jan-19.
 */
@Path("/report")
public class ReportService {

    @GET
    @Path("/getRatingList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AllPersonReportDTO> getPersons(@QueryParam("gender") String genderString, @QueryParam("type") String typeString
            , @QueryParam("period") Integer period, @QueryParam("factor") Integer factor) throws Exception {
        ReportDAO dao = new ReportDAO();
        Gender gender = genderString != null ? Gender.valueOf(genderString.toUpperCase()) : Gender.WOMEN;
        Type type = typeString != null ? Type.valueOf(typeString.toUpperCase()) : null;

        return dao.getAllPersonReport(gender, type, period, factor);
    }

    @GET
    @Path("/getPersonGraphDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonGraphReportDTO> getPersonGraphDetails(@QueryParam("id") String id, @QueryParam("type") String typeString) throws Exception {
        ReportDAO dao = new ReportDAO();
        Type type = typeString != null ? Type.valueOf(typeString.toUpperCase()) : null;

        List<PersonGraphReportDTO> personReport = dao.getPersonGraphReport(Integer.valueOf(id), type);
        if(personReport.size() > 100) {
            personReport = personReport.subList(personReport.size() - 100, personReport.size());
        }

        return personReport;
    }

    @GET
    @Path("/getPersonDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonReportDTO> getPersonDetails(@QueryParam("id") String id, @QueryParam("type") String typeString) throws Exception {
        ReportDAO dao = new ReportDAO();
        Type type = typeString != null ? Type.valueOf(typeString.toUpperCase()) : null;

        List<PersonReportDTO> personReport = dao.getPersonReport(Integer.valueOf(id), type);

        return personReport;
    }

    @GET
    @Path("/getCompetitionDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompetitionReportDTO> getCompetitionDetails(@QueryParam("id") String id, @QueryParam("type") String typeString) throws Exception {
        ReportDAO dao = new ReportDAO();
        Type type = typeString != null ? Type.valueOf(typeString.toUpperCase()) : null;

        List<CompetitionReportDTO> competitionReport = dao.getCompetitionReport(Integer.valueOf(id), type);
        Collections.sort(competitionReport, Comparator.comparingInt(CompetitionReportDTO::getPlace));

        return competitionReport;
    }

    @GET
    @Path("/getCompetitions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompetitionsReportDTO> getCompetitions() throws Exception {
        ReportDAO dao = new ReportDAO();

        List<CompetitionsReportDTO> competitionReport = dao.getCompetitionsReport();

        return competitionReport;
    }

    @GET
    @Path("/getCompetitionType")
    @Produces(MediaType.APPLICATION_JSON)
    public EnumResult getCompetitionType(@QueryParam("id") String id) throws Exception {
        ReportDAO dao = new ReportDAO();
        EnumResult result = new EnumResult();

        Type type = dao.getCompetitionType(Integer.valueOf(id));
        result.setName(type.name());
        result.setDisplayName(type.getDisplayName());

        return result;
    }

    @GET
    @Path("/getCompetitionName")
    @Produces(MediaType.APPLICATION_JSON)
    public CompetitionNameDTO getCompetitionName(@QueryParam("id") String id) throws Exception {
        ReportDAO dao = new ReportDAO();

        return dao.getCompetitionName(Integer.valueOf(id));
    }

    @GET
    @Path("/getPersonName")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonName(@QueryParam("id") String id) throws Exception {
        ReportDAO dao = new ReportDAO();

        return dao.getPersonName(Integer.valueOf(id));
    }

}
