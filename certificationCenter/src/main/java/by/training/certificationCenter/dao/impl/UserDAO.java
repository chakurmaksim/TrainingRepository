package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.UserFactory;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.UserByLoginSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends CertificationMySqlDAO<User> {
    private static final String FIND_USER = "SELECT id, organisation_id, "
            + "login, name, surname, patronymic, phone, email, user_role, "
            + "actual FROM user WHERE id = ?";

    public UserDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<User> findAll(int skip, int pageLim) {
        return null;
    }

    @Override
    public User findEntityById(int id) throws DAOException {
        User user = null;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_USER)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = receiveUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(), e);
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
    public int create(User entity) {
        return 0;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public List<User> query(Specification specification) throws DAOException {
        List<User> entities = new ArrayList<>();
        if (specification instanceof UserByLoginSpecification) {
            UserByLoginSpecification sqlSpec =
                    (UserByLoginSpecification) specification;
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                        sqlSpec.toSqlQuery())) {
                    while (resultSet.next()) {
                        User user = receiveUser(resultSet);
                        entities.add(user);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(getStatementError(), e);
            }
        }
        return entities;
    }

    private User receiveUser(final ResultSet resultSet) throws DAOException {
        try {
            int userId = resultSet.getInt("id");
            System.out.println(userId);
            int organisation_id = resultSet.getInt("organisation_id");
            String login = resultSet.getString("login");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String patronymic = resultSet.getString("patronymic");
            long phone = resultSet.getLong("phone");
            String email = resultSet.getString("email");
            int role = resultSet.getInt("user_role");
            boolean actual = resultSet.getBoolean("actual");
            UserFactory factory = UserFactory.getSingleInstance();
            User user = factory.createUser(
                    userId, organisation_id, login, name, surname,
                    patronymic, phone, email, role, actual);
            System.out.println(user.getId() +" "+user.getLogin() +" "+ user.getRole().getRoleName());
            return user;
        } catch (SQLException e) {
            throw new DAOException(getColumnLabelError(), e);
        }
    }
}
