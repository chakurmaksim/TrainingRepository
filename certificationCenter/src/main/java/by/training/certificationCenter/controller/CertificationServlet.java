package by.training.certificationCenter.controller;

import by.training.certificationCenter.controller.command.Command;
import by.training.certificationCenter.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CertificationServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jspPage;
        try {
            Command command = (Command) request.getAttribute("command");
            command.execute(request, response);
            jspPage = command.getForwardPathName();
            getServletContext().getRequestDispatcher(jspPage).forward(request, response);
        } catch (CommandException e) {
            logger.error(e.toString());
        }
    }
}
