package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Document;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.User;
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
import java.util.List;
import java.util.UUID;

public class ApplicationServiceImpl
        implements ApplicationService<Application, User> {
    /**
     * Events logger, in that case to register possible exceptions when
     * connection is being closed or DAO exceptions occur.
     */
    private static Logger logger = LogManager.getLogger(
            ApplicationServiceImpl.class);

    @Override
    public List<Application> receiveAppsByUser(
            final User user, final int skipPages, final int pageLimit)
            throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        try {
            applicationDAO = factory.getApplicationDAO(
                    wrapper.getConnection());
            Role role = Role.getByIdentity(user.getRole().getIndex());
            switch (role) {
                case CLIENT:
                    Specification appsByUserIdSpec =
                            new AppsByUserIdSpecification(
                                    user.getId(), skipPages, pageLimit);
                    List<Application> applications = applicationDAO.
                            query(appsByUserIdSpec);
                    return applications;
                case EXPERT:
                    applications = applicationDAO.findAll(skipPages, pageLimit);
                    return applications;
                default:
                    throw new ServiceException(
                            "message.application.list.permission");
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.list.mistake");
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
                application.setProducts(productDAO.query(
                        new ProductsByAppIdSpecification(applicationId)));
                DocumentDAO documentDAO = daoFactory.getDocumentDAO(
                        applicationDAO.getConnection());
                application.setDocuments(documentDAO.query(
                        new DocumentsByAppIdSpecification(applicationId)));
                appointAppExecutor(application, user);
                return application;
            } else {
                throw new ServiceException("message.application.id.existed");
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.show.mistake");
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
            throw new ServiceException("message.application.date.validate");
        }
        for (Product product : application.getProducts()) {
            if (!ApplicationValidator.validateProductCode(product.getCode())) {
                throw new ServiceException("message.product.code.validate");
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
                application.setId(applicationId);
                DocumentDAO documentDAO = factory.getDocumentDAO(
                        applicationDAO.getConnection());
                ProductDAO productDAO = factory.getProductDAO(
                        applicationDAO.getConnection());
                try {
                    saveNewDocuments(application, documentDAO);
                    addNewProducts(application, productDAO);
                    wrapper.commitOperation(applicationDAO.getConnection());
                    flag = true;
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                    wrapper.rollbackOperation(applicationDAO.getConnection());
                    throw new ServiceException(
                            "message.application.added.mistake");
                }
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.added.mistake");
        } finally {
            returnBackConnection(wrapper, applicationDAO, isolationLevel);
        }
        return flag;
    }

    @Override
    public boolean deleteApplication(final int applicationId, final User user)
            throws ServiceException {
        boolean flag = false;
        Application application;
        try {
           application = showApplicationById(applicationId, user);
        } catch (ServiceException e) {
            throw new ServiceException("message.application.deleted.mistake");
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
                logger.error(e.getMessage(), e);
                wrapper.rollbackOperation(applicationDAO.getConnection());
                throw new ServiceException(
                        "message.application.deleted.mistake");
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.deleted.mistake");
        } finally {
            returnBackConnection(wrapper, applicationDAO, isolationLevel);
        }
        return flag;
    }

    @Override
    public boolean updateApplication(final Application application)
            throws ServiceException {
        if (!ApplicationValidator.checkPossibilityToUpdate(
                application.getStatus())) {
            throw new ServiceException("message.application.updated.late");
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
            applicationDAO.update(application);
            ProductDAO productDAO = factory.getProductDAO(
                    applicationDAO.getConnection());
            try {
                for (Product product : application.getProducts()) {
                    if (product.getId() > 0) {
                        productDAO.update(product);
                    } else {
                        productDAO.create(product);
                    }
                }
                wrapper.commitOperation(applicationDAO.getConnection());
                return true;
            } catch (DAOException e) {
                logger.error(e.getMessage(), e);
                wrapper.rollbackOperation(applicationDAO.getConnection());
                throw new ServiceException(
                        "message.application.updated.mistake");
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.updated.mistake");
        } finally {
            returnBackConnection(wrapper, applicationDAO, isolationLevel);
        }
    }

    @Override
    public FileInputStream receiveFileInputStream(final String fullFileName)
            throws ServiceException {
        try {
            return FileWriteReader.getSingleInstance().
                    getInputStream(fullFileName);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.file.found.error");
        }
    }

    @Override
    public int receiveRowsNumber(User user) throws ServiceException {
        ConnectionWrapper wrapper = ConnectionWrapper.getInstance();
        ApplicationDAO applicationDAO = null;
        try {
            applicationDAO = DAOFactory.getInstance().getApplicationDAO(
                    wrapper.getConnection());
            Role role = Role.getByIdentity(user.getRole().getIndex());
            switch (role) {
                case CLIENT:
                    return applicationDAO.getRowsNumber(user.getId());
                case EXPERT:
                    return applicationDAO.getRowsNumber();
                default:
                    throw new ServiceException(
                            "message.application.list.permission");
            }
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.list.mistake");
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

    private void appointAppExecutor(
            final Application application, final User user)
            throws ServiceException {
        if (application.getExecutor().getId() == user.getId()) {
            application.setExecutor(user);
            application.setOrganisation(user.getOrganisation());
        } else {
            UserService userService = ServiceFactory.getInstance().
                    getUserService();
            try {
                User appUser = userService.receiveUserById(
                        application.getExecutor().getId());
                application.setExecutor(appUser);
                application.setOrganisation(appUser.getOrganisation());
            } catch (ServiceException e) {
                throw new ServiceException("message.application.show.mistake");
            }
        }
    }

    private void addNewProducts(
            final Application application, final ProductDAO productDAO)
            throws DAOException {
        for (Product product : application.getProducts()) {
            productDAO.create(product);
        }
    }

    private void saveNewDocuments(
            final Application application, final DocumentDAO documentDAO)
            throws DAOException {
        FileWriteReader writeReader = FileWriteReader.getSingleInstance();
        List<Document> documentList = application.getDocuments();
        for (Document document : documentList) {
            UUID generatedName = Generators.timeBasedGenerator().generate();
            int pointInd = document.getFileName().lastIndexOf(".");
            String fileExtension = document.getFileName().substring(pointInd);
            String fileName = generatedName + fileExtension;
            document.setFileName(fileName);
            writeReader.writeDocument(document.getUploadFilePath(),
                    document.getFileName(), document.getInputStream());
            documentDAO.create(document);
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
