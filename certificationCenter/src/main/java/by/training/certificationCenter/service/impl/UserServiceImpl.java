package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.impl.OrganisationDAO;
import by.training.certificationCenter.dao.impl.UserDAO;
import by.training.certificationCenter.dao.pool.ConnectionWrapper;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.UserByLoginSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService<User> {
    /**
     * Events logger, in that case to register possible exceptions when
     * connection is being closed.
     */
    private static Logger logger = LogManager.getLogger(
            ApplicationServiceImpl.class);

    @Override
    public User signIn(final String login, final String password)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        UserDAO userDAO = null;
        try {
            userDAO = factory.getUserDAO(wrapper.getConnection());
            Specification userByLogin = new UserByLoginSpecification(
                    login, password);
            List<User> userList = userDAO.query(userByLogin);
            User user = null;
            if (userList.size() > 0) {
                user = userList.remove(0);
            }
            if (user == null) {
                String message = String.format("User login \"%s\" or password "
                        + "are not recognized", login);
                throw new ServiceException(message);
            } else {
                OrganisationDAO organisationDAO = factory.
                        getOrganisationDAO(userDAO.getConnection());
                user.setOrganisation(organisationDAO.findEntityById(
                        user.getOrganisation().getId()));
            }
            if (user.isActual() && user.getOrganisation().isAccepted()) {
                return user;
            } else {
                String message = String.format("User login \"%s\" or "
                        + "organisation name \"%s\" are not valid",
                        user.getLogin(), user.getOrganisation().getName());
                throw new ServiceException(message);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            if (userDAO != null) {
                try {
                    wrapper.closeConnection(userDAO.getConnection());
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public User receiveUserById(final int userId)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        UserDAO userDAO = null;
        try {
            userDAO = factory.getUserDAO(wrapper.getConnection());
            User user = userDAO.findEntityById(userId);
            if (user != null) {
                OrganisationDAO organisationDAO = factory.getOrganisationDAO(
                        userDAO.getConnection());
                user.setOrganisation(organisationDAO.findEntityById(
                        user.getOrganisation().getId()));
                return user;
            } else {
                String message = String.format("User with id number "
                        + "\"%d\" does not exist", userId);
                throw new ServiceException(message);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            if (userDAO != null) {
                try {
                    wrapper.closeConnection(userDAO.getConnection());
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public boolean registration(final User user)
            throws ServiceException {
        return false;
    }
}
