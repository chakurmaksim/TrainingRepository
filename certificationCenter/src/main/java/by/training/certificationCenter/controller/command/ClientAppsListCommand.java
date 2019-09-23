package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.action.ClientAppsListAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class ClientAppsListCommand extends Command {
    private static final String PARAM_NAME_PAGE = "page";
    private static final int PAGE_LIMIT = 5;
    private ClientAppsListAction action = new ClientAppsListAction();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("authorizedUser");
        try {
            int page = Integer.parseInt(request.getParameter(PARAM_NAME_PAGE));
            List<Application> list = action.supplyAppsByUserId(user.getId(), page, PAGE_LIMIT);
            request.setAttribute("apps", list);
        } catch (NumberFormatException | SQLException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
