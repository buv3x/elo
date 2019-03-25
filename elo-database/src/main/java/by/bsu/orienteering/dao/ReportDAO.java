package by.bsu.orienteering.dao;

import by.bsu.orienteering.model.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 08-Jan-19.
 */
public class ReportDAO extends AbstractDAO {

    protected static final Logger logger = Logger.getLogger(ReportDAO.class.getName());

//    MySQL
//    private static String SQL_SELECT_ALL_REPORT =
//            "SELECT p.id, CONCAT(p.surname,' ',p.name), cl.rating, cl.total_factor, l2.change_rating, l2.factor " +
//                    "FROM calculation cl " +
//                    "INNER JOIN result rl ON cl.result = rl.id " +
//                    "INNER JOIN person p ON rl.person = p.id " +
//                    "INNER JOIN competition c ON rl.competition = c.id " +
//                    "LEFT JOIN (SELECT p.id person, COUNT(cla.id) lastYearStarts, SUM(cla.change_rating) change_rating, SUM(cla.factor) factor " +
//                    "FROM person p " +
//                    "LEFT JOIN result rla ON rla.person = p.id " +
//                    "INNER JOIN calculation cla ON cla.result = rla.id " +
//                    "INNER JOIN competition cc ON cc.id = rla.competition " +
//                    "WHERE p.gender = ? " +
//                    "AND (cc.date BETWEEN ? AND ?) and cla.calculator = 0 and cla.type %s " +
//                    "GROUP BY p.id) l2 on l2.person = p.id " +
//                    "WHERE cl.calculator = 0 AND cl.type %s AND p.gender = ? AND l2.factor >= ? " +
//                    "AND cl.id = " +
//                    "(SELECT cl2.id " +
//                    "FROM calculation cl2 " +
//                    "INNER JOIN result rl2 ON cl2.result = rl2.id " +
//                    "INNER JOIN competition c2 ON rl2.competition = c2.id " +
//                    "WHERE rl2.person = rl.person AND cl2.calculator = 0 AND cl2.type %s " +
//                    "ORDER BY c2.date desc, rl2.competition desc " +
//                    "LIMIT 1) " +
//                    "order by cl.rating desc";

    private static String SQL_SELECT_ALL_REPORT =
            "SELECT p.id, CONCAT(p.surname,' ',p.name), l.rating, l.total_factor, l2.change_rating, l2.change_factor " +
                    "FROM person p " +
                    "LEFT JOIN (SELECT p.id person, COUNT(cla.id) lastYearStarts, SUM(cla.change_rating) change_rating, SUM(cla.factor) change_factor " +
                    "FROM person p " +
                    "LEFT JOIN result rla ON rla.person = p.id " +
                    "INNER JOIN calculation cla ON cla.result = rla.id " +
                    "INNER JOIN competition cc ON cc.id = rla.competition " +
                    "WHERE (cc.date BETWEEN ? AND ?) and cla.calculator = 0 and cla.type %s " +
                    "GROUP BY p.id) l2 on l2.person = p.id " +
                    "LEFT JOIN (SELECT distinct on (r.person) r.person, cl.rating, cl.total_factor " +
                    "FROM calculation cl " +
                    "INNER JOIN result r on r.id = cl.result " +
                    "INNER JOIN competition c ON c.id = r.competition " +
                    "WHERE cl.calculator = 0 AND cl.type %s " +
                    "ORDER BY r.person, cl.calculator, cl.type, c.date desc, r.competition desc) l ON l.person = p.id " +
                    "WHERE l2.change_factor >= ? AND p.gender = ? " +
                    "order by l.rating desc";

    private static String SQL_SELECT_PERSON_GRAPH_REPORT =
            "SELECT c.date, l.rating" +
                    "FROM calculation l " +
                    "INNER JOIN result r ON l.result = r.id " +
                    "INNER JOIN person p ON r.person = p.id " +
                    "INNER JOIN competition c ON c.id = r.competition " +
                    "WHERE p.id = ? and l.calculator = 0 and l.type %s " +
                    "ORDER BY c.date, c.id ";

