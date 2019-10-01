package by.training.certificationCenter.service.specification;

public class UserByLoginSpecification implements SqlSpecification {
    private String login;
    private String password;
    public UserByLoginSpecification(final String newLogin,
                                    final String newPassword) {
        this.login = newLogin;
        this.password = newPassword;
    }
    @Override
    public String toSqlQuery() {
        return String.format("SELECT id, organisation_id, login, name, "
                + "surname, patronymic, phone, email, user_role, actual FROM "
                + "user WHERE login = '%s' AND password = '%s'",
                login, password);
    }
}
