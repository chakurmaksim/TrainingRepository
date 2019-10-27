package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Document;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.Status;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.dao.factory.DAOFactory;
import by.training.certificationCenter.dao.filereader.FileWriteReader;
import by.training.certificationCenter.dao.impl.ApplicationDAO;
import by.training.certificationCenter.dao.impl.DocumentDAO;
import by.training.certificationCenter.dao.impl.ProductDAO;
import by.training.certificationCenter.dao.pool.ConnectionPoolWrapper;
import by.training.certificationCenter.dao.pool.TomcatConnectionPoolWrapper;
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
import java.time.LocalDate;
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
    /**
     * Variable contains database connection pool wrapper.
     */
    private ConnectionPoolWrapper wrapper;
    /**
     * DAOFactory instance.
     */
    private DAOFactory daoFactory;

    /**
     * Constructor where is used Tomcat connection pool.
     */
    public ApplicationServiceImpl() {
        wrapper = TomcatConnectionPoolWrapper.getInstance();
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * There is connection pool could be various.
     *
     * @param newWrapper Connection pool wrapper
     */
    public ApplicationServiceImpl(final ConnectionPoolWrapper newWrapper) {
        this.wrapper = newWrapper;
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * Method allows to get a list of applications by user id depending of
     * the user role.
     *
     * @param user      User instance with the role of client or expert
     * @param skipPages number of pages you need to skip
     * @param pageLimit number of applications you need to get
     * @return list of applications
     * @throws ServiceException when could not get the application list
     */
    @Override
    public List<Application> receiveAppsByUser(
            final User user, final int skipPages, final int pageLimit)
            throws ServiceException {
        ApplicationDAO applicationDAO = null;
        try {
            applicationDAO = daoFactory.getApplicationDAO(
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

    /**
     * Method is to get an application by id.
     *
     * @param applicationId application id
     * @param user          User instance with the role of client or expert
     * @return Application instance
     * @throws ServiceException when could not get an application
     */
    @Override
    public Application showApplicationById(
            final int applicationId, final User user) throws ServiceException {
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

    /**
     * Method is to save the new application with its contents
     *
     * @param application Application instance
     * @return true if operation finished successfully
     * @throws ServiceException if operation failed
     */
    @Override
    public boolean addNewApplication(final Application application)
            throws ServiceException {
        boolean flag = false;
        if (!ApplicationValidator.validateDateAdded(
                application.getDate_add())) {
            throw new ServiceException("message.application.date.validate");
        }
        for (Product product : application.getProducts()) {
            if (!ApplicationValidator.validateProductCode(product.getCode())) {
                throw new ServiceException("message.product.code.validate");
            }
        }
        ApplicationDAO applicationDAO = null;
        int isolationLevel = 0;
        try {
            applicationDAO = daoFactory.getApplicationDAO(
                    wrapper.getConnection());
            wrapper.setAutoCommit(applicationDAO.
                    getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    applicationDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(
                    applicationDAO.getConnection());
            int applicationId = applicationDAO.create(application);
            if (applicationId > 0) {
                application.setId(applicationId);
                DocumentDAO documentDAO = daoFactory.getDocumentDAO(
                        applicationDAO.getConnection());
                ProductDAO productDAO = daoFactory.getProductDAO(
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
            if (applicationDAO != null) {
                returnBackConnection(applicationDAO, isolationLevel);
            }
        }
        return flag;
    }

    /**
     * Method is to delete the application by id with its contents.
     *
     * @param applicationId application id
     * @param user          User instance with the role of client
     * @return true if operation finished successfully
     * @throws ServiceException if operation failed
     */
    @Override
    public boolean deleteApplication(final int applicationId, final User user)
            throws ServiceException {
        boolean flag = false;
        Application application;
        try {
            application = showApplicationById(applicationId, user);
        } catch (ServiceException e) {
            throw new ServiceException("message.application.id.existed");
        }
        ApplicationDAO applicationDAO = null;
        int isolationLevel = 0;
        try {
            applicationDAO = daoFactory.getApplicationDAO(wrapper.getConnection());
            wrapper.setAutoCommit(applicationDAO.
                    getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    applicationDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(
                    applicationDAO.getConnection());
            ProductDAO productDAO = daoFactory.getProductDAO(
                    applicationDAO.getConnection());
            DocumentDAO documentDAO = daoFactory.getDocumentDAO(
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
            if (applicationDAO != null) {
                returnBackConnection(applicationDAO, isolationLevel);
            }
        }
        return flag;
    }

    /**
     * Method is to update application fields.
     *
     * @param application updated Application instance
     * @return true if operation finished successfully
     * @throws ServiceException if operation failed
     */
    @Override
    public boolean updateApplication(final Application application)
            throws ServiceException {
        if (!ApplicationValidator.checkPossibilityToUpdate(
                application.getStatus())) {
            throw new ServiceException("message.application.updated.late");
        }
        ApplicationDAO applicationDAO = null;
        int isolationLevel = 0;
        try {
            applicationDAO = daoFactory.getApplicationDAO(
                    wrapper.getConnection());
            wrapper.setAutoCommit(applicationDAO.
                    getConnection(), false);
            isolationLevel = wrapper.getTransactionIsolationLevel(
                    applicationDAO.getConnection());
            wrapper.setTransactionReadUncommittedLevel(
                    applicationDAO.getConnection());
            applicationDAO.update(application);
            ProductDAO productDAO = daoFactory.getProductDAO(
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
            if (applicationDAO != null) {
                returnBackConnection(applicationDAO, isolationLevel);
            }
        }
    }

    /**
     * Method allows you to get a FileInputStream for downloading a file that
     * relates to a specific application
     *
     * @param fullFileName file name with packages
     * @return FileInputStream object
     * @throws ServiceException FileInputStream object could not be got
     */
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

    /**
     * Method is to get the total number of applications
     *
     * @param user User instance
     * @return quantity of rows (number of applications)
     * @throws ServiceException number of rows can not be got
     */
    @Override
    public int receiveRowsNumber(User user) throws ServiceException {
        ApplicationDAO applicationDAO = null;
        try {
            applicationDAO = daoFactory.getApplicationDAO(
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

    /**
     * Method consists in updating the application fields, namely in assigning
     * a registration number, date of completion and status of work.
     *
     * @param user User instance with the role of expert
     * @param applicationId application id
     * @param regNumber registration number of an application
     * @param resolveDate date of completion
     * @param statusIndex status index of work
     * @return true if operation finished successfully
     * @throws ServiceException if operation failed
     */
    @Override
    public boolean registerApplication(
            final User user, int applicationId, int regNumber,
            LocalDate resolveDate, int statusIndex) throws ServiceException {
        Application application;
        if (!ApplicationValidator.checkApplicationStatusIndex(statusIndex)) {
            throw new ServiceException("message.application.status.index.error");
        }
        try {
            application = showApplicationById(applicationId, user);
        } catch (ServiceException e) {
            throw new ServiceException("message.application.id.existed");
        }
        if (!ApplicationValidator.validateDateResolved(
                application.getDate_add(), resolveDate)) {
            throw new ServiceException("message.application.resolveDate.validate");
        }
        application.setReg_num(regNumber);
        application.setDate_resolve(resolveDate);
        application.setStatus(Status.getByIdentity(statusIndex));
        ApplicationDAO applicationDAO = null;
        try {
            applicationDAO = daoFactory.getApplicationDAO(
                    wrapper.getConnection());
            applicationDAO.update(application);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("message.application.register.mistake");
        } finally {
            if (applicationDAO != null) {
                try {
                    wrapper.closeConnection(applicationDAO.getConnection());
                } catch (DAOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return true;
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

    private void returnBackConnection(final ApplicationDAO applicationDAO,
                                      final int isolationLevel) {
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
