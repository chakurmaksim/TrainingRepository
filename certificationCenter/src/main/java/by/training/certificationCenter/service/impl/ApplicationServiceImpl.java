package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.impl.ApplicationDAO;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.ProductService;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.specification.AppsByUserIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApplicationServiceImpl
        implements ApplicationService<Application, User> {
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
                    Specification appsByUserIdSpec =
                            new AppsByUserIdSpecification(user.getId(),
                                    skip, pageLim);
                    List<Application> apps = appDao.query(appsByUserIdSpec);
                    factory.closeCertificationDAO(appDao);
                    return apps;
                case EXPERT:
                    apps = appDao.findAll(skip, pageLim);
                    factory.closeCertificationDAO(appDao);
                    return apps;
                default:
                    throw new ServiceException(String.format(
                            "User \"%s\" with the role \"%s\" does not have "
                                    + "permission to access applications",
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
        DAOFactory daoFactory = DAOFactory.getInstance();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        Application application;
        try {
            ApplicationDAO applicationDAO = daoFactory.getApplicationDAO();
            application = applicationDAO.findEntityById(applicationId);
            daoFactory.closeCertificationDAO(applicationDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        if (application != null) {
            ProductService productService = serviceFactory.getProductService();
            application.setProducts(
                    productService.receiveProductsByAppId(applicationId));
            if (application.getExecutor().getId() == user.getId()) {
                application.setExecutor(user);
                application.setOrg(user.getOrg());
            } else {
                UserService userService = serviceFactory.getUserService();
                User appUser = userService.receiveUserById(
                        application.getExecutor().getId());
                application.setExecutor(appUser);
                application.setOrg(appUser.getOrg());
            }
            return application;
        } else {
            throw new ServiceException(String.format(
                    "Application with id number \"%d\" does not exist.",
                    applicationId));
        }
    }

    @Override
    public boolean addNewApplication(final Application application)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            ApplicationDAO applicationDAO = factory.getApplicationDAO();
            int applicationId = applicationDAO.create(application);
            factory.closeCertificationDAO(applicationDAO);
            if (applicationId > 0) {
                ProductService productService = ServiceFactory.
                        getInstance().getProductService();
                for (Product product : application.getProducts()) {
                    product.setApplicationId(applicationId);
                    productService.addNewProduct(product);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean deleteApplication(final int applicationId, final User user)
            throws ServiceException {
        Application application = showApplicationById(applicationId, user);
        DAOFactory factory = DAOFactory.getInstance();
        ProductService productService = ServiceFactory.
                getInstance().getProductService();
        for (Product product : application.getProducts()) {
            productService.deleteProduct(product.getId());
        }
        try {
            ApplicationDAO applicationDAO = factory.getApplicationDAO();
            applicationDAO.remove(applicationId);
            factory.closeCertificationDAO(applicationDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public Application updateApplication(final Application application)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        Application updatedApp;
        try {
            ApplicationDAO applicationDAO = factory.getApplicationDAO();
            updatedApp = applicationDAO.update(application);
            factory.closeCertificationDAO(applicationDAO);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        if (updatedApp != null) {
            ProductService productService = ServiceFactory.
                    getInstance().getProductService();
            List<Product> updatedProducts = new ArrayList<>();
            for (Product product : application.getProducts()) {
                updatedProducts.add(productService.updateProduct(product));
            }
            updatedApp.setProducts(updatedProducts);
            return updatedApp;
        } else {
            throw new ServiceException(String.format(
                    "Application with id number \"%d\" can not be updated.",
                    application.getId()));
        }
    }
}
