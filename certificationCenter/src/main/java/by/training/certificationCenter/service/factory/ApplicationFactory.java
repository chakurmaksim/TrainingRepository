package by.training.certificationCenter.service.factory;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.Status;
import by.training.certificationCenter.bean.User;

import java.io.Serializable;
import java.sql.Date;

public class ApplicationFactory implements Cloneable, Serializable {
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

    public Application createApplication(
            final int id, final int userId, final int orgId,
            final int regNum, final Date dateAdd,
            final String requirements, final Date dateResolve,
            final int statusInd) {
        Application application = new Application(id);
        User user = new User(userId, null);
        Organisation org = new Organisation(orgId, -1, null);
        application.setReg_num(regNum);
        if (dateAdd != null) {
            application.setDate_add(dateAdd.toLocalDate());
        }
        application.setRequirements(requirements);
        if (dateResolve != null) {
            application.setDate_resolve(dateResolve.toLocalDate());
        }
        application.setStatus(Status.getByIdentity(statusInd));
        application.setExecutor(user);
        application.setOrg(org);
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
