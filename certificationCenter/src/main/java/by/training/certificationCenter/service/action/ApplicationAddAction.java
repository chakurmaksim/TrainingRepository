package by.training.certificationCenter.service.action;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.dao.ApplicationDAO;
import by.training.certificationCenter.dao.ProductDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationAddAction {
    public boolean insertAppToDatabase(final Application app, Connection connection)
            throws SQLException {
        // Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        ApplicationDAO appDao = new ApplicationDAO(connection);
        ProductDAO prodDao = new ProductDAO(connection, app.getId());
        if (appDao.create(app)) {
            for (Product product : app.getProducts()) {
                if (!prodDao.create(product)) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        }
        connection.rollback();
        return false;
    }
}
