package by.training.certificationCenter.service.specification;

public class AppsByUserIdSpecification
        implements SqlSpecification {
    private final int userId;
    private final int pageLimit;
    private final int skip;

    public AppsByUserIdSpecification(
            final int newUserId,
            final int newSkip,
            final int newPageLimit) {
        this.userId = newUserId;
        this.pageLimit = newPageLimit;
        skip = newSkip;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT id, date_add, registration_number, "
                + "date_resolve, application_status FROM application "
                + "WHERE user_id = %d ORDER BY id DESC "
                + "LIMIT  %d,%d", userId, skip, pageLimit);
    }
}
