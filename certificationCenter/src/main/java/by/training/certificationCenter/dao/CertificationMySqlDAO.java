package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.CertificationEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public abstract class CertificationMySqlDAO<T extends CertificationEntity>
        implements CertificationDAO<T> {
    private static final String STATEMENT_ERROR;
    private static final String COLUMN_LABEL_ERROR;
    private Connection connection;
    private Logger logger;
    static {
        STATEMENT_ERROR = "Error has occurred during creating statement";
        COLUMN_LABEL_ERROR = "Column label is not valid";
    }
    public CertificationMySqlDAO(final Connection newConnection) {
        this.connection = newConnection;
        logger = LogManager.getLogger();
    }

    public static String getStatementError() {
        return STATEMENT_ERROR;
    }

    public static String getColumnLabelError() {
        return COLUMN_LABEL_ERROR;
    }

    public Connection getConnection() {
        return connection;
    }

    public Logger getLogger() {
        return logger;
    }
}
