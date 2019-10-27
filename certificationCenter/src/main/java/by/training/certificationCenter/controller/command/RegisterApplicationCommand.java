package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterApplicationCommand extends Command {
    /**
     * The key that is required to get the user instance from the
     * session attribute.
     */
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    /**
     * Key that is required to set the message to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_MESSAGE = "message";
    /**
     * Key that is required to set the error message to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_ERROR = "errorMessage";
    /**
     * The key is required to obtain the relative servlet path.
     */
    private static final String PARAM_NAME_PATH = "servletPath";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        String servletPath = request.getParameter(PARAM_NAME_PATH);
        setPathName(servletPath);
        String applicationIdParam = request.getParameter(
                "application_id");
        if (applicationIdParam == null) {
            return;
        }
        String regNumberParam = request.getParameter(
                "registration_number");
        String resolveDateParam = request.getParameter("date_resolve");
        String statusIndexParam = request.getParameter(
                "application_status_index");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        List<Application> applicationList = (List<Application>) session.
                getAttribute("applicationList");
        session.removeAttribute("applicationList");
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        ApplicationService service = ServiceFactory.getInstance().
                getApplicationService();
        int applicationId;
        try {
            applicationId = Integer.parseInt(applicationIdParam);
        } catch (NumberFormatException e) {
            session.setAttribute(ATTRIBUTE_NAME_ERROR, bundle.getString(
                    "message.application.id.numberFormat"));
            setRedirect(true);
            setPathName(PathConfiguration.getProperty("path.page.error"));
            return;
        }
        int regNumber = 0;
        try {
            regNumber = Integer.parseInt(regNumberParam);
        } catch (NumberFormatException e) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR, bundle.getString(
                    "message.application.registration.numberFormat"));
            request.setAttribute("apps", applicationList);
            return;
        }
        String[] resolveDateParamArray = resolveDateParam.split("/");
        if (resolveDateParamArray.length != 3) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR, bundle.getString(
                    "message.application.date.mistake"));
            request.setAttribute("apps", applicationList);
            return;
        }
        LocalDate resolveDate;
        try {
            int day = Integer.parseInt(resolveDateParamArray[0]);
            int month = Integer.parseInt(resolveDateParamArray[1]);
            int year = Integer.parseInt(resolveDateParamArray[2]);
            resolveDate = LocalDate.of(year, month, day);
        } catch (NumberFormatException e) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR, bundle.getString(
                    "message.application.date.mistake"));
            request.setAttribute("apps", applicationList);
            return;
        }
        int statusInd;
        try {
            statusInd = Integer.parseInt(statusIndexParam);
        } catch (NumberFormatException e) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR, bundle.getString(
                    "message.application.status.index.numberFormat"));
            request.setAttribute("apps", applicationList);
            return;
        }
        try {
            if (service.registerApplication(user, applicationId, regNumber,
                    resolveDate, statusInd)) {
                setRedirect(true);
                setPathName(PathConfiguration.getProperty("path.page.success"));
                session.setAttribute(ATTRIBUTE_NAME_MESSAGE,
                        bundle.getString("message.application.registered"));
                getLogger().info(String.format("user expert \"%s\" successfully"
                                + " registered application id \"%d\" ",
                        user.getLogin(), applicationId));
            }
        } catch (ServiceException e) {
            setRedirect(true);
            setPathName(PathConfiguration.getProperty("path.page.error"));
            session.setAttribute(ATTRIBUTE_NAME_ERROR,
                    bundle.getString(e.getMessage()));
            getLogger().error(String.format("user expert \"%s\" unsuccessfully "
                    + "tried to register application id \"%d\"",
                    user.getLogin(), applicationId));
        }
    }
}
