package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.OrganisationFactory;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrganisationDAO extends CertificationMySqlDAO<Organisation> {
    /**
     * The variable contains a database query to get an organisation by id.
     */
    private static final String FIND_BY_ID = "SELECT id, unp, name, address, "
            + "phone, email, accept FROM organisation WHERE id = ?";
    /**
     * The variable contains a database query to add a new organisation.
     */
    private static final String INSERT_ORG = "INSERT INTO organisation("
            + "unp, name, address, phone, email, accept) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    public OrganisationDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Organisation> findAll(int skip, int pageLim) {
        return null;
    }

    @Override
    public Organisation findEntityById(int id) throws DAOException {
        Organisation org = null;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_BY_ID)) {
            int index = 0;
            statement.setInt(++index, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int currId = resultSet.getInt("id");
                    int unp = resultSet.getInt("unp");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    long phone = resultSet.getLong("phone");
                    String email = resultSet.getString("email");
                    boolean accept = resultSet.getBoolean("accept");
                    OrganisationFactory factory = OrganisationFactory.
                            getSingleInstance();
                    org = factory.createOrganisation(
                            currId, unp, name, address, phone, email, accept);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "OrganisationDAO"), e);
        }
        return org;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public int create(Organisation entity) throws DAOException {
        int organisation_id = 0;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                INSERT_ORG, Statement.RETURN_GENERATED_KEYS)) {
            int count = 0;
            statement.setInt(++count, entity.getUnp());
            statement.setString(++count, entity.getName());
            statement.setString(++count, entity.getAddress());
            statement.setLong(++count, entity.getPhoneNumber());
            statement.setString(++count, entity.getEmail());
            statement.setBoolean(++count, entity.isAccepted());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                organisation_id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "OrganisationDAO"), e);
        }
        return organisation_id;
    }

    @Override
    public boolean update(Organisation entity) {
        return false;
    }

    @Override
    public List<Organisation> query(Specification specification) {
        return null;
    }
}
