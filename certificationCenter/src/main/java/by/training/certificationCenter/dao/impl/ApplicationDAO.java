package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.ApplicationFactory;
import by.training.certificationCenter.service.specification.AppsByUserIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends CertificationMySqlDAO<Application> {
    /**
     * The variable contains a database query to add a new application.
     */
    private static final String INSERT_APP = "INSERT INTO application(user_id, "
            + "organisation_id, date_add, requirements) VALUES (?, ?, ?, ?)";
    /**
     * The variable contains a database query to get an application by id.
     */
    private static final String FIND_APP = "SELECT id, user_id, "
            + "organisation_id, registration_number, date_add, requirements, "
            + "date_resolve, application_status FROM application WHERE id = ?";
    /**
     * The variable contains a database query to get a limited list of
     * applications.
     */
    private static final String FIND_ALL = "SELECT id, registration_number, "
            + "date_add, date_resolve, application_status FROM application "
            + "ORDER BY id DESC LIMIT ?,?";
    /**
     * The variable contains a database query to remove an application by id.
     */
    private static final String REMOVE_APP = "DELETE FROM application "
            + "WHERE id = ?";
    /**
     * The variable contains a database query to update an application by id.
     */
    private static final String UPDATE_APP = "UPDATE application SET "
            + "requirements = ? WHERE id = ?";

    public ApplicationDAO(Connection newConnection) {
        super(newConnection);
    }

    /**
     * Method allows to find a limited list of applications.
     *
     * @param skipPages number of pages that needs to skip
     * @param pageLim   number of rows (applications) at the page
     * @return list of applications
     * @throws DAOException when occurs a database access error
     */
    @Override
    public List<Application> findAll(int skipPages, int pageLim)
            throws DAOException {
        Connection connection = getConnection();
        List<Application> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_ALL)) {
            statement.setInt(1, skipPages);
            statement.setInt(2, pageLim);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(receiveDemoApplication(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ApplicationDAO"), e);
        }
        return entities;
    }

    /**
     * Method allows to find an application by id.
     *
     * @param id application id
     * @return Application instance
     * @throws DAOException when occurs a database access error
     */
    @Override
    public Application findEntityById(final int id) throws DAOException {
        Connection connection = getConnection();
        Application application = null;
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_APP)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                application = receiveDemoApplication(resultSet);
                buildApplicationWithUserAndOrg(application, resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ApplicationDAO"), e);
        }
        return application;
    }

    /**
     * Method allows to remove an application by id from the database.
     *
     * @param id application id
     * @return true if entity was successfully removed from the database
     * @throws DAOException when occurs a database access error
     */
    @Override
    public boolean remove(int id) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(REMOVE_APP)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ApplicationDAO"), e);
        }
    }

    /**
     * Method allows to add a new application in to the database.
     *
     * @param entity Application instance
     * @return generated id by the database
     * @throws DAOException when occurs a database access error
     */
    @Override
    public int create(Application entity) throws DAOException {
        int applicationId = 0;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(INSERT_APP, Statement.RETURN_GENERATED_KEYS)) {
            int index = 0;
            statement.setInt(++index, entity.getExecutor().getId());
            statement.setInt(++index, entity.getOrganisation().getId());
            statement.setDate(++index, Date.valueOf(entity.getDate_add().
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            statement.setString(++index, entity.getRequirements());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                applicationId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ApplicationDAO"), e);
        } catch (IllegalArgumentException e) {
            String message = "Value can not be parsed to a Date object at the "
                    + "ApplicationDAO";
            throw new DAOException(message, e);
        }
        return applicationId;
    }

    /**
     * Method allows to update an application fields.
     *
     * @param entity updated Application instance
     * @return Application instance if operation finished successfully
     * @throws DAOException when occurs a database access error
     */
    @Override
    public Application update(Application entity) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(UPDATE_APP)) {
            statement.setString(1, entity.getRequirements());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ApplicationDAO"), e);
        }
    }

    /**
     * Method allows to get a specific query. In this case to find a limited
     * list of applications by user id.
     *
     * @param specification Specification instance
     * @return list of applications
     * @throws DAOException when occurs a database access error
     */
    @Override
    public List<Application> query(final Specification specification)
            throws DAOException {
        List<Application> applicationList = new ArrayList<>();
        if (specification instanceof AppsByUserIdSpecification) {
            AppsByUserIdSpecification
                    appsByUserId = (AppsByUserIdSpecification) specification;
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                        appsByUserId.toSqlQuery())) {
                    while (resultSet.next()) {
                        Application application = receiveDemoApplication(
                                resultSet);
                        applicationList.add(application);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(getStatementError(
                        "ApplicationDAO"), e);
            }
        }
        return applicationList;
    }

    private boolean buildApplicationWithUserAndOrg(
            final Application application, final ResultSet resultSet)
            throws DAOException {
        try {
            int userId = resultSet.getInt("user_id");
            int org_Id = resultSet.getInt("organisation_id");
            String appRequirements = resultSet.
                    getString("requirements");
            ApplicationFactory factory = ApplicationFactory.getSingleInstance();
            factory.buildAppWithUserAndOrg(
                    application, userId, org_Id, appRequirements);
            return true;
        } catch (SQLException e) {
            throw new DAOException(getColumnLabelError(
                    "ApplicationDAO"), e);
        }
    }

    private Application receiveDemoApplication(final ResultSet resultSet)
            throws DAOException {
        try {
            int appId = resultSet.getInt("id");
            int regNum = resultSet.getInt("registration_number");
            Date dateAdd = resultSet.getDate("date_add");
            Date dateResolve = resultSet.getDate("date_resolve");
            int status = resultSet.getInt("application_status");
            ApplicationFactory factory = ApplicationFactory.getSingleInstance();
            Application application = factory.createDemoApplication(appId,
                    regNum, dateAdd, dateResolve, status);
            return application;
        } catch (SQLException e) {
            throw new DAOException(getColumnLabelError(
                    "ApplicationDAO"), e);
        }
    }
}
