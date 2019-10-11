package by.training.certificationCenter.dao.pool;

import by.training.certificationCenter.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionWrapper {
    /**
     * Variable that keeps error message when database access error occurs.
     */
    private static final String CONNECTION_GET_ERROR;
    /**
     * Variable that keeps error message when connection can not be returned
     * in to the connection pool.
     */
    private static final String CONNECTION_CLOSE_ERROR;
    /**
     * Variable that keeps error message when the setAutoCommit method is
     * disabled because the connection is already closed or database access
     * error has occurred.
     */
    private static final String AUTO_COMMIT_ERROR;
    /**
     * Variable that keeps error message when the setTransactionIsolation method
     * is disabled because the connection is already closed or the got
     * parameter has not been valid.
     */
    private static final String SET_TRANSACTION_ERROR;
    /**
     * Variable that keeps error message when the getTransactionIsolation method
     * is disabled because the connection is already closed.
     */
    private static final String GET_TRANSACTION_ERROR;
    /**
     * Variable that keeps error message when the rollback method
     * is disabled because the connection is already closed or the connection
     * has been in auto-commit mode.
     */
    private static final String ROLL_BACK_ERROR;
    /**
     * Variable that keeps error message when the commit method
     * is disabled because the connection is already closed or the connection
     * has been in auto-commit mode.
     */
    private static final String COMMIT_OPERATION_ERROR;

    static {
        CONNECTION_GET_ERROR = "Database access error";
        CONNECTION_CLOSE_ERROR = "Connection close error because the "
                + "connection is already closed or database access error "
                + "has occurred";
        AUTO_COMMIT_ERROR = "Can not change auto commit flag";
        SET_TRANSACTION_ERROR = "Can not set transaction level because the "
                + "given parameter is not one of the Connection or method is "
                + "called on a closed connection";
        GET_TRANSACTION_ERROR = "It is not be able to get transaction level "
                + "because the method is called on a closed connection";
        ROLL_BACK_ERROR = "Can not roll back operation because the method is "
                + "called on a closed connection or Connection object is in "
                + "auto-commit mode";
        COMMIT_OPERATION_ERROR = "Can not commit operation because the method "
                + "is called on a closed connection or Connection object is "
                + "in auto-commit mode";
    }

    private ConnectionWrapper() {
    }

    private static class ConnectionWrapperHolder {
        /**
         * Variable that holds the ConnectionWrapper single instance.
         */
        private static final ConnectionWrapper
                SINGLE_INSTANCE = new ConnectionWrapper();
    }

    public static ConnectionWrapper getInstance() {
        return ConnectionWrapperHolder.SINGLE_INSTANCE;
    }

    public Connection getConnection() throws DAOException {
        try {
            return ConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_GET_ERROR, e);
        }
    }

    public void closeConnection(final Connection connection)
            throws DAOException {
        try {
            ConnectionPool.close(connection);
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_CLOSE_ERROR, e);
        }
    }

    public void setAutoCommit(final Connection connection, boolean autoCommit)
            throws DAOException {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new DAOException(AUTO_COMMIT_ERROR, e);
        }
    }

    public void setTransactionIsolationLevel(
            final Connection connection, int level) throws DAOException {
        try {
            connection.setTransactionIsolation(level);
        } catch (SQLException e) {
            throw new DAOException(SET_TRANSACTION_ERROR, e);
        }
    }

    public void setTransactionReadUncommittedLevel(
            final Connection connection) throws DAOException {
        try {
            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException e) {
            throw new DAOException(SET_TRANSACTION_ERROR, e);
        }
    }

    public int getTransactionIsolationLevel(final Connection connection)
            throws DAOException {
        try {
            return connection.getTransactionIsolation();
        } catch (SQLException e) {
            throw new DAOException(GET_TRANSACTION_ERROR, e);
        }
    }

    public void rollbackOperation(final Connection connection)
            throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException(ROLL_BACK_ERROR, e);
        }
    }

    public void commitOperation(final Connection connection)
            throws DAOException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(COMMIT_OPERATION_ERROR, e);
        }
    }
}
