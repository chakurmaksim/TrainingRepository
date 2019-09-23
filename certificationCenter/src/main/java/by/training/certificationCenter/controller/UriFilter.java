package by.training.certificationCenter.controller;

import by.training.certificationCenter.controller.command.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UriFilter implements Filter {
    private static Map<String, Command> commands = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commands.put("/index", new MainCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/applications", new ClientAppsListCommand());
        commands.put("/showApplication", new ShowApplicationCommand());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String commandName;
            if (endAction >= 0) {
                commandName = uri.substring(beginAction, endAction);
                System.out.println("Command name: " + commandName);
            } else {
                commandName = uri.substring(beginAction) + "index";
                System.out.println("Empty command name: " + commandName);
            }
            Command command = commands.get(commandName);
            String forwardPathName = String.format("/jsp%s.jsp", commandName);
            if (command != null) {
                command.setForwardPathName(forwardPathName);
                httpRequest.setAttribute("command", command);
                chain.doFilter(request, response);
            } else {
                httpRequest.setAttribute("error", String.format(
                        "Запрошенный адрес %s не может быть обработан сервером", uri));
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
