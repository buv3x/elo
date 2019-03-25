package by.bsu.orienteering.service;

import by.bsu.orienteering.dao.CompetitionDAO;
import by.bsu.orienteering.dao.DataTransferDAO;
import by.bsu.orienteering.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by alexey.memelov on 26-Dec-18.
 */
@Path("/data")
public class DataTransferService {

    @POST
    @Path("/transfer")
    @Produces(MediaType.APPLICATION_JSON)
    public void saveCompetition() throws Exception {
        DataTransferDAO dao = new DataTransferDAO();
        dao.transferPerson();
    }

}
