package by.training.certificationCenter.service.action;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.ConnectionPool;
import by.training.certificationCenter.dao.OrganisationDAO;
import by.training.certificationCenter.dao.UserDAO;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.UserByLoginSpecification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserFindAction {
    public User findByLoginAndPassword(
            final String login, final String password)
            throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        Specification userByLogin = new UserByLoginSpecification(login, password);
        UserDAO userDAO = new UserDAO(connection);
        List<User> userList = userDAO.query(userByLogin);
        User user = null;
        if (userList.size() > 0) {
            user = userList.get(0);
            OrganisationDAO orgDAO = new OrganisationDAO(connection);
            int orgID = user.getOrg().getId();
            Organisation org = orgDAO.findEntityById(orgID);
            user.setOrg(org);
        }
        return user;
    }
}
