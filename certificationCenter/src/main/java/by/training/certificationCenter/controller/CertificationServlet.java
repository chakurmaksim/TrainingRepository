package by.training.certificationCenter.controller;

import by.training.certificationCenter.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CertificationServlet extends HttpServlet {
    private static final String ATTR_COMMAND = "command";
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {
        Command command = (Command) request.getAttribute(ATTR_COMMAND);
        command.execute(request, response);
        if (command.isRedirect()) {
            String redirectedUri = request.getContextPath() + command.getPathName();
            response.sendRedirect(redirectedUri);
        } else {
            doGet(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = (Command) request.getAttribute(ATTR_COMMAND);
        command.execute(request, response);
        String jspPage = command.getPathName();
        getServletContext().getRequestDispatcher(jspPage).forward(request, response);
    }
}
