package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASS = "password";
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASS);
        if (login != null && password != null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService service = factory.getUserService();
            try {
                User user = service.signIn(login, password);
                if (user != null) {
                    setRedirect(true);
                    setPathName(PathConfiguration.getProperty(
                            "path.page.home"));
                    HttpSession session = request.getSession();
                    session.setAttribute(ATTRIBUTE_NAME_USER, user);
                    getLogger().info(String.format(
                            "user \"%s\" is logged in from %s (%s:%s)",
                            login, request.getRemoteAddr(),
                            request.getRemoteHost(), request.getRemotePort()));
                } else {
                    request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                            "User name or password are not recognized");
                    getLogger().info(String.format("user \"%s\" unsuccessfully "
                                    + "tried to log in from %s (%s:%s)",
                            login, request.getRemoteAddr(),
                            request.getRemoteHost(), request.getRemotePort()));
                }
            } catch (ServiceException e) {
                request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
                getLogger().error(String.format("user \"%s\" unsuccessfully "
                                + "tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(),
                        request.getRemoteHost(), request.getRemotePort()));
            }
        }
    }
}
