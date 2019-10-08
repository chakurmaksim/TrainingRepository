package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowApplicationCommand extends Command {
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    private static final String ATTRIBUTE_NAME_APP = "application";
    private static final String PARAM_NAME_APP_ID = "application_id";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        ServiceFactory factory = ServiceFactory.getInstance();
        ApplicationService service = factory.getApplicationService();
        try {
            int applicationId = Integer.parseInt(
                    request.getParameter(PARAM_NAME_APP_ID));
            Application application = service.showApplicationById(
                    applicationId, user);
            request.setAttribute(ATTRIBUTE_NAME_APP, application);
        } catch (NumberFormatException | ServiceException e) {
            getLogger().error(e.toString());
        }
    }
}
