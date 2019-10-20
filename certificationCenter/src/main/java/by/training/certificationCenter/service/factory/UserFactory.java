package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.User;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public final class UserFactory implements Cloneable, Serializable {
    /**
     * Variable for keeping UserFactory instance.
     */
    private static final UserFactory SINGLE_INSTANCE;
    static {
        SINGLE_INSTANCE = new UserFactory();
    }
    private UserFactory() {
    }

    public static UserFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    public User createUser(
            final int id, final int orgId, final String login,
            final String name, final String surname,
            final String patronymic, final long phone,
            final String email, final int roleInd, final boolean actual) {
        User user = new User(id);
        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setPhone(phone);
        user.setMail(email);
        Optional<Role> optRole = Arrays.stream(Role.values()).filter(
                r -> r.getIndex() == roleInd).findAny();
        Role role = optRole.get();
        user.setRole(role);
        user.setActual(actual);
        user.setOrganisation(new Organisation(orgId));
        return user;
    }

    public User createNewUser(final String login, final String name,
                              final String surname, final String patronymic,
                              final long phone, final String email,
                              final int roleInd, final String password) {
        User user = createUser(0, 0, login, name, surname,
                patronymic, phone, email, roleInd, true);
        user.setPassword(password);
        return user;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return SINGLE_INSTANCE;
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }
}
