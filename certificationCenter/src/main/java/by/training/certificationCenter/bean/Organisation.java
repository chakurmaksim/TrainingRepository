package by.training.certificationCenter.bean;

import java.io.Serializable;

public class Organisation extends CertificationEntity implements Serializable {
    private final int unp;
    private final String name;
    private String address;
    private long phoneNumber;
    private String email;
    private boolean accepted;

    public Organisation(final int newId,
                        final int newUnp,
                        final String newName) {
        super(newId);
        this.unp = newUnp;
        this.name = newName;
    }

    public int getUnp() {
        return unp;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Юридическое лицо{"
                + "id=" + id
                + ", УНП=" + unp
                + ", Наименование='" + name + '\''
                + ", Место нахождения='" + address + '\''
                + ", Телефон=" + phoneNumber
                + ", Адрес электронной почты='" + email + '\''
                + ", Действующая=" + accepted
                + '}';
    }
}
