package by.bsu.orienteering.dao;

import by.bsu.orienteering.model.Person;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alexey.memelov on 21-Mar-19.
 */
public class DataTransferDAO {

    protected static final Logger logger = Logger.getLogger(DataTransferDAO.class.getName());

    private static String SQL_SELECT_COMPETITION =
            "SELECT name, date, type, level, gender, id FROM competition";

    private static String SQL_INSERT_COMPETITION =
            "INSERT INTO competition (name, date, type, level, gender, id) VALUES (?, ? ,?, ?, ?, ?)";

    private static String SQL_SELECT_PERSON =
            "SELECT name, surname, gender, id FROM person";

    private static String SQL_INSERT_PERSON =
            "INSERT INTO person (name, surname, gender, id) VALUES (?, ? ,?, ?)";

    private static String SQL_SELECT_RESULT =
            "SELECT person, competition, result, id FROM result";

    private static String SQL_INSERT_RESULT =
            "INSERT INTO result (person, competition, result, id) VALUES (?, ? ,?, ?)";


    public void transferPerson() throws Exception {
        InitialContext cxt = new InitialContext();
        if ( cxt == null ) {
            throw new Exception("No context!");
        }
        DataSource mysql = (DataSource) cxt.lookup("java:/comp/env/jdbc/mysql");
        DataSource postgres = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");

        try (Connection con = mysql.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_SELECT_RESULT)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                try(Connection conP = postgres.getConnection();
                    PreparedStatement statementI = conP.prepareStatement(SQL_INSERT_RESULT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    statementI.setInt(1, resultSet.getInt(1));
                    statementI.setInt(2, resultSet.getInt(2));
                    statementI.setInt(3, resultSet.getInt(3));
                    statementI.setInt(4, resultSet.getInt(4));
                    statementI.executeUpdate();
                }   catch (SQLException ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }



}
