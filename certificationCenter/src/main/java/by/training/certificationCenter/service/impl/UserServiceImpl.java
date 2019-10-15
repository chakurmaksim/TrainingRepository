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
import by.training.certificationCenter.service.validator.UserValidator;
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
        boolean flag = false;
        if (!UserValidator.validateUNP(user.getOrganisation().getUnp())) {
            throw new ServiceException("Identification number of the "
                    + "organisation has to contain 9 digits");
        }
        if (!UserValidator.validateEmail(user.getMail()) ||
                !UserValidator.validateEmail(user.getOrganisation().
                        getEmail())) {
            throw new ServiceException("Organisation or executor's email "
                    + "address are not correct");
        }
        if (!UserValidator.validatePhoneNumber(user.getPhone())
                || !UserValidator.validatePhoneNumber(user.getOrganisation().
                getPhoneNumber())) {
            throw new ServiceException("Organisation or executor's phone "
                    + "number are incorrect");
        }
        if (!UserValidator.validatePassword(user.getPassword())) {
            throw new ServiceException("Password is not strong enough");
        }
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = null;
        int isolationLevel = 0;
        try {
            userDAO = factory.getUserDAO(wrapper.getConnection());
            wrapper.setAutoCommit(userDAO.getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    userDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(userDAO.getConnection());
            OrganisationDAO organisationDAO = factory.getOrganisationDAO(
                    userDAO.getConnection());
            int organisationId = organisationDAO.create(user.getOrganisation());
            if (organisationId > 0) {
                user.getOrganisation().setId(organisationId);
                try {
                    int userId = userDAO.create(user);
                    user.setId(userId);
                    wrapper.commitOperation(userDAO.getConnection());
                    flag = true;
                } catch (DAOException e) {
                    wrapper.rollbackOperation(userDAO.getConnection());
                    throw new ServiceException(e.getMessage(), e);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            if (userDAO != null) {
                try {
                    if (isolationLevel != 0) {
                        wrapper.setTransactionIsolationLevel(
                                userDAO.getConnection(),
                                isolationLevel);
                    }
                    wrapper.setAutoCommit(userDAO.getConnection(),
                            true);
                    wrapper.closeConnection(userDAO.getConnection());
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return flag;
    }
}
