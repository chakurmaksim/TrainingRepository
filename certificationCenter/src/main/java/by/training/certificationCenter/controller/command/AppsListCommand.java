package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AppsListCommand extends Command {
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    private static final String ATTRIBUTE_NAME_APPS = "apps";
    private static final String PARAM_NAME_PAGE_NUM = "page";
    private static final int PAGE_LIMIT = 5;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        ServiceFactory factory = ServiceFactory.getInstance();
        ApplicationService service = factory.getApplicationService();
        try {
            int pageNum = Integer.parseInt(
                    request.getParameter(PARAM_NAME_PAGE_NUM));
            List<Application> list = service.receiveAppsByUserId(
                    user, pageNum, PAGE_LIMIT);
            request.setAttribute(ATTRIBUTE_NAME_APPS, list);
        } catch (NumberFormatException | ServiceException e) {
            getLogger().error(e.toString());
        }
    }
}
