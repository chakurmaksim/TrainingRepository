package by.training.certificationCenter.controller;

import by.training.certificationCenter.controller.command.Command;
import by.training.certificationCenter.dao.pool.ConnectionPoolWrapper;
import by.training.certificationCenter.dao.pool.TomcatConnectionPoolWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CertificationServlet extends HttpServlet {
    /**
     * Attribute name to get the certain object of the Command class
     * from the HttpServletRequest instance.
     */
    private static final String ATTR_NAME_COMMAND = "command";
    private static ConnectionPoolWrapper wrapper;

    public void init() {
        wrapper = TomcatConnectionPoolWrapper.getInstance();
        wrapper.initialPool();
    }

    public void destroy() {
        wrapper.closePool();
    }

    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = (Command) request.getAttribute(ATTR_NAME_COMMAND);
        command.execute(request, response);
        String jspPage = command.getPathName();
        if (command.isRedirect()) {
            String redirectedUri = request.getContextPath() + jspPage;
            response.sendRedirect(redirectedUri);
        } else {
            getServletContext().getRequestDispatcher(jspPage).
                    forward(request, response);
        }
    }
}
