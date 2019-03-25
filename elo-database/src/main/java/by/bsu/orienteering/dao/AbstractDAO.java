package by.bsu.orienteering.dao;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Created by alexey.memelov on 03-Jan-19.
 */
public abstract class AbstractDAO {

    protected DataSource getDatasource() throws Exception {
        InitialContext cxt = new InitialContext();
        if ( cxt == null ) {
            throw new Exception("No context!");
        }
        return (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );
    }

}
