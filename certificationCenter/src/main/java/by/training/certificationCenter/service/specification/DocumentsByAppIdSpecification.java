package by.training.certificationCenter.service.specification;

public class DocumentsByAppIdSpecification implements SqlSpecification {
    /**
     * Application id variable.
     */
    private int applicationId;

    public DocumentsByAppIdSpecification(final int newApplicationId) {
        this.applicationId = newApplicationId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT id, storage FROM documentation WHERE "
                + "application_id = %d", applicationId);
    }
}
