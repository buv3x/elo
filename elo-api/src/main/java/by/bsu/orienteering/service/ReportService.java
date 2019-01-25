package by.bsu.orienteering.service;

import by.bsu.orienteering.dao.ReportDAO;
import by.bsu.orienteering.model.AllPersonReportDTO;
import by.bsu.orienteering.model.Gender;
import by.bsu.orienteering.model.PersonReportDTO;
import by.bsu.orienteering.model.Type;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by alexey.memelov on 08-Jan-19.
 */
@Path("/report")
public class ReportService {

    @GET
    @Path("/getRatingList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AllPersonReportDTO> getPersons(@QueryParam("gender") String genderString, @QueryParam("type") String typeString) throws Exception {
        ReportDAO dao = new ReportDAO();
        Gender gender = genderString != null ? Gender.valueOf(genderString.toUpperCase()) : Gender.WOMEN;
        Type type = typeString != null ? Type.valueOf(typeString.toUpperCase()) : null;

        return dao.getAllPersonReport(gender, type);
    }

    @GET
    @Path("/getPersonDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonReportDTO> getPersonDetails(@QueryParam("id") String id, @QueryParam("type") String typeString) throws Exception {
        ReportDAO dao = new ReportDAO();
        Type type = typeString != null ? Type.valueOf(typeString.toUpperCase()) : null;

        return dao.getPersonReport(Integer.valueOf(id), type);
    }

    @GET
    @Path("/getPersonName")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonName(@QueryParam("id") String id) throws Exception {
        ReportDAO dao = new ReportDAO();

        return dao.getPersonName(Integer.valueOf(id));
    }

}