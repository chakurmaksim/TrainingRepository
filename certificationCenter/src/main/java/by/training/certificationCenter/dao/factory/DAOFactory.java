package by.training.certificationCenter.dao.factory;

import by.training.certificationCenter.dao.impl.*;

import java.sql.Connection;

public final class DAOFactory {

    private DAOFactory() {
    }

    private static class DAOFactoryHolder {
        /**
         * Variable that holds the DAOFactory single instance.
         */
        private static final DAOFactory
                SINGLE_INSTANCE = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return DAOFactoryHolder.SINGLE_INSTANCE;
    }

    public ApplicationDAO getApplicationDAO(final Connection connection) {
        return new ApplicationDAO(connection);
    }

    public OrganisationDAO getOrganisationDAO(final Connection connection) {
        return new OrganisationDAO(connection);
    }

    public ProductDAO getProductDAO(final Connection connection) {
        return new ProductDAO(connection);
    }

    public UserDAO getUserDAO(final Connection connection) {
        return new UserDAO(connection);
    }

    public DocumentDAO getDocumentDAO(final Connection connection) {
        return new DocumentDAO(connection);
    }
}
