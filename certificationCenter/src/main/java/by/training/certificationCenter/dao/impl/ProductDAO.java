package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.SqlSpecification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends CertificationMySqlDAO<Product> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PROD_NAME = "product_name";
    private static final String COLUMN_PROD_CODE = "product_code";
    private static final String COLUMN_PRODUCER = "producer_name";
    private static final String COLUMN_ADDR = "producer_address";
    private static final String COLUMN_ATTR_ID = "quantity_attribute_id";
    private static final String COLUMN_ATTR_NAME = "quantity_attribute_name";
    private static final String INSERT_PROD;
    private static final String FIND_PROD;

    static {
        INSERT_PROD = "INSERT INTO product(application_id, "
                + "product_name, product_code, producer_name, "
                + "producer_address, quantity_attribute_id) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        FIND_PROD = "SELECT product.id, product_name, product_code, "
                + "producer_name, producer_address, "
                + "quantity_attribute_id, quantity_attribute_name "
                + "FROM product JOIN quantity_attribute ON "
                + "product.quantity_attribute_id = quantity_attribute.id "
                + "ORDER BY id DESC LIMIT ?,?";
    }

    public ProductDAO(final Connection newConnection) {
        super(newConnection);
    }

    @Override
    public List<Product> findAll(int skip, int pageLim) throws DAOException {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_PROD)) {
            statement.setInt(1, skip);
            statement.setInt(2, pageLim);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = receiveProduct(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(), e);
        }
        return products;
    }

    @Override
    public Product findEntityById(int id) {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(Product entity) {
        return false;
    }

    @Override
    public int create(Product entity) throws DAOException {
        int productId = 0;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(INSERT_PROD)) {
            int index = 0;
            statement.setInt(++index, entity.getApplicationId());
            statement.setString(++index, entity.getName());
            statement.setLong(++index, entity.getCode());
            statement.setString(++index, entity.getProducer());
            statement.setString(++index, entity.getAddress());
            statement.setInt(++index, entity.getAttr().getId());
            productId = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(getStatementError()
                    + " create product into database", e);
        }
        return productId;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public List<Product> query(Specification specification)
            throws DAOException {
        SqlSpecification sqlSpec = (SqlSpecification) specification;
        Connection connection = getConnection();
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                    sqlSpec.toSqlQuery())) {
                while (resultSet.next()) {
                    Product product = receiveProduct(resultSet);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError()
                    + " at query product list", e);
        }
        return products;
    }

    private Product receiveProduct(final ResultSet resultSet)
            throws SQLException {
        int id = resultSet.getInt(COLUMN_ID);
        String name = resultSet.getString(COLUMN_PROD_NAME);
        long code = resultSet.getLong(COLUMN_PROD_CODE);
        String producer_name = resultSet.getString(COLUMN_PRODUCER);
        String producer_address = resultSet.getString(COLUMN_ADDR);
        int quantity_attr_id = resultSet.getInt(COLUMN_ATTR_ID);
        String quantity_attr_name = resultSet.getString(COLUMN_ATTR_NAME);
        ProductFactory factory = ProductFactory.getSingleInstance();
        Product product = factory.createProduct(
                id, name, code, producer_name, producer_address,
                quantity_attr_id, quantity_attr_name);
        return product;
    }
}
