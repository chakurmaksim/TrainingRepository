package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.service.configuration.PathConfiguration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainCommand extends Command {
    /**
     * The key is required to obtain the current page locale.
     */
    private static final String PARAM_NAME_LOCALE = "locale";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String locale = PathConfiguration.getProperty("default.page.locale");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(PARAM_NAME_LOCALE)) {
                    locale = cookie.getValue();
                }
            }
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(PARAM_NAME_LOCALE) == null) {
            session.setAttribute(PARAM_NAME_LOCALE, locale);
        }
    }
}
