package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.QuantityAttribute;

import java.io.Serializable;

public class ProductFactory implements Cloneable, Serializable {
    /**
     * Variable for keeping ProductFactory instance.
     */
    private static final ProductFactory SINGLE_INSTANCE;
    static {
        SINGLE_INSTANCE = new ProductFactory();
    }
    private ProductFactory() {
    }

    public static ProductFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    public Product createProduct(
            final int id, final String name,
            final long code, final String producer,
            final String address, final int quantity_attr_id,
            final String quantity_attr_name) {
        Product product = new Product(id);
        QuantityAttribute attribute = new QuantityAttribute(
                quantity_attr_id, quantity_attr_name);
        product.setName(name);
        product.setCode(code);
        product.setProducer(producer);
        product.setAddress(address);
        product.setAttr(attribute);
        return product;
    }

    public Product createNewClientProduct(
            final String name, final long code, final String producer,
            final String address, final int quantity_attr_id,
            final Application application) {
        Product product = createProduct(0, name, code, producer, address,
                quantity_attr_id, null);
        product.setApplication(application);
        return product;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return SINGLE_INSTANCE;
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }
}
