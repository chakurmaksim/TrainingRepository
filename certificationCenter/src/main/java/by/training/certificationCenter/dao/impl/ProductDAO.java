package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.specification.Specification;
import by.training.certificationCenter.service.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends CertificationMySqlDAO<Product> {
    private static final String INSERT_PROD = "INSERT INTO product("
            + "application_id, product_name, product_code, producer_name, "
            + "producer_address, quantity_attribute_id) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_PROD = "SELECT product.id, product_name, "
            + "product_code, producer_name, producer_address, "
            + "quantity_attribute_id, quantity_attribute_name FROM product "
            + "JOIN quantity_attribute ON product.quantity_attribute_id = "
            + "quantity_attribute.id ORDER BY id DESC LIMIT ?, ?";
    private static final String REMOVE_PROD = "DELETE FROM product "
            + "WHERE id = ?";
    private static final String UPDATE_PROD = "UPDATE product SET "
            + "product_name = ?, product_code = ?, producer_name = ?, "
            + "producer_address = ?, quantity_attribute_id = ? WHERE id = ?";

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
            throw new DAOException(getStatementError()
                    + " at find all products", e);
        }
        return products;
    }

    @Override
    public Product findEntityById(int id) {
        return null;
    }

    @Override
    public boolean remove(int id) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(REMOVE_PROD)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(getStatementError()
                    + " at remove product by id", e);
        }
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
                prepareStatement(INSERT_PROD,
                        Statement.RETURN_GENERATED_KEYS)) {
            int index = 0;
            statement.setInt(++index, entity.getApplicationId());
            statement.setString(++index, entity.getName());
            statement.setLong(++index, entity.getCode());
            statement.setString(++index, entity.getProducer());
            statement.setString(++index, entity.getAddress());
            statement.setInt(++index, entity.getAttr().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                productId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(getStatementError()
                    + " at create product into database", e);
        }
        return productId;
    }

    @Override
    public Product update(Product entity) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(UPDATE_PROD)) {
            int index = 0;
            statement.setString(++index, entity.getName());
            statement.setLong(++index, entity.getCode());
            statement.setString(++index, entity.getProducer());
            statement.setString(++index, entity.getAddress());
            statement.setInt(++index, entity.getAttr().getId());
            statement.setInt(++index, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(getStatementError()
                    + " at update product", e);
        }
        return entity;
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
        int id = resultSet.getInt("id");
        String name = resultSet.getString("product_name");
        long code = resultSet.getLong("product_code");
        String producer_name = resultSet.getString("producer_name");
        String producer_address = resultSet.getString(
                "producer_address");
        int quantity_attr_id = resultSet.getInt(
                "quantity_attribute_id");
        String quantity_attr_name = resultSet.getString(
                "quantity_attribute_name");
        ProductFactory factory = ProductFactory.getSingleInstance();
        Product product = factory.createProduct(
                id, name, code, producer_name, producer_address,
                quantity_attr_id, quantity_attr_name);
        return product;
    }
}
