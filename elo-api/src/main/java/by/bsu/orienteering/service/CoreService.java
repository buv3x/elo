package by.bsu.orienteering.service;

import by.bsu.orienteering.model.Gender;
import by.bsu.orienteering.model.Level;
import by.bsu.orienteering.model.Source;
import by.bsu.orienteering.model.EnumResult;
import by.bsu.orienteering.model.Type;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.memelov on 22-Dec-18.
 */
@Path("/core")
public class CoreService {

    @GET
    @Path("/getGenders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumResult> getGenders() {
        List<EnumResult> result = new ArrayList<>();
        for (Gender source : Gender.values()) {
            EnumResult sourceResult = new EnumResult();
            sourceResult.setName(source.name());
            sourceResult.setDisplayName(source.getDisplayName());
            result.add(sourceResult);
        }
        return result;
    }

    @GET
    @Path("/getTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumResult> getTypes() {
        List<EnumResult> result = new ArrayList<>();
        for (Type source : Type.values()) {
            EnumResult sourceResult = new EnumResult();
            sourceResult.setName(source.name());
            sourceResult.setDisplayName(source.getDisplayName());
            result.add(sourceResult);
        }
        return result;
    }

    @GET
    @Path("/getLevels")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumResult> getLevels() {
        List<EnumResult> result = new ArrayList<>();
        for (Level source : Level.values()) {
            EnumResult sourceResult = new EnumResult();
            sourceResult.setName(source.name());
            sourceResult.setDisplayName(source.getDisplayName());
            result.add(sourceResult);
        }
        return result;
    }

}
