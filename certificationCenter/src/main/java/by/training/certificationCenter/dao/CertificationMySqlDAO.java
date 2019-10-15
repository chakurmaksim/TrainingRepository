package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.CertificationEntity;

import java.sql.Connection;

public abstract class CertificationMySqlDAO<T extends CertificationEntity>
        implements CertificationDAO<T> {
    /**
     * Variable that keeps error message when there is some problems while
     * creating the Statement object.
     */
    private static final String STATEMENT_ERROR;
    /**
     * Variable that keeps error message when there is some problems while
     * getting values from the ResultSet object.
     */
    private static final String COLUMN_LABEL_ERROR;
    /**
     * Variable that holds current connection to the database.
     */
    private Connection connection;
    static {
        STATEMENT_ERROR = "Error has occurred while creating the Statement "
                + "object at the %s because database access error has occurred "
                + "or the given SQL statement has produced anything other than "
                + "a single ResultSet object or such object has already "
                + "been existed";
        COLUMN_LABEL_ERROR = "Column label in the ResultSet object at the %s "
                + "is not valid or database access error has occurred";
    }
    public CertificationMySqlDAO(final Connection newConnection) {
        this.connection = newConnection;
    }

    public static String getStatementError(final String className) {
        return String.format(STATEMENT_ERROR, className);
    }

    public static String getColumnLabelError(final String className) {
        return String.format(COLUMN_LABEL_ERROR, className);
    }

    public Connection getConnection() {
        return connection;
    }
}
