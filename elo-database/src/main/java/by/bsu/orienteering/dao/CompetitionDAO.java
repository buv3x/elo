package by.bsu.orienteering.dao;

import by.bsu.orienteering.model.Competition;
import by.bsu.orienteering.model.Gender;
import by.bsu.orienteering.model.Person;
import by.bsu.orienteering.model.Result;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 26-Dec-18.
 */
public class CompetitionDAO extends AbstractDAO {

    protected static final Logger logger = Logger.getLogger(CompetitionDAO.class.getName());

    private static String SQL_INSERT_COMPETITION =
            "INSERT INTO competition (name, date, type, level, gender) VALUES (?, ? ,?, ?, ?)";

    private static String SQL_INSERT_PERSON =
            "INSERT INTO person (name, surname, gender) VALUES (?, ? ,?)";

    private static String SQL_INSERT_RESULT =
            "INSERT INTO result (person, competition, result) VALUES (?, ? ,?)";

    private static String SQL_SELECT_ALL_PERSON =
            "SELECT surname, name, id from person WHERE gender = ? ORDER BY surname";

    private static String SQL_SELECT_PERSON =
            "SELECT id from person WHERE surname = ? and name = ? and gender = ?";


    public Integer saveCompetition(Competition competition) throws Exception {
        Integer result = null;
        DataSource ds = getDatasource();
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_COMPETITION, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, competition.getName());
            statement.setDate(2, new Date(competition.getDate().getTime()));
            statement.setInt(3, competition.getType().ordinal());
            statement.setInt(4, competition.getLevel().ordinal());
            statement.setInt(5, competition.getGender().ordinal());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public Integer savePerson(Person person) throws Exception {
        Integer result = null;
        DataSource ds = getDatasource();
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_PERSON, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setInt(3, person.getGender().ordinal());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public void saveResult(Result result) throws Exception {
        DataSource ds = getDatasource();
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_RESULT)) {
            statement.setInt(1, result.getPersonId());
            statement.setInt(2, result.getCompetitionId());
            statement.setInt(3, result.getResult());
            statement.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public List<Person> getPersons(Gender gender) throws Exception {
        DataSource ds = getDatasource();
        List<Person> result = new ArrayList<>();
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_SELECT_ALL_PERSON)) {
            statement.setInt(1, gender.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Person person = new Person();
                person.setSurname(resultSet.getString(1));
                person.setName(resultSet.getString(2));
                person.setId(resultSet.getInt(3));
                result.add(person);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;
    }


    public Integer getPerson(String surname, String name, Gender gender) throws Exception {
        DataSource ds = getDatasource();
        Integer result = null;
        try (Connection con = ds.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_SELECT_PERSON)) {
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.setInt(3, gender.ordinal());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;
    }


}




