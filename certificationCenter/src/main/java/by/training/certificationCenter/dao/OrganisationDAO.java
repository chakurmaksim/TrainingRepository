package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.service.factory.OrganisationFactory;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.*;
import java.util.List;

public class OrganisationDAO extends CertificationMySqlDAO<Organisation> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_UNP = "unp";
    private static final String COLUMN_ORG_NAME = "name";
    private static final String COLUMN_ORG_ADDR = "address";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_MAIL = "email";
    private static final String COLUMN_ACCEPT = "accept";
    private static final String FIND_BY_ID;
    static {
        FIND_BY_ID = "SELECT * FROM organisation WHERE id = ?";
    }
    public OrganisationDAO(Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Organisation> findAll() {
        return null;
    }

    @Override
    public Organisation findEntityById(int id) {
        Organisation org = null;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            int index = 0;
            statement.setInt(++index, id);
            try (ResultSet resultSet = (ResultSet) statement.executeQuery()) {
                if (resultSet.next()) {
                    int currId = resultSet.getInt(COLUMN_ID);
                    int unp = resultSet.getInt(COLUMN_UNP);
                    String name = resultSet.getString(COLUMN_ORG_NAME);
                    String address = resultSet.getString(COLUMN_ORG_ADDR);
                    long phone = resultSet.getLong(COLUMN_PHONE);
                    String email = resultSet.getString(COLUMN_MAIL);
                    boolean accept = resultSet.getBoolean(COLUMN_ACCEPT);
                    OrganisationFactory factory = OrganisationFactory.getSingleInstance();
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
    public boolean create(Organisation entity) {
        return false;
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
