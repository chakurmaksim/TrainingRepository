package by.training.certificationCenter.service.specification;

public class ProductsByAppIdSpecification implements SqlSpecification {
    private int applicationId;
    public ProductsByAppIdSpecification(final int newApplicationId) {
        this.applicationId = newApplicationId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT product.id, product_name, product_code, "
                + "producer_name, producer_address, "
                + "quantity_attribute_id, quantity_attribute_name "
                + "FROM product JOIN quantity_attribute ON "
                + "product.quantity_attribute_id = quantity_attribute.id "
                + "WHERE product.application_id = %d", applicationId);
    }
}
