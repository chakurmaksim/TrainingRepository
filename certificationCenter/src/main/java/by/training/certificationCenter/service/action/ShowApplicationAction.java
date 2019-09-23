package by.training.certificationCenter.service.action;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowApplicationAction {
    public Application findApplicationById(final int applicationId, final User user)
            throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        ApplicationDAO applicationDAO = new ApplicationDAO(connection);
        Application application = applicationDAO.findEntityById(applicationId);
        if (application != null) {
            ProductDAO productDAO = new ProductDAO(connection, applicationId);
            List<Product> products = productDAO.findAll();
            application.setProducts(products);
            if (application.getExecutor().getId() == user.getId()) {
                application.setExecutor(user);
                application.setOrg(user.getOrg());
            } else {
                UserDAO userDAO = new UserDAO(connection);
                User appUser = userDAO.findEntityById(application.getExecutor().getId());
                if (appUser != null) {
                    OrganisationDAO organisationDAO = new OrganisationDAO(connection);
                    Organisation org = organisationDAO.findEntityById(appUser.getOrg().getId());
                    appUser.setOrg(org);
                    application.setExecutor(appUser);
                    application.setOrg(org);
                }
            }
        }
        return application;
    }
}
