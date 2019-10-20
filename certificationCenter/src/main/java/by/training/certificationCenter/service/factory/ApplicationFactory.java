package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.Status;
import by.training.certificationCenter.bean.User;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public final class ApplicationFactory implements Cloneable, Serializable {
    /**
     * Variable for keeping ProductFactory instance.
     */
    private static final ApplicationFactory SINGLE_INSTANCE;
    static {
        SINGLE_INSTANCE = new ApplicationFactory();
    }
    private ApplicationFactory() {
    }

    public static ApplicationFactory getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    public void buildAppWithUserAndOrg(
            final Application application, final int userId, final int orgId,
            final String requirements) {
        User user = new User(userId);
        Organisation org = new Organisation(orgId);
        user.setOrganisation(org);
        application.setExecutor(user);
        application.setOrganisation(org);
        application.setRequirements(requirements);
    }

    public Application createDemoApplication(
            final int id, final int regNum, final Date dateAdd,
            final Date dateResolve, final int statusInd) {
        Application application = new Application(id);
        application.setReg_num(regNum);
        if (dateAdd != null) {
            application.setDate_add(dateAdd.toLocalDate());
        }
        if (dateResolve != null) {
            application.setDate_resolve(dateResolve.toLocalDate());
        }
        application.setStatus(Status.getByIdentity(statusInd));
        return application;
    }

    public Application createNewClientApplication(final LocalDate dateAdd,
                                                  final User user,
                                                  final String requirements) {
        Application application = new Application(0);
        application.setDate_add(dateAdd);
        application.setExecutor(user);
        application.setOrganisation(user.getOrganisation());
        application.setRequirements(requirements);
        application.setStatus(Status.getByIdentity(0));
        return application;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return SINGLE_INSTANCE;
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }
}
