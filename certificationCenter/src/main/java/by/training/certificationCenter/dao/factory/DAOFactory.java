package by.training.certificationCenter.dao.factory;

import by.training.certificationCenter.dao.CertificationDAO;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.impl.ApplicationDAO;
import by.training.certificationCenter.dao.impl.OrganisationDAO;
import by.training.certificationCenter.dao.impl.ProductDAO;
import by.training.certificationCenter.dao.impl.UserDAO;
import by.training.certificationCenter.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {
    private static final String CONNECTION_ERROR;
    private static final String CONNECTION_CLOSE_ERROR;
    private static final String AUTO_COMMIT_ERROR;
    private static final String ROLL_BACK_ERROR;
    private static final String COMMIT_OPERATION_ERROR;
    private static final String SET_TRANSACTION_ERROR;
    private static final String GET_TRANSACTION_ERROR;
    static {
        CONNECTION_ERROR = "Database connection error";
        CONNECTION_CLOSE_ERROR = "Connection close error";
        AUTO_COMMIT_ERROR = "Can not change auto commit flag";
        ROLL_BACK_ERROR = "Can not roll back operation";
        COMMIT_OPERATION_ERROR = "Can not commit operation";
        SET_TRANSACTION_ERROR = "Can not set transaction level because the "
                + "given parameter is not one of the Connection or method is "
                + "called on a closed connection";
        GET_TRANSACTION_ERROR = "It is not be able to get transaction level "
                + "because the method is called on a closed connection";
    }
    private DAOFactory() {
    }

    private static class DAOFactoryHolder {
        private static final DAOFactory
                SINGLE_INSTANCE = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return DAOFactoryHolder.SINGLE_INSTANCE;
    }

    public ApplicationDAO getApplicationDAO() throws DAOException {
        try {
            Connection connection = ConnectionPool.getConnection();
            return new ApplicationDAO(connection);
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }
    }

    public OrganisationDAO getOrganisationDAO() throws DAOException {
        try {
            Connection connection = ConnectionPool.getConnection();
            return new OrganisationDAO(connection);
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }
    }

    public ProductDAO getProductDAO() throws DAOException {
        try {
            Connection connection = ConnectionPool.getConnection();
            return new ProductDAO(connection);
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }
    }

    public UserDAO getUserDAO() throws DAOException {
        try {
            Connection connection = ConnectionPool.getConnection();
            return new UserDAO(connection);
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }
    }

    public boolean closeCertificationDAO(final CertificationDAO dao)
            throws DAOException {
        if (dao instanceof CertificationMySqlDAO) {
            CertificationMySqlDAO mySqlDAO = (CertificationMySqlDAO) dao;
            try {
                ConnectionPool.close(mySqlDAO.getConnection());
                return true;
            } catch (SQLException e) {
                throw new DAOException(CONNECTION_CLOSE_ERROR, e);
            }
        }
        return false;
    }

    public void setAutoCommit(
            final CertificationDAO dao, boolean autoCommit)
            throws DAOException {
        if (dao instanceof CertificationMySqlDAO) {
            CertificationMySqlDAO mySqlDAO = (CertificationMySqlDAO) dao;
            try {
                mySqlDAO.getConnection().setAutoCommit(autoCommit);
            } catch (SQLException e) {
                throw new DAOException(AUTO_COMMIT_ERROR, e);
            }
        }
    }

    public void setTransactionIsolationLevel(
            final CertificationDAO dao, int level) throws DAOException {
        if (dao instanceof CertificationMySqlDAO) {
            CertificationMySqlDAO mySqlDAO = (CertificationMySqlDAO) dao;
            try {
                mySqlDAO.getConnection().setTransactionIsolation(level);
            } catch (SQLException e) {
                throw new DAOException(SET_TRANSACTION_ERROR, e);
            }
        }
    }

    public int getTransactionIsolationLevel(final CertificationDAO dao)
            throws DAOException {
        int level = 0;
        if (dao instanceof CertificationMySqlDAO) {
            CertificationMySqlDAO mySqlDAO = (CertificationMySqlDAO) dao;
            try {
                level = mySqlDAO.getConnection().getTransactionIsolation();
            } catch (SQLException e) {
                throw new DAOException(GET_TRANSACTION_ERROR, e);
            }
        }
        return level;
    }

    public void rollbackOperation(final CertificationDAO dao)
            throws DAOException {
        if (dao instanceof CertificationMySqlDAO) {
            CertificationMySqlDAO mySqlDAO = (CertificationMySqlDAO) dao;
            try {
                mySqlDAO.getConnection().rollback();
            } catch (SQLException e) {
                throw new DAOException(ROLL_BACK_ERROR, e);
            }
        }
    }

    public void commitOperation(final CertificationDAO dao)
            throws DAOException {
        if (dao instanceof CertificationMySqlDAO) {
            CertificationMySqlDAO mySqlDAO = (CertificationMySqlDAO) dao;
            try {
                mySqlDAO.getConnection().commit();
            } catch (SQLException e) {
                throw new DAOException(COMMIT_OPERATION_ERROR, e);
            }
        }
    }
}
