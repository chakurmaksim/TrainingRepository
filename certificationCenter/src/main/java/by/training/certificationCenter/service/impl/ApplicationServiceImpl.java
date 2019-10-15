package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.*;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.filereader.FileWriteReader;
import by.training.certificationCenter.dao.impl.ApplicationDAO;
import by.training.certificationCenter.dao.impl.DocumentDAO;
import by.training.certificationCenter.dao.impl.ProductDAO;
import by.training.certificationCenter.dao.pool.ConnectionWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.validator.ApplicationValidator;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.specification.AppsByUserIdSpecification;
import by.training.certificationCenter.service.specification.DocumentsByAppIdSpecification;
import by.training.certificationCenter.service.specification.ProductsByAppIdSpecification;
import by.training.certificationCenter.service.specification.Specification;
import com.fasterxml.uuid.Generators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApplicationServiceImpl
        implements ApplicationService<Application, User> {
    /**
     * Events logger, in that case to register possible exceptions when
     * connection is being closed.
     */
    private static Logger logger = LogManager.getLogger(
            ApplicationServiceImpl.class);

    @Override
    public List<Application> receiveAppsByUser(
            final User user, final int pageNum, final int pageLim)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        int skipPages = (pageNum - 1) * pageLim;
        try {
            applicationDAO = factory.getApplicationDAO(
                    wrapper.getConnection());
            Role role = Role.getByIdentity(user.getRole().getIndex());
            switch (role) {
                case CLIENT:
                    Specification appsByUserIdSpec =
                            new AppsByUserIdSpecification(
                                    user.getId(), skipPages, pageLim);
                    List<Application> applications = applicationDAO.
                            query(appsByUserIdSpec);
                    return applications;
                case EXPERT:
                    applications = applicationDAO.findAll(skipPages, pageLim);
                    return applications;
                default:
                    String message = String.format("User \"%s\" with the role "
                                    + "\"%s\" does not have permission to "
                                    + "access applications", user.getLogin(),
                            user.getRole().getRoleName());
                    throw new ServiceException(message);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            if (applicationDAO != null) {
                try {
                    wrapper.closeConnection(applicationDAO.getConnection());
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public Application showApplicationById(
            final int applicationId, final User user) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        try {
            applicationDAO = daoFactory.getApplicationDAO(
                    wrapper.getConnection());
            Application application = applicationDAO.
                    findEntityById(applicationId);
            if (application != null) {
                ProductDAO productDAO = daoFactory.getProductDAO(
                        applicationDAO.getConnection());
                application.setProducts(receiveProductsByAppId(
                        applicationId, productDAO));
                DocumentDAO documentDAO = daoFactory.getDocumentDAO(
                        applicationDAO.getConnection());
                application.setDocuments(receiveDocumentsByAppId(
                        applicationId, documentDAO));
                appointAppExecutor(application, user);
                return application;
            } else {
                String message = String.format("Application with id number "
                        + "\"%d\" does not exist", applicationId);
                throw new ServiceException(message);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            if (applicationDAO != null) {
                try {
                    wrapper.closeConnection(applicationDAO.getConnection());
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public boolean addNewApplication(final Application application)
            throws ServiceException {
        boolean flag = false;
        if (!ApplicationValidator.validateAddedDate(
                application.getDate_add())) {
            throw new ServiceException("Application date has to be today");
        }
        for (Product product : application.getProducts()) {
            if (!ApplicationValidator.validateProductCode(product.getCode())) {
                throw new ServiceException("Product code should be between 4 "
                        + "and 10 digits");
            }
        }
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        int isolationLevel = 0;
        try {
            applicationDAO = factory.getApplicationDAO(wrapper.getConnection());
            wrapper.setAutoCommit(applicationDAO.
                    getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    applicationDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(
                    applicationDAO.getConnection());
            int applicationId = applicationDAO.create(application);
            if (applicationId > 0) {
                DocumentDAO documentDAO = factory.getDocumentDAO(
                        applicationDAO.getConnection());
                ProductDAO productDAO = factory.getProductDAO(
                        applicationDAO.getConnection());
                try {
                    saveNewDocuments(application, applicationId, documentDAO);
                    addNewProducts(application, applicationId, productDAO);
                    wrapper.commitOperation(applicationDAO.getConnection());
                    flag = true;
                } catch (ServiceException | DAOException e) {
                    wrapper.rollbackOperation(applicationDAO.getConnection());
                    throw new ServiceException(e.getMessage(), e);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            returnBackConnection(wrapper, applicationDAO, isolationLevel);
        }
        return flag;
    }

    @Override
    public boolean deleteApplication(final int applicationId, final User user)
            throws ServiceException {
        boolean flag = false;
        Application application = showApplicationById(applicationId, user);
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        int isolationLevel = 0;
        try {
            applicationDAO = factory.getApplicationDAO(wrapper.getConnection());
            wrapper.setAutoCommit(applicationDAO.
                    getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    applicationDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(
                    applicationDAO.getConnection());
            ProductDAO productDAO = factory.getProductDAO(
                    applicationDAO.getConnection());
            DocumentDAO documentDAO = factory.getDocumentDAO(
                    applicationDAO.getConnection());
            FileWriteReader writeReader = FileWriteReader.getSingleInstance();
            try {
                for (Product product : application.getProducts()) {
                    productDAO.remove(product.getId());
                }
                for (Document document : application.getDocuments()) {
                    documentDAO.remove(document.getId());
                    writeReader.deleteFile(
                            document.getUploadFilePath()
                                    + document.getFileName());
                }
                applicationDAO.remove(applicationId);
                wrapper.commitOperation(applicationDAO.getConnection());
                flag = true;
            } catch (DAOException e) {
                wrapper.rollbackOperation(applicationDAO.getConnection());
                throw new ServiceException(e.getMessage(), e);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        } finally {
            returnBackConnection(wrapper, applicationDAO, isolationLevel);
        }
        return flag;
    }

    @Override
    public Application updateApplication(final Application application)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        int isolationLevel = 0;
        try {
            applicationDAO = factory.getApplicationDAO(wrapper.getConnection());
            wrapper.setAutoCommit(applicationDAO.
                    getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    applicationDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(
                    applicationDAO.getConnection());
            Application updatedApplication = applicationDAO.update(application);
            if (updatedApplication != null) {
                ProductDAO productDAO = factory.getProductDAO(
                        applicationDAO.getConnection());
                try {
                    updateProducts(updatedApplication, productDAO);
                    wrapper.commitOperation(applicationDAO.getConnection());
                    return updatedApplication;
                } catch (ServiceException | DAOException e) {
                    wrapper.rollbackOperation(applicationDAO.getConnection());
                    throw new ServiceException(e.getMessage(), e);
                }
            } else {
                String message = String.format("Application with id number "
                        + "\"%d\" can not be updated", application.getId());
                throw new ServiceException(message);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            returnBackConnection(wrapper, applicationDAO, isolationLevel);
        }
    }

    public FileInputStream receiveFileInputStream(final String fullFileName)
            throws ServiceException {
        try {
            return FileWriteReader.getSingleInstance().
                    getInputStream(fullFileName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<Product> receiveProductsByAppId(
            final int appId, final ProductDAO productDAO)
            throws ServiceException {
        Specification specification = new ProductsByAppIdSpecification(appId);
        try {
            return productDAO.query(specification);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<Document> receiveDocumentsByAppId(
            final int appId, final DocumentDAO documentDAO)
            throws ServiceException {
        Specification specification = new DocumentsByAppIdSpecification(appId);
        FileWriteReader writeReader = FileWriteReader.getSingleInstance();
        try {
            List<Document> documentList = documentDAO.query(specification);
            return documentList;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void appointAppExecutor(
            final Application application, final User user)
            throws ServiceException {
        if (application.getExecutor().getId() == user.getId()) {
            application.setExecutor(user);
            application.setOrganisation(user.getOrganisation());
        } else {
            UserService userService = ServiceFactory.
                    getInstance().getUserService();
            try {
                User appUser = userService.receiveUserById(
                        application.getExecutor().getId());
                application.setExecutor(appUser);
                application.setOrganisation(appUser.getOrganisation());
            } catch (ServiceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
    }

    private void addNewProducts(
            final Application application, final int applicationId,
            final ProductDAO productDAO) throws ServiceException {
        try {
            for (Product product : application.getProducts()) {
                product.setApplicationId(applicationId);
                productDAO.create(product);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void saveNewDocuments(
            Application application, final int applicationId,
            final DocumentDAO documentDAO) throws ServiceException {
        FileWriteReader writeReader = FileWriteReader.getSingleInstance();
        List<Document> documentList = application.getDocuments();
        for (Document document : documentList) {
            document.setApplicationId(applicationId);
            UUID generatedName = Generators.timeBasedGenerator().generate();
            int pointInd = document.getFileName().lastIndexOf(".");
            String fileExtension = document.getFileName().substring(pointInd);
            String fileName = generatedName + fileExtension;
            document.setFileName(fileName);
            try {
                writeReader.writeDocument(document.getUploadFilePath(),
                        document.getFileName(), document.getInputStream());
                documentDAO.create(document);
            } catch (DAOException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
    }

    private void updateProducts(
            final Application application, final ProductDAO productDAO)
            throws ServiceException {
        List<Product> productList = new ArrayList<>();
        try {
            for (Product product : application.getProducts()) {
                productList.add(productDAO.update(product));
            }
            application.setProducts(productList);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void returnBackConnection(final ConnectionWrapper wrapper,
                                      final ApplicationDAO applicationDAO,
                                      final int isolationLevel) {
        if (applicationDAO != null) {
            try {
                if (isolationLevel != 0) {
                    wrapper.setTransactionIsolationLevel(
                            applicationDAO.getConnection(),
                            isolationLevel);
                }
                wrapper.setAutoCommit(applicationDAO.
                        getConnection(), true);
                wrapper.closeConnection(applicationDAO.getConnection());
            } catch (DAOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
