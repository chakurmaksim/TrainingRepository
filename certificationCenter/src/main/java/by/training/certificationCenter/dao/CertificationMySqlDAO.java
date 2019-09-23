package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.CertificationEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public abstract class CertificationMySqlDAO<T extends CertificationEntity> implements CertificationDAO<T> {
    private Connection connection;
    private Logger logger;
    public CertificationMySqlDAO(final Connection newConnection) {
        this.connection = newConnection;
        logger = LogManager.getLogger();
    }

    public Connection getConnection() {
        return connection;
    }

    public Logger getLogger() {
        return logger;
    }
}
