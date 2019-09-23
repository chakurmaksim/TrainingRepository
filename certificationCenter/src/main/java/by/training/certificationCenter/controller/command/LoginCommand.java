package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.action.UserFindAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class LoginCommand extends Command {
    private static final String PARAM__LOGIN = "login";
    private static final String PARAM__PASS = "password";
    private UserFindAction action = new UserFindAction();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        String login = request.getParameter(PARAM__LOGIN);
        String password = request.getParameter(PARAM__PASS);
        if (login != null && password != null) {
            try {
                User user = action.findByLoginAndPassword(login, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("authorizedUser", user);
                    //session.setAttribute("menu", menu.get(user.getRole()));
                    getLogger().info(String.format("user \"%s\" is logged in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                } else {
                    request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                    getLogger().info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                }
            } catch (SQLException e) {
                request.setAttribute("message", "Произошла ошибка при получении информации из базы данных");
                getLogger().info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                e.printStackTrace();
            }
        }
    }
}
