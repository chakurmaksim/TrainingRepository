package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.ApplicationTable;
import by.training.certificationCenter.bean.Status;
import by.training.certificationCenter.service.factory.ApplicationFactory;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.SqlSpecification;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class ApplicationDAO extends CertificationMySqlDAO<Application> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER = "user_id";
    private static final String COLUMN_ORG = "organisation_id";
    private static final String COLUMN_REG_NUM = "registration_number";
    private static final String COLUMN_DATE_ADD = "date_add";
    private static final String COLUMN_REQUIREMENTS = "requirements";
    private static final String COLUMN_DATE_RESOLVE = "date_resolve";
    private static final String COLUMN_STATUS = "application_status";
    private static final String INSERT_APP;
    private static final String FIND_APP;
    private static final EnumSet<Status> STATUS_ENUM_SET;

    static {
        INSERT_APP = "INSERT INTO application(user_id, "
                + "organisation_id, date_add, requirements) "
                + "VALUES(?, ?, ?, ?)";
        FIND_APP = "SELECT * FROM application WHERE id = ?";
        STATUS_ENUM_SET = EnumSet.allOf(Status.class);
    }

    public ApplicationDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Application> findAll() {
        return null;
    }

    @Override
    public Application findEntityById(int id) throws SQLException {
        Application application = null;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_APP)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int appId = resultSet.getInt(COLUMN_ID);
                int userId = resultSet.getInt(COLUMN_USER);
                int org_Id = resultSet.getInt(COLUMN_ORG);
                int regNum = resultSet.getInt(COLUMN_REG_NUM);
                Date dateAdd = resultSet.getDate(COLUMN_DATE_ADD);
                String requirements = resultSet.getString(COLUMN_REQUIREMENTS);
                Date dateResolve = resultSet.getDate(COLUMN_DATE_RESOLVE);
                int status = resultSet.getInt(COLUMN_STATUS);
                ApplicationFactory factory = ApplicationFactory.getSingleInstance();
                application = factory.createApplication(
                        appId, userId, org_Id, regNum, dateAdd,
                        requirements, dateResolve, status);
            }
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
    public boolean create(Application entity) {
        boolean flag = false;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_APP)) {
            int index = 0;
            statement.setInt(++index, entity.getExecutor().getId());
            statement.setInt(++index, entity.getOrg().getId());
            statement.setDate(++index, Date.valueOf(entity.getDate_add().
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            statement.setString(++index, entity.getRequirements());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            getLogger().error(e.toString());
        }
        return flag;
    }

    @Override
    public Application update(Application entity) {
        return null;
    }

    @Override
    public List<Application> query(Specification specification) {
        SqlSpecification sqlSpec = (SqlSpecification) specification;
        Connection connection = getConnection();
        List<Application> entities = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = (ResultSet) statement.executeQuery(sqlSpec.toSqlQuery())) {
                ResultSetMetaData resMetaData = resultSet.getMetaData();
                while (resultSet.next()) {
                    Application entity = new Application(resultSet.getInt("id"));
                    for (int i = 1; i <= resMetaData.getColumnCount(); i++) {
                        String columnName = resMetaData.getColumnName(i);
                        ApplicationTable table = ApplicationTable.valueOf(columnName.toUpperCase());
                        switch (table) {
                            case DATE_ADD:
                                entity.setDate_add(resultSet.getDate(i).toLocalDate());
                                break;
                            case REGISTRATION_NUMBER:
                                entity.setReg_num(resultSet.getInt(i));
                                break;
                            case DATE_RESOLVE:
                                Date date = resultSet.getDate(i);
                                if (date != null) {
                                    entity.setDate_resolve(date.toLocalDate());
                                }
                                break;
                            case REQUIREMENTS:
                                entity.setRequirements(resultSet.getString(i));
                                break;
                            case APPLICATION_STATUS:
                                int ind = resultSet.getInt(i);
                                Iterator<Status> iterator = STATUS_ENUM_SET.iterator();
                                while (iterator.hasNext()) {
                                    Status status = iterator.next();
                                    if (status.getIndex() == ind) {
                                        entity.setStatus(status);
                                    }
                                }
                        }
                    }
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            getLogger().error(e.toString());
        }
        return entities;
    }
}
