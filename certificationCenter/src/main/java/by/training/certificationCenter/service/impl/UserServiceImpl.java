package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.impl.UserDAO;
import by.training.certificationCenter.service.OrganisationService;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.UserByLoginSpecification;

import java.util.List;

public class UserServiceImpl implements UserService<User> {
    @Override
    public User signIn(final String login, final String password)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            UserDAO userDAO = factory.getUserDAO();
            Specification userByLogin = new UserByLoginSpecification(
                    login, password);
            List<User> userList = userDAO.query(userByLogin);
            factory.closeCertificationDAO(userDAO);
            User user = null;
            if (userList.size() > 0) {
                user = userList.remove(0);
                OrganisationService orgService = ServiceFactory.
                        getInstance().getOrganisationService();
                Organisation org = orgService.receiveOrgById(
                        user.getOrg().getId());
                user.setOrg(org);
            }

            if (user != null && user.isActual() && user.getOrg().isAccepted()) {
                return user;
            } else if (user == null) {
                throw new ServiceException(
                        "User login or password are not recognized");
            } else {
                throw new ServiceException(
                        "User or organisation are not valid");
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User receiveUserById(final int userId)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            UserDAO userDAO = factory.getUserDAO();
            User user = userDAO.findEntityById(userId);
            factory.closeCertificationDAO(userDAO);
            if (user != null) {
                OrganisationService orgService = ServiceFactory.getInstance().
                        getOrganisationService();
                Organisation org = orgService.receiveOrgById(
                        user.getOrg().getId());
                user.setOrg(org);
            }
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean registration(final User user)
            throws ServiceException {
        return false;
    }
}
