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

public final class ConnectionPool {
    /**
     * Component that provides a connection to a DBMS application.
     */
    private static DataSource dataSource;
    /**
     * Events logger, in that case to register possible exceptions when
     * creating an instance of the DataSource.
     */
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    static {
        try {
            Properties prop = DatabaseConfiguration.getProperties();
            InitialContext initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(
                    prop.getProperty("envContextName"));
            dataSource = (DataSource) envContext.lookup(
                    prop.getProperty("dataSourceName"));
        } catch (NamingException e) {
            logger.error(e.toString());
        }
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(final Connection connection)
            throws SQLException {
        connection.close();
    }
}