package by.bsu.orienteering.dao;

import by.bsu.orienteering.model.Calculation;
import by.bsu.orienteering.model.CalculationDTO;
import by.bsu.orienteering.model.Calculator;
import by.bsu.orienteering.model.Level;
import by.bsu.orienteering.model.Type;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 03-Jan-19.
 */
public class CalculationDAO extends AbstractDAO {

    protected static final Logger logger = Logger.getLogger(CalculationDAO.class.getName());

//    private static String SQL_SELECT_CALCULATIONS =
//            "SELECT c.level, c.type, c.date, r.person, r.result, COALESCE(l.rating,1500), COALESCE(l.total_factor,0) from competition c " +
//                    "LEFT JOIN result r ON r.competition = c.id " +
//                    "LEFT JOIN (SELECT distinct on (cl.person, cl.calculator, cl.type) cl.person, cl.calculator, cl.type, cl.date, cl.rating, cl.total_factor " +
//                            "FROM calculation cl WHERE cl.calculator=? AND cl.type %s ORDER BY cl.person, cl.calculator, cl.type, cl.date desc, cl.competition desc) l " +
//                        "ON l.person = r.person " +
//                    "WHERE c.id = ? ";

    private static String SQL_SELECT_CALCULATIONS =
            "SELECT ccl.level, ccl.type, ccl.date, rl.person, rl.result, COALESCE(cl.rating,1500), COALESCE(cl.total_factor,0), rl.id " +
                    "FROM competition ccl " +
                    "LEFT JOIN result rl ON ccl.id = rl.competition " +
                    "LEFT JOIN calculation cl ON cl.id = (SELECT cl2.id " +
                    "FROM calculation cl2 " +
                    "INNER JOIN result rl2 ON cl2.result = rl2.id " +
                    "INNER JOIN competition c2 ON rl2.competition = c2.id " +
                    "WHERE rl2.person = rl.person AND cl2.calculator = ? AND cl2.type %s " +
                    "ORDER BY c2.date desc, rl2.competition desc " +
                    "LIMIT 1) " +
                    "WHERE ccl.id  = ?";

    private static String SQL_INSERT_CALCULATION =
            "INSERT INTO calculation (result, rating, change_rating, calculator, total_factor, factor, type) " +
                    "VALUES(?,?,?,?,?,?,?)";

    private static String SQL_SELECT_COMPETITIONS =
            "SELECT c.id FROM competition c " +
                    "WHERE NOT EXISTS (SELECT * FROM calculation l " +
                    "INNER JOIN result r ON r.id = l.result " +
                    "WHERE r.competition = c.id AND l.calculator = ? AND l.type %s) " +
                    "%s " +
                    "order by date, id ";

    public CalculationDTO prepareCalculation(int id, Calculator calculator, Type type) throws Exception {
        DataSource ds = getDatasource();
        CalculationDTO dto = new CalculationDTO();
        String typeSelect = type != null ? " = ?" : "IS NULL";
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(String.format(SQL_SELECT_CALCULATIONS, typeSelect, typeSelect))) {
            statement.setInt(1, calculator.ordinal());
            if(type != null) {
                statement.setInt(2, type.ordinal());
                statement.setInt(3, id);
            } else {
                statement.setInt(2, id);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if(dto.getDate() == null) {
                    dto.setCalculator(calculator);
                    dto.setLevel(Level.values()[resultSet.getInt(1)]);
                    dto.setType(type);
                    dto.setDate(resultSet.getDate(3));
                }
                Calculation calculation = new Calculation();
                calculation.setPersonId(resultSet.getInt(4));
                calculation.setResult(resultSet.getInt(5));
                calculation.setPreviousRating(resultSet.getDouble(6));
                calculation.setPreviousFactor(resultSet.getInt(7));
                calculation.setResultId(resultSet.getInt(8));
                dto.getCalculations().add(calculation);
            }
        } catch (SQLException ex) {
            logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }

        return dto;
    }

    public List<Integer> getUncalculatedCompetitions(Calculator calculator, Type type) throws Exception {
        DataSource ds = getDatasource();
        List<Integer> ids = new ArrayList<>();
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    String.format(SQL_SELECT_COMPETITIONS, type != null ? " = ?" : "IS NULL", type != null ? "AND c.type = ?" : ""))) {
            statement.setInt(1, calculator.ordinal());
            if(type != null) {
                statement.setInt(2, type.ordinal());
                statement.setInt(3, type.ordinal());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }

        return ids;
    }

    public void saveCalculation(CalculationDTO calculationDTO) throws Exception {
        DataSource ds = getDatasource();
        for (Calculation calculation : calculationDTO.getCalculations()) {
            try (Connection con = ds.getConnection();
                PreparedStatement statement = con.prepareStatement(SQL_INSERT_CALCULATION)) {
                statement.setInt(1, calculation.getResultId());
                statement.setDouble(2, calculation.getNewRating());
                statement.setDouble(3, calculation.getNewRating() - calculation.getPreviousRating());
                statement.setInt(4, calculationDTO.getCalculator().ordinal());
                statement.setDouble(5, calculation.getNewFactor());
                statement.setDouble(6, calculation.getNewFactor() - calculation.getPreviousFactor());
                if(calculationDTO.getType() != null) {
                    statement.setInt(7, calculationDTO.getType().ordinal());
                } else {
                    statement.setNull(7, Types.INTEGER);
                }
                statement.execute();
            } catch (SQLException ex) {
                logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }



}
