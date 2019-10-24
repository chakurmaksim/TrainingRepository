package by.training.certificationCenter.bean;

import java.io.Serializable;
import java.util.Objects;

public class User extends CertificationEntity implements Serializable {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private long phone;
    private String mail;
    private Role role;
    private boolean actual;
    private Organisation organisation;

    public User(final int newId) {
        super(newId);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id == user.id
                && phone == user.phone
                && Objects.equals(login, user.login)
                && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname)
                && Objects.equals(mail, user.mail)
                && Objects.equals(organisation, user.organisation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, surname, phone,
                mail, organisation);
    }

    @Override
    public String toString() {
        return "User{"
                + "login='" + login + '\''
                + ", password='" + password + '\''
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", patronymic='" + patronymic + '\''
                + ", phone=" + phone
                + ", mail='" + mail + '\''
                + ", role=" + role
                + ", actual=" + actual
                + ", org=" + organisation
                + '}';
    }
}
