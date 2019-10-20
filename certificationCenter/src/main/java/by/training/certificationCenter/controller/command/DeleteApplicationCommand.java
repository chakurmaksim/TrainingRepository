package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class DeleteApplicationCommand extends Command {
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
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    /**
     * Key is required to get the application id from the request parameters.
     */
    private static final String PARAM_NAME_APP_ID = "application_id";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        ServiceFactory factory = ServiceFactory.getInstance();
        ApplicationService service = factory.getApplicationService();
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        int applicationId = 0;
        try {
            applicationId = Integer.parseInt(
                    request.getParameter(PARAM_NAME_APP_ID));
            if (service.deleteApplication(applicationId, user)) {
                setRedirect(true);
                setPathName(PathConfiguration.getProperty("path.page.success"));
                session.setAttribute(ATTRIBUTE_NAME_MESSAGE,
                        bundle.getString("message.application.deleted"));
            }
        } catch (NumberFormatException e) {
            session.setAttribute(ATTRIBUTE_ERROR_MESSAGE, bundle.getString(
                    "message.application.id.numberFormat"));
            setRedirect(true);
            setPathName(PathConfiguration.getProperty("path.page.error"));
        } catch (ServiceException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    bundle.getString(e.getMessage()));
            getLogger().error(String.format("user \"%s\" unsuccessfully "
                            + "tried to delete application with id \"%s\"",
                    user.getLogin(), applicationId));
        }
    }
}
