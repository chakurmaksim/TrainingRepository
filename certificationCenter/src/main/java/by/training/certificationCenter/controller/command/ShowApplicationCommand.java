package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.action.ShowApplicationAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ShowApplicationCommand extends Command {
    private static final String PARAM_NAME_PAGE = "application_id";
    private ShowApplicationAction action = new ShowApplicationAction();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authorizedUser");
        try {
            int applicationId = Integer.parseInt(request.getParameter(PARAM_NAME_PAGE));
            Application application = action.findApplicationById(applicationId, user);
            request.setAttribute("application", application);
        } catch (NumberFormatException | SQLException e) {
            getLogger().error(e.toString());
        }
    }
}
