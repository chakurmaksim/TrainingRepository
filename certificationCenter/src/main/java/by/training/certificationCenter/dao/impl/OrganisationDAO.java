package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.service.factory.OrganisationFactory;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrganisationDAO extends CertificationMySqlDAO<Organisation> {
    private static final String FIND_BY_ID = "SELECT id, unp, name, address, "
            + "phone, email, accept FROM organisation WHERE id = ?";

    public OrganisationDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Organisation> findAll(int skip, int pageLim) {
        return null;
    }

    @Override
    public Organisation findEntityById(int id) {
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
            getLogger().error(e.toString());
        }
        return org;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(Organisation entity) {
        return false;
    }

    @Override
    public int create(Organisation entity) {
        return 0;
    }

    @Override
    public Organisation update(Organisation entity) {
        return null;
    }

    @Override
    public List<Organisation> query(Specification specification) {
        return null;
    }
}