    private static String SQL_SELECT_PERSON_REPORT =
            "SELECT c.id, c.date, c.name, c.type, l.rating, l.change_rating, l.total_factor, l.factor, r.result, c1.person_count " +
                    "FROM calculation l " +
                    "INNER JOIN result r ON l.result = r.id " +
                    "INNER JOIN person p ON r.person = p.id " +
                    "INNER JOIN competition c ON c.id = r.competition " +
                    "INNER JOIN (SELECT r1.competition, COUNT(r1.id) person_count FROM result r1 GROUP BY r1.competition) c1 ON c.id = c1.competition " +
                    "WHERE p.id = ? and l.calculator = 0 and l.type %s " +
                    "ORDER BY c.date desc, c.id desc ";

    private static String SQL_SELECT_PERSON_NAME =
            "SELECT CONCAT(P.surname,' ',p.name) FROM person p WHERE p.id = ?";

    public List<AllPersonReportDTO> getAllPersonReport(Gender gender, Type type) throws Exception {
        DataSource ds = getDatasource();
        List<AllPersonReportDTO> result = new ArrayList<>();
        String typeSQL = type != null ? " = ?" : "IS NULL";
        Calendar calendar = Calendar.getInstance();
        try (Connection con = ds.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     String.format(SQL_SELECT_ALL_REPORT, typeSQL, typeSQL, typeSQL))) {
            if(type != null) {
                statement.setDate(2, new Date(calendar.getTime().getTime()));
                calendar.add(Calendar.YEAR, -1);
                statement.setDate(1, new Date(calendar.getTime().getTime()));
                statement.setInt(3, type.ordinal());
                statement.setInt(4, type.ordinal());
                statement.setInt(5, 40);
                statement.setInt(6, gender.ordinal());
            } else {
                statement.setDate(2, new Date(calendar.getTime().getTime()));
                calendar.add(Calendar.YEAR, -1);
                statement.setDate(1, new Date(calendar.getTime().getTime()));
                statement.setInt(3, 80);
                statement.setInt(4, gender.ordinal());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AllPersonReportDTO dto = new AllPersonReportDTO();
                dto.setId(resultSet.getInt(1));
                dto.setDisplayName(resultSet.getString(2));
                dto.setRating(resultSet.getBigDecimal(3).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                dto.setFactor(resultSet.getInt(4));
                dto.setLastYearChange(resultSet.getBigDecimal(5).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                dto.setLastYearFactor(resultSet.getInt(6));
                result.add(dto);
            }
        } catch (SQLException ex) {
            logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public List<PersonGraphReportDTO> getPersonGraphReport(Integer id, Type type) throws Exception {
        DataSource ds = getDatasource();
        List<PersonGraphReportDTO> result = new ArrayList<>();
        String typeSQL = type != null ? " = ?" : "IS NULL";
        try (Connection con = ds.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     String.format(SQL_SELECT_PERSON_GRAPH_REPORT, typeSQL))) {
            statement.setInt(1, id);
            if(type != null) {
                statement.setInt(2, type.ordinal());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PersonGraphReportDTO dto = new PersonGraphReportDTO();
                dto.setDate(resultSet.getDate(1));
                dto.setRating(resultSet.getBigDecimal(2).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                result.add(dto);
            }
        } catch (SQLException ex) {
            logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public List<PersonReportDTO> getPersonReport(Integer id, Type type) throws Exception {
        DataSource ds = getDatasource();
        List<PersonReportDTO> result = new ArrayList<>();
        String typeSQL = type != null ? " = ?" : "IS NULL";
        try (Connection con = ds.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     String.format(SQL_SELECT_PERSON_REPORT, typeSQL))) {
            statement.setInt(1, id);
            if(type != null) {
                statement.setInt(2, type.ordinal());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PersonReportDTO dto = new PersonReportDTO();
                dto.setId(resultSet.getInt(1));
                dto.setDate(resultSet.getDate(2));
                dto.setName(resultSet.getString(3));
                dto.setType(Type.values()[resultSet.getInt(4)].getDisplayName());
                dto.setRating(resultSet.getBigDecimal(5).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                dto.setRatingChange(resultSet.getBigDecimal(6).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                dto.setFactor(resultSet.getInt(7));
                dto.setFactorChange(resultSet.getInt(8));
                dto.setPlace(resultSet.getInt(7) != 0 ? resultSet.getInt(7) : resultSet.getInt(8));
                dto.setPlace(resultSet.getInt(8));
                result.add(dto);
            }
        } catch (SQLException ex) {
            logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public String getPersonName(Integer id) throws Exception {
        DataSource ds = getDatasource();
        try (Connection con = ds.getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SELECT_PERSON_NAME)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException ex) {
            logger.log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }

        return "";
    }

}
