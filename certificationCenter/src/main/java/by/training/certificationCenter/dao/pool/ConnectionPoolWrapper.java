package by.training.certificationCenter.dao.pool;

import by.training.certificationCenter.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionPoolWrapper {
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

    protected String getConnectionGetError() {
        return CONNECTION_GET_ERROR;
    }

    protected String getConnectionCloseError() {
        return CONNECTION_CLOSE_ERROR;
    }

    public abstract void initialPool();

    public abstract void closePool();

    public abstract Connection getConnection() throws DAOException;

    public abstract void closeConnection(Connection connection)
            throws DAOException;

    public void setAutoCommit(Connection connection, boolean autoCommit)
            throws DAOException {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new DAOException(AUTO_COMMIT_ERROR, e);
        }
    }

    public void commitOperation(Connection connection) throws DAOException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(COMMIT_OPERATION_ERROR, e);
        }
    }

    public void rollbackOperation(Connection connection) throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException(ROLL_BACK_ERROR, e);
        }
    }

    public void setTransactionReadUncommittedLevel(Connection connection)
            throws DAOException {
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

    public void setTransactionIsolationLevel(Connection connection, int level)
            throws DAOException {
        try {
            connection.setTransactionIsolation(level);
        } catch (SQLException e) {
            throw new DAOException(SET_TRANSACTION_ERROR, e);
        }
    }
}
