package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.controller.command.*;
import by.training.certificationCenter.service.configuration.PathConfiguration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UriFilter implements Filter {
    private static Map<String, Command> commands = new ConcurrentHashMap<>();
    private static final String ATTR_NAME_COMMAND = "command";
    private static final String ATTR_NAME_URL_PATTERN = "urlPattern";
    private static final String ATTR_NAME_APPLICATION = "application";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commands.put("/applications", new AppsListCommand());
        commands.put("/applyFor", new ApplyForCommand());
        commands.put("/chooseLanguage", new ChooseLanguageCommand());
        commands.put(("/deleteApplication"), new DeleteApplicationCommand());
        commands.put("/downloadDocument", new DownloadDocumentCommand());
        commands.put("/editApplication", new EditApplicationCommand());
        commands.put("/success", new MainCommand());
        commands.put("/index", new MainCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/showApplication", new ShowApplicationCommand());
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession(false);
            String urlPattern = UrlPatternUtils.getUrlPattern(httpRequest);
            if (session != null && !urlPattern.equals("/chooseLanguage")) {
                session.removeAttribute(ATTR_NAME_APPLICATION);
            }
            Command forwardCommand = commands.get(urlPattern);
            String forwardPathName = String.format("/jsp%s.jsp", urlPattern);
            if (forwardCommand != null) {
                forwardCommand.setPathName(forwardPathName);
                forwardCommand.setRedirect(false);
                httpRequest.setAttribute(ATTR_NAME_COMMAND, forwardCommand);
                httpRequest.setAttribute(ATTR_NAME_URL_PATTERN, urlPattern);
                chain.doFilter(request, response);
            } else {
                httpRequest.setAttribute("error", String.format(
                        "Запрошенный адрес %s не может быть обработан сервером",
                        httpRequest.getRequestURI()));
                httpRequest.getServletContext().getRequestDispatcher(
                        PathConfiguration.getProperty("path.page.error")).
                        forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
