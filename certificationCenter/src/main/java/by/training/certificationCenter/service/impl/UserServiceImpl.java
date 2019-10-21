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
     * connection is being closed or DAO exceptions occur.
     */
    private static Logger logger = LogManager.getLogger(
            ApplicationServiceImpl.class);

    @Override
    public User signIn(final String login, final String password)
            throws ServiceException {
        String encodedPass = PasswordEncoder.encodePassword(login, password);
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        UserDAO userDAO = null;
        try {
            userDAO = factory.getUserDAO(wrapper.getConnection());
            Specification userByLogin = new UserByLoginSpecification(
                    login, encodedPass);
            List<User> userList = userDAO.query(userByLogin);
            User user = null;
            if (userList.size() == 1) {
                user = userList.remove(0);
            }
            if (user == null) {
                throw new ServiceException("message.user.login.recognized");
            } else {
                OrganisationDAO organisationDAO = factory.
                        getOrganisationDAO(userDAO.getConnection());
                user.setOrganisation(organisationDAO.findEntityById(
                        user.getOrganisation().getId()));
            }
            if (!user.isActual()) {
                throw new ServiceException("message.login.validity");
            } else if (!user.getOrganisation().isAccepted()) {
                throw new ServiceException("message.organisation.validity");
            } else {
                return user;
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.user.show.mistake");
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
                throw new ServiceException("message.user.id.existed");
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.user.show.mistake");
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
            throw new ServiceException("message.organisation.unp.incorrect");
        }
        if (!UserValidator.validateEmail(user.getMail()) ||
                !UserValidator.validateEmail(user.getOrganisation().
                        getEmail())) {
            throw new ServiceException("message.user.email.incorrect");
        }
        if (!UserValidator.validatePhoneNumber(user.getPhone())
                || !UserValidator.validatePhoneNumber(user.getOrganisation().
                getPhoneNumber())) {
            throw new ServiceException("message.user.phone.incorrect");
        }
        if (!UserValidator.validatePassword(user.getPassword())) {
            throw new ServiceException("message.user.password.notStrong");
        }
        user.setPassword(PasswordEncoder.encodePassword(user.getLogin(),
                user.getPassword()));
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
                    logger.error(e.getMessage(), e);
                    wrapper.rollbackOperation(userDAO.getConnection());
                    throw new ServiceException(
                            "message.user.registration.error");
                }
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.user.registration.error");
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
