package mil.dfas.persistence.jdbc.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    protected static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);

    //TODO: use DFASUtil when I get the code for it
    //DFASUtil.getDatabaseConnectionFromInitialContext("jdbc/accountCreator");
    DataSource dataSource;

    public DatabaseManager(DataSource dataSource) {

        this.dataSource = dataSource;

    }

    public <T> T execute(DatabaseCall<T> call){

        Connection connection = null;
        try {

            connection = dataSource.getConnection();
            return call.withConnection(connection);

        } catch (SQLException e) {

            log.error("Unable to complete call to database.", e);
            return null;

        } finally {
            if(connection != null){

                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Unable to close connection?!", e);
                }

            }
        }
    }
}
