package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class LoginCommand extends Command {
    /**
     * Key is required to get the user login from the request parameters.
     */
    private static final String PARAM_LOGIN = "login";
    /**
     * Key is required to get the user password from the request parameters.
     */
    private static final String PARAM_PASS = "password";
    /**
     * Key is required to remember user login and password.
     */
    private static final String PARAM_REMEMBER = "rememberMe";
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
     * Key is required to remember user login into the Cookies.
     */
    private static final String COOKIES_SAVE_LOGIN = "savedLogin";
    /**
     * Key is required to remember user password into the Cookies.
     */
    private static final String COOKIES_SAVE_PASSWORD = "savedPassword";


    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIES_SAVE_LOGIN)) {
                    request.setAttribute(COOKIES_SAVE_LOGIN, cookie.getValue());
                }
                if (cookie.getName().equals(COOKIES_SAVE_PASSWORD)) {
                    request.setAttribute(COOKIES_SAVE_PASSWORD,
                            cookie.getValue());
                }
            }
        }
        HttpSession session = request.getSession();
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASS);
        String checkBox = request.getParameter(PARAM_REMEMBER);
        if (login != null && password != null && !login.trim().equals("")) {
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService service = factory.getUserService();
            try {
                User user = service.signIn(login, password);
                if (user != null) {
                    rememberLoginAndPassword(response, login,
                            password, checkBox);
                    setRedirect(true);
                    setPathName(PathConfiguration.getProperty(
                            "path.page.success"));
                    session.setAttribute(ATTRIBUTE_NAME_USER, user);
                    getLogger().info(String.format(
                            "user \"%s\" is logged in from %s (%s:%s)",
                            login, request.getRemoteAddr(),
                            request.getRemoteHost(), request.getRemotePort()));
                } else {
                    request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                            bundle.getString(
                                    "message.user.login.recognized"));
                    getLogger().info(String.format("user \"%s\" unsuccessfully "
                                    + "tried to log in from %s (%s:%s)",
                            login, request.getRemoteAddr(),
                            request.getRemoteHost(), request.getRemotePort()));
                }
            } catch (ServiceException e) {
                request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                        bundle.getString(e.getMessage()));
                getLogger().error(String.format("user \"%s\" unsuccessfully "
                                + "tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(),
                        request.getRemoteHost(), request.getRemotePort()));
            }
        } else if (login != null && login.trim().equals("")) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    bundle.getString("message.user.login.empty"));
        }
    }

    private void rememberLoginAndPassword(HttpServletResponse response,
                                          String login, String password,
                                          String checkBox) {
        if (checkBox != null && Boolean.valueOf(checkBox)) {
            Cookie cookieLogin = new Cookie(COOKIES_SAVE_LOGIN, login);
            Cookie cookiePass = new Cookie(COOKIES_SAVE_PASSWORD, password);
            response.addCookie(cookieLogin);
            response.addCookie(cookiePass);
        }
    }
}
