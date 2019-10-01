package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.controller.command.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UriFilter implements Filter {
    private static Map<String, Command> forwardCommands = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        forwardCommands.put("/applications", new AppsListCommand());
        forwardCommands.put("/applyFor", new ApplyForCommand());
        forwardCommands.put("/home", new MainCommand());
        forwardCommands.put("/index", new MainCommand());
        forwardCommands.put("/login", new LoginCommand());
        forwardCommands.put("/showApplication", new ShowApplicationCommand());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            System.out.println(httpRequest.getServletPath());
            String urlPattern = UrlPatternUtils.getUrlPattern(httpRequest);
            Command forwardCommand = forwardCommands.get(urlPattern);
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
                        "/jsp/error.jsp").forward(request, response);
            }
        } else {

        }
    }

    @Override
    public void destroy() {

    }
}
