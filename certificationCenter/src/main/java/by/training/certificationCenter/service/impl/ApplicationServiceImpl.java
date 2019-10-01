package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.impl.ApplicationDAO;
import by.training.certificationCenter.dao.impl.ProductDAO;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.specification.AppsByUserIdSpecification;
import by.training.certificationCenter.service.specification.ProductsByAppIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService<Application, User> {
    @Override
    public List<Application> receiveAppsByUserId(
            final User user, final int pageNum, final int pageLim)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            ApplicationDAO appDao = factory.getApplicationDAO();
            int skip = (pageNum - 1) * pageLim;
            Role role = Role.getByIdentity(user.getRole().getIndex());
            switch (role) {
                case CLIENT:
                    Specification appsByUserIdSpec = new AppsByUserIdSpecification(
                            user.getId(), skip, pageLim);
                    List<Application> apps = appDao.query(appsByUserIdSpec);
                    factory.closeCertificationDAO(appDao);
                    return apps;
                case EXPERT:
                    apps = appDao.findAll(skip, pageLim);
                    return apps;
                default:
                    throw new ServiceException(String.format(
                            "User \"%s\" with the role \"%s\" does not have permission to access applications",
                            user.getLogin(), user.getRole().getRoleName()));
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Application showApplicationById(
            final int applicationId, final User user)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        Application application = null;
        try {
            ApplicationDAO applicationDAO = factory.getApplicationDAO();
            application = applicationDAO.findEntityById(applicationId);
            factory.closeCertificationDAO(applicationDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.toString());
        }
        if (application != null) {
            try {
                ProductDAO productDAO = factory.getProductDAO();
                Specification prodByAppIdSpec =
                        new ProductsByAppIdSpecification(applicationId);
                List<Product> products = productDAO.query(prodByAppIdSpec);
                application.setProducts(products);
                factory.closeCertificationDAO(productDAO);
            } catch (DAOException e) {
                throw new ServiceException(e.getMessage());
            }
            if (application.getExecutor().getId() == user.getId()) {
                application.setExecutor(user);
                application.setOrg(user.getOrg());
            } else {
                UserService userService = ServiceFactory.
                        getInstance().getUserService();
                User appUser = userService.receiveUserById(
                        application.getExecutor().getId());
                application.setExecutor(appUser);
                application.setOrg(appUser.getOrg());
            }
        }
        return application;
    }

    @Override
    public boolean addNewApplication(Application application)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            ApplicationDAO applicationDAO = factory.getApplicationDAO();
            factory.setAutoCommit(applicationDAO, false);
            int applicationId = applicationDAO.create(application);
            if (applicationId > 0) {
                ProductDAO productDAO = factory.getProductDAO();
                factory.setAutoCommit(productDAO, false);
                for (Product product : application.getProducts()) {
                    product.setApplicationId(applicationId);
                    int productId = productDAO.create(product);
                    if (productId == 0) {
                        factory.rollbackOperation(applicationDAO);
                        factory.rollbackOperation(productDAO);
                        return false;
                    }
                }
                factory.commitOperation(applicationDAO);
                factory.commitOperation(productDAO);
                factory.closeCertificationDAO(applicationDAO);
                factory.closeCertificationDAO(productDAO);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }
}
