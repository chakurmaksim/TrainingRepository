package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.service.configuration.PathConfiguration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends Command {
    private static final String PARAM_NAME_LOCALE = "locale";
    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        request.getSession(false).invalidate();
        setRedirect(true);
        setPathName(PathConfiguration.getProperty("path.page.index"));
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(PARAM_NAME_LOCALE)) {
                    String locale = cookie.getValue();
                    request.getSession().setAttribute(
                            PARAM_NAME_LOCALE, locale);
                }
            }
        }
    }
}
