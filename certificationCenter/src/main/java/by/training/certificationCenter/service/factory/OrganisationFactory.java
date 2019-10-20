package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.bean.Organisation;

import java.io.Serializable;

public class OrganisationFactory implements Cloneable, Serializable {
    /**
     * Variable for keeping UserFactory instance.
     */
    private static final OrganisationFactory SINGLE_INSTANCE;
    static {
        SINGLE_INSTANCE = new OrganisationFactory();
    }

    private OrganisationFactory() {
    }

    public Organisation createOrganisation(
            final int id, final int unp,
            final String name, final String address,
            final long phone, final String email,
            final boolean accept) {
        Organisation org = new Organisation(id);
        org.setUnp(unp);
        org.setName(name);
        org.setAddress(address);
        org.setPhoneNumber(phone);
        org.setEmail(email);
        org.setAccepted(accept);
        return org;
    }

    public Organisation createNewClientOrganisation(
            final int unp, final String name, final String address,
            final long phone, final String email) {
        return createOrganisation(0, unp, name, address, phone,
                email, true);
    }

    public static OrganisationFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return SINGLE_INSTANCE;
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }
}
