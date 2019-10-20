package by.training.certificationCenter.dao.impl;

import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.dao.CertificationMySqlDAO;
import by.training.certificationCenter.dao.exception.DAOException;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.specification.ProductsByAppIdSpecification;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends CertificationMySqlDAO<Product> {
    /**
     * The variable contains a database query to add a new product.
     */
    private static final String INSERT_PROD = "INSERT INTO product("
            + "application_id, product_name, product_code, producer_name, "
            + "producer_address, quantity_attribute_id) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    /**
     * The variable contains a database query to find products.
     */
    private static final String FIND_PROD = "SELECT product.id, product_name, "
            + "product_code, producer_name, producer_address, "
            + "quantity_attribute_id, quantity_attribute_name FROM product "
            + "JOIN quantity_attribute ON product.quantity_attribute_id = "
            + "quantity_attribute.id ORDER BY id DESC LIMIT ?, ?";
    /**
     * The variable contains a database query to remove a product by id.
     */
    private static final String REMOVE_PROD = "DELETE FROM product "
            + "WHERE id = ?";
    /**
     * The variable contains a database query to update a product by id.
     */
    private static final String UPDATE_PROD = "UPDATE product SET "
            + "product_name = ?, product_code = ?, producer_name = ?, "
            + "producer_address = ?, quantity_attribute_id = ? WHERE id = ?";

    public ProductDAO(final Connection newConnection) {
        super(newConnection);
    }

    /**
     * Method allows to find a limited list of products.
     *
     * @param skipPages number of pages that needs to skip
     * @param pageLim   number of rows (products) at the page
     * @return list of products
     * @throws DAOException when occurs a database access error
     */
    @Override
    public List<Product> findAll(int skipPages, int pageLim)
            throws DAOException {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(FIND_PROD)) {
            statement.setInt(1, skipPages);
            statement.setInt(2, pageLim);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(receiveProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ProductDAO"), e);
        }
        return products;
    }

    /**
     * Method has to allow to find a product by id.
     *
     * @param id product id
     * @return Product instance
     * @throws DAOException the method is not supported now
     */
    @Override
    public Product findEntityById(int id) throws DAOException {
        throw new DAOException(DAOException.getUnsupportedOperation(
                "findEntityById", "ProductDAO"));
    }

    /**
     * Method allows to remove a product by id from the database.
     *
     * @param id product id
     * @return true if entity was successfully removed from the database
     * @throws DAOException when occurs a database access error
     */
    @Override
    public boolean remove(int id) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(REMOVE_PROD)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ProductDAO"), e);
        }
    }

    /**
     * Method allows to add a new product in to the database.
     *
     * @param entity Product instance
     * @return generated id by the database
     * @throws DAOException when occurs a database access error
     */
    @Override
    public int create(Product entity) throws DAOException {
        int productId = 0;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.
                prepareStatement(INSERT_PROD,
                        Statement.RETURN_GENERATED_KEYS)) {
            int index = 0;
            statement.setInt(++index, entity.getApplication().getId());
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
            throw new DAOException(getStatementError(
                    "ProductDAO"), e);
        }
        return productId;
    }

    /**
     * Method allows to update a product fields.
     *
     * @param entity updated Product instance
     * @return Product instance if operation finished successfully
     * @throws DAOException when occurs a database access error
     */
    @Override
    public boolean update(Product entity) throws DAOException {
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
            return true;
        } catch (SQLException e) {
            throw new DAOException(getStatementError(
                    "ProductDAO"), e);
        }
    }

    /**
     * Method allows to get a specific query. In this case to find a limited
     * list of products by application id.
     *
     * @param specification Specification instance
     * @return list of products
     * @throws DAOException when occurs a database access error
     */
    @Override
    public List<Product> query(Specification specification)
            throws DAOException {
        List<Product> products = new ArrayList<>();
        if (specification instanceof ProductsByAppIdSpecification) {
            ProductsByAppIdSpecification
                    prodByAppId = (ProductsByAppIdSpecification) specification;
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                        prodByAppId.toSqlQuery())) {
                    while (resultSet.next()) {
                        products.add(receiveProduct(resultSet));
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(getStatementError(
                        "ProductDAO"), e);
            }
        }
        return products;
    }

    private Product receiveProduct(final ResultSet resultSet)
            throws DAOException {
        try {
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
        } catch (SQLException e) {
            throw new DAOException(getColumnLabelError(
                    "ProductDAO"), e);
        }
    }
}
