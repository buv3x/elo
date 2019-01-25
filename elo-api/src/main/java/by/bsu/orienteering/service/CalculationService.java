package by.bsu.orienteering.service;

import by.bsu.orienteering.calculator.ICalculator;
import by.bsu.orienteering.calculator.Log4LimitNoFresh300Calculator;
import by.bsu.orienteering.dao.CalculationDAO;
import by.bsu.orienteering.model.CalculateCompetitionRequest;
import by.bsu.orienteering.model.CalculationDTO;
import by.bsu.orienteering.model.Calculator;
import by.bsu.orienteering.model.Type;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 03-Jan-19.
 */
@Path("/calculation")
public class CalculationService {

    protected static final Logger logger = Logger.getLogger(CalculationService.class.getName());

    @POST
    @Path("/calculateCompetition")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean calculateCompetition(CalculateCompetitionRequest request) throws Exception {
        logger.severe("Calculating " + request.getCompetitionId() + " " + request.getCalculator().name());
        CalculationDAO dao = new CalculationDAO();
        CalculationDTO calculation = dao.prepareCalculation(request.getCompetitionId(), request.getCalculator(), request.getType());

        ICalculator calculator = CalculatorFactory.getInstance(request.getCalculator());
        calculator.calculate(calculation);
        dao.saveCalculation(calculation);
        logger.severe(String.format("Calculated: %d. Calculator: %s. Type: %s.",
                request.getCompetitionId(),
                request.getCalculator().name(),
                request.getType() != null ? request.getType().name() : "ALL"));
        return true;
    }

    @POST
    @Path("/calculateAllCompetitions")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean calculateAllCompetitions() throws Exception {
        CalculationDAO dao = new CalculationDAO();
        for (Calculator calculator : Calculator.values()) {
            calculateCompetitions(dao, calculator, null);
            for (Type type : Type.values()) {
                calculateCompetitions(dao, calculator, type);
            }
        }
        return true;
    }

    private void calculateCompetitions(CalculationDAO dao, Calculator calculator, Type type) throws Exception {
        logger.severe("Getting uncalculated competitions");
        List<Integer> uncalculatedCompetitions = dao.getUncalculatedCompetitions(calculator, type);
        logger.severe("Size: " + uncalculatedCompetitions.size());
        for (Integer competitionId : uncalculatedCompetitions) {
            CalculateCompetitionRequest request = new CalculateCompetitionRequest();
            request.setCompetitionId(competitionId);
            request.setCalculator(calculator);
            request.setType(type);
            calculateCompetition(request);
        }
    }

}
