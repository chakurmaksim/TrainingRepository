package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChooseLanguageCommand extends Command {
    private static final String PARAM_NAME_PATH = "servletPath";
    private static final String PARAM_NAME_LOCALE = "locale";
    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        String servletPath = request.getParameter(PARAM_NAME_PATH);
        setPathName(servletPath);
        String locale = request.getParameter(PARAM_NAME_LOCALE);
        HttpSession session = request.getSession(false);
        session.setAttribute(PARAM_NAME_LOCALE, locale);
        Cookie localeCookie = new Cookie(PARAM_NAME_LOCALE, locale);
        response.addCookie(localeCookie);
        /*Application application = (Application) request.getAttribute("application");
        System.out.println(application);
        if (application != null) {
            request.setAttribute("application", application);
        }*/
    }
}
