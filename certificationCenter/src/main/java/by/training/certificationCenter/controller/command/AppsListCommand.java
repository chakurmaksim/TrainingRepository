package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ResourceBundle;

public class AppsListCommand extends Command {
    /**
     * The key that is required to get the user instance from the
     * request attribute.
     */
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    /**
     * Key that is required to set the error message to the request attribute.
     */
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    /**
     * Key that is required to set the application list to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_APPS = "apps";
    /**
     * The key is required to obtain the page number in order to determine
     * which applications should be displayed on the page.
     */
    private static final String PARAM_NAME_PAGE_NUM = "page";
    /**
     * Limit of applications that will be displayed on the page.
     */
    private static final int PAGE_LIMIT = 2;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        ServiceFactory factory = ServiceFactory.getInstance();
        ApplicationService service = factory.getApplicationService();
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        try {
            int currentPageNumber = Integer.parseInt(request.getParameter(
                    PARAM_NAME_PAGE_NUM));
            int skipPages = PaginationHelper.calculateSkipPages(
                    currentPageNumber, PAGE_LIMIT);
            if (skipPages < 0) {
                request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, bundle.getString(
                        "message.page.minus"));
            }
            List<Application> list = service.receiveAppsByUser(
                    user, skipPages, PAGE_LIMIT);
            int totalApps = service.receiveRowsNumber(user);
            int lastPageNumber = PaginationHelper.calculateLastPage(
                    totalApps, PAGE_LIMIT);
            request.setAttribute(ATTRIBUTE_NAME_APPS, list);
            request.setAttribute("currentPageNumber", currentPageNumber);
            request.setAttribute("lastPageNumber", lastPageNumber);
        } catch (NumberFormatException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, bundle.getString(
                    "message.page.numberFormat"));
        } catch (ServiceException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    bundle.getString(e.getMessage()));
            getLogger().error(String.format("user \"%s\" unsuccessfully "
                    + "tried to get application list", user.getLogin()));
        }
    }
}
