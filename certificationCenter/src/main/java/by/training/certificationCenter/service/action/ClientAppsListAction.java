package by.training.certificationCenter.service.action;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.dao.ApplicationDAO;
import by.training.certificationCenter.dao.ConnectionPool;
import by.training.certificationCenter.service.specification.AppsByUserIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientAppsListAction {
    public List<Application> supplyAppsByUserId(
            final int userId, final int page, final int limit)
            throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        Specification appByUserIdSpec = new AppsByUserIdSpecification(userId, page, limit);
        ApplicationDAO appDao = new ApplicationDAO(connection);
        List<Application> apps = appDao.query(appByUserIdSpec);
        ConnectionPool.close(connection);
        return apps;
    }
}
