package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.factory.UserFactory;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.SqlSpecification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends CertificationMySqlDAO<User> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ORG_ID = "organisation_id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASS = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_PATRON = "patronymic";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_MAIL = "email";
    private static final String COLUMN_ROLE = "user_role";
    private static final String COLUMN_ACTUAL = "actual";
    private static final String FIND_USER;

    static {
        FIND_USER = "SELECT * FROM user WHERE id = ?";
    }

    public UserDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntityById(int id) throws SQLException {
        User user = null;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_USER)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        }
        return user;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public List<User> query(Specification specification) {
        SqlSpecification sqlSpec = (SqlSpecification) specification;
        Connection connection = getConnection();
        List<User> entities = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = (ResultSet) statement.executeQuery(sqlSpec.toSqlQuery())) {
                while (resultSet.next()) {
                    User user = createUser(resultSet);
                    entities.add(user);
                }
            }
        } catch (SQLException e) {
            getLogger().error(e.toString());
        }
        return entities;
    }

    private User createUser(final ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt(COLUMN_ID);
        int organisation_id = resultSet.getInt(COLUMN_ORG_ID);
        String login = resultSet.getString(COLUMN_LOGIN);
        String password = resultSet.getString(COLUMN_PASS);
        String name = resultSet.getString(COLUMN_NAME);
        String surname = resultSet.getString(COLUMN_SURNAME);
        String patronymic = resultSet.getString(COLUMN_PATRON);
        long phone = resultSet.getLong(COLUMN_PHONE);
        String email = resultSet.getString(COLUMN_MAIL);
        int role = resultSet.getInt(COLUMN_ROLE);
        boolean actual = resultSet.getBoolean(COLUMN_ACTUAL);
        UserFactory factory = UserFactory.getSingleInstance();
        User user = factory.createUser(userId, organisation_id,
                login, password, name, surname,
                patronymic, phone, email, role, actual);
        return user;
    }
}
