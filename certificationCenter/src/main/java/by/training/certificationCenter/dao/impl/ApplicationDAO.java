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
    private static final String INSERT_APP = "INSERT INTO application(user_id, "
            + "organisation_id, date_add, requirements) VALUES (?, ?, ?, ?)";
    private static final String FIND_APP = "SELECT id, user_id, "
            + "organisation_id, registration_number, date_add, requirements, "
            + "date_resolve, application_status FROM application WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, registration_number, "
            + "date_add, date_resolve, application_status FROM application "
            + "ORDER BY id DESC LIMIT ?,?";

    public ApplicationDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Application> findAll(int skip, int pageLim)
            throws DAOException {
        Connection connection = getConnection();
        List<Application> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_ALL)) {
            statement.setInt(1, skip);
            statement.setInt(2, pageLim);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = receiveDemoApplication(resultSet);
                entities.add(application);
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(), e);
        }
        return entities;
    }

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
            throw new DAOException(getStatementError(), e);
        }
        return application;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(Application entity) {
        return false;
    }

    @Override
    public int create(Application entity) throws DAOException {
        int applicationId = 0;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(INSERT_APP)) {
            int index = 0;
            statement.setInt(++index, entity.getExecutor().getId());
            statement.setInt(++index, entity.getOrg().getId());
            statement.setDate(++index, Date.valueOf(entity.getDate_add().
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            statement.setString(++index, entity.getRequirements());
            applicationId = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(getStatementError(), e);
        }
        return applicationId;
    }

    @Override
    public Application update(Application entity) {
        return null;
    }

    @Override
    public List<Application> query(final Specification specification)
            throws DAOException {
        List<Application> entities = new ArrayList<>();
        if (specification instanceof AppsByUserIdSpecification) {
            AppsByUserIdSpecification appsByUserId =
                    (AppsByUserIdSpecification) specification;
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                        appsByUserId.toSqlQuery())) {
                    while (resultSet.next()) {
                        Application application = receiveDemoApplication(
                                resultSet);
                        entities.add(application);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(getStatementError(), e);
            }
        }
        return entities;
    }

    private boolean buildApplicationWithUserAndOrg(
            final Application application, final ResultSet resultSet)
            throws DAOException {
        boolean flag = false;
        try {
            int userId = resultSet.getInt("user_id");
            int org_Id = resultSet.getInt("organisation_id");
            String requirements = resultSet.getString("requirements");
            ApplicationFactory factory = ApplicationFactory.getSingleInstance();
            factory.buildAppWithUserAndOrg(
                    application, userId, org_Id, requirements);
            flag = true;
        } catch (SQLException e) {
            throw new DAOException(getColumnLabelError(), e);
        }
        return flag;
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
            Application application = factory.createDemoApp(appId, regNum,
                    dateAdd, dateResolve, status);
            return application;
        } catch (SQLException e) {
            throw new DAOException(getColumnLabelError(), e);
        }
    }
}
