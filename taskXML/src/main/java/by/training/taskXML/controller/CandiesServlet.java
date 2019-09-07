package by.training.taskXML.controller;

import by.training.taskXML.bean.Candy;
import by.training.taskXML.service.CandiesAbstractBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CandiesServlet extends HttpServlet {

    /**
     * Overridden doGet() method.
     *
     * @param request  from browser
     * @param response to browser
     * @throws IOException exception
     */
    @Override
    public void doGet(final HttpServletRequest request,
                      final HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        if (request.getParameter("builder") != null) {
            String parameter = request.getParameter("builder");
            CandiesBuilderFactory factory = CandiesBuilderFactory.
                    getBuilderFactory();
            String xsdFileName = getServletContext().getRealPath("")
                    + CandiesAbstractBuilder.getXsdFileName();
            String xmlFileName = getServletContext().getRealPath("")
                    + CandiesAbstractBuilder.getXmlFileName();
            CandiesAbstractBuilder builder = factory.createBuilder(
                    parameter, xsdFileName, xmlFileName);
            builder.buildSetCandies();
            Set<Candy> candies = builder.getCandies();
            request.setAttribute("candies", candies);
            request.getRequestDispatcher("candies.jsp").
                    forward(request, response);
        }
    }
}
