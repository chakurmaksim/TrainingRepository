package by.training.certificationCenter.dao.pool;

import by.training.certificationCenter.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public final class TomcatConnectionPoolWrapper extends ConnectionPoolWrapper {
    private TomcatConnectionPoolWrapper() {
    }

    private static class ConnectionWrapperHolder {
        /**
         * Variable that holds the ConnectionWrapper single instance.
         */
        private static final TomcatConnectionPoolWrapper
                SINGLE_INSTANCE = new TomcatConnectionPoolWrapper();
    }

    public static TomcatConnectionPoolWrapper getInstance() {
        return ConnectionWrapperHolder.SINGLE_INSTANCE;
    }

    @Override
    public void initialPool() {
        TomcatConnectionPool.initialPool();
    }

    @Override
    public void closePool() {
        TomcatConnectionPool.closePool();
    }

    @Override
    public Connection getConnection() throws DAOException {
        try {
            return TomcatConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new DAOException(getConnectionGetError(), e);
        }
    }

    @Override
    public void closeConnection(final Connection connection)
            throws DAOException {
        try {
            TomcatConnectionPool.close(connection);
        } catch (SQLException e) {
            throw new DAOException(getConnectionCloseError(), e);
        }
    }
}
