package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.controller.command.*;
import by.training.certificationCenter.service.configuration.PathConfiguration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UriFilter implements Filter {
    private static Map<String, Command> commands = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commands.put("/applications", new AppsListCommand());
        commands.put("/applyFor", new ApplyForCommand());
        commands.put("/chooseLanguage", new ChooseLanguageCommand());
        commands.put(("/deleteApplication"), new DeleteApplicationCommand());
        commands.put("/editApplication", new EditApplicationCommand());
        commands.put("/home", new MainCommand());
        commands.put("/index", new MainCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/showApplication", new ShowApplicationCommand());
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String urlPattern = UrlPatternUtils.getUrlPattern(httpRequest);
            Command forwardCommand = commands.get(urlPattern);
            String forwardPathName = String.format("/jsp%s.jsp", urlPattern);
            if (forwardCommand != null) {
                forwardCommand.setPathName(forwardPathName);
                httpRequest.setAttribute("command", forwardCommand);
                httpRequest.setAttribute("urlPattern", urlPattern);
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
