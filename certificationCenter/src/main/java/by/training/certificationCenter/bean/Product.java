package by.training.certificationCenter.bean;

import java.io.Serializable;
import java.util.Objects;

public class Product extends CertificationEntity implements Serializable {
    /**
     * Product name.
     */
    private String name;
    /**
     * Product code commodity nomenclature of foreign economic activity.
     */
    private long code;
    /**
     * Producer name.
     */
    private String producer;
    /**
     * Producer location.
     */
    private String address;
    /**
     * Release sign.
     */
    private QuantityAttribute attr;
    /**
     * Link to the application to which this product belongs.
     */
    private Application application;

    public Product(final int newId) {
        super(newId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public QuantityAttribute getAttr() {
        return attr;
    }

    public void setAttr(QuantityAttribute attr) {
        this.attr = attr;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application newApplication) {
        this.application = newApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return id == product.id
                && code == product.code
                && attr.getId() == product.attr.getId()
                && Objects.equals(name, product.name) &&
                Objects.equals(producer, product.producer) &&
                Objects.equals(address, product.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, producer, address, attr.getId());
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", code=" + code
                + ", producer='" + producer + '\''
                + ", address='" + address + '\''
                + ", attrId=" + attr.getId()
                + '}';
    }
}
