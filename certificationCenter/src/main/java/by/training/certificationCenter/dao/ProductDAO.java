package by.training.certificationCenter.dao;

import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends CertificationMySqlDAO<Product> {
    private static final String INSERT_PROD;
    private static final String FIND_PROD;
    private int application_id;

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
                + "WHERE product.application_id = ?";
    }

    public ProductDAO(final Connection newConnection, final int app_id) {
        super(newConnection);
        this.application_id = app_id;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_PROD)) {
            statement.setInt(1, application_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("product_name");
                long code = resultSet.getLong("product_code");
                String producer_name = resultSet.getString("producer_name");
                String producer_address = resultSet.getString("producer_address");
                int quantity_attr_id = resultSet.getInt("quantity_attribute_id");
                String quantity_attr_name = resultSet.getString("quantity_attribute_name");
                ProductFactory factory = ProductFactory.getSingleInstance();
                Product product = factory.createProduct(
                        id, name, code, producer_name, producer_address,
                        quantity_attr_id, quantity_attr_name);
                products.add(product);
            }
        } catch (SQLException e) {
            getLogger().error(e.toString());
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
    public boolean create(Product entity) {
        boolean flag = false;
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PROD)) {
            int index = 0;
            statement.setInt(++index, application_id);
            statement.setString(++index, entity.getName());
            statement.setLong(++index, entity.getCode());
            statement.setString(++index, entity.getProducer());
            statement.setString(++index, entity.getAddress());
            statement.setInt(++index, entity.getAttr().getId());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            getLogger().error(e.toString());
        }
        return flag;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public List<Product> query(Specification specification) {
        return null;
    }
}
