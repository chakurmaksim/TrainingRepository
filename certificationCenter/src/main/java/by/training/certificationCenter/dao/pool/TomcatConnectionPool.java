package by.training.certificationCenter.dao.pool;

import by.training.certificationCenter.service.configuration.DatabaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class TomcatConnectionPool {
    private static Context envContext;
    /**
     * Component that provides a connection to a DBMS application.
     */
    private static DataSource dataSource;
    /**
     * Events logger, in that case to register possible exceptions when
     * creating an instance of the DataSource.
     */
    private static Logger logger = LogManager.getLogger(
            TomcatConnectionPool.class);

    private TomcatConnectionPool() {
    }

    static void initialPool() {
        try {
            Properties properties = DatabaseConfiguration.getProperties();
            InitialContext initContext = new InitialContext();
            envContext = (Context) initContext.lookup(
                    properties.getProperty("envContextName"));
            dataSource = (DataSource) envContext.lookup(
                    properties.getProperty("dataSourceName"));
            logger.debug("Application is started");
        } catch (NamingException e) {
            logger.error(e);
        }
    }

    static void closePool() {
        try {
            logger.debug("Application is stopped");
            envContext.close();
        } catch (NamingException e) {
            logger.error(e);
        }
    }

    static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    static void close(final Connection connection)
            throws SQLException {
        connection.close();
    }
}
