package by.training.certificationCenter.bean;

import java.io.Serializable;
import java.util.Objects;

public class Organisation extends CertificationEntity implements Serializable {
    /**
     * Identification number of the organisation.
     */
    private int unp;
    /**
     * Organisation name.
     */
    private String name;
    /**
     * Legal address of the organisation.
     */
    private String address;
    /**
     * Phone number.
     */
    private long phoneNumber;
    /**
     * Email address.
     */
    private String email;
    /**
     * Variable shows organisation is valid or not.
     */
    private boolean accepted;

    public Organisation(final int newId) {
        super(newId);
    }

    public int getUnp() {
        return unp;
    }

    public void setUnp(int unp) {
        this.unp = unp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Organisation that = (Organisation) o;
        return id == that.getId()
                && unp == that.unp
                && phoneNumber == that.phoneNumber
                && Objects.equals(name, that.name)
                && Objects.equals(address, that.address)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unp, name, address, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "Organisation{"
                + "unp=" + unp
                + ", name='" + name + '\''
                + ", address='" + address + '\''
                + ", phoneNumber=" + phoneNumber
                + ", email='" + email + '\''
                + ", accepted=" + accepted
                + '}';
    }
}
