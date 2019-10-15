package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.UserService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.OrganisationFactory;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.factory.UserFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class RegistrationCommand extends Command {
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    private static final int ROLE_IND = 3;

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        Enumeration<String> parameterNames = request.getParameterNames();
        int countParams = 0;
        if (parameterNames.hasMoreElements()) {
            countParams++;
        }
        if (countParams == 0) {
            return;
        }
        try {
            User user = createUser(request);
            user.setOrganisation(createOrganisation(request));
            UserService service = ServiceFactory.getInstance().getUserService();
            if (service.registration(user)) {
                setRedirect(true);
                setPathName(PathConfiguration.getProperty("path.page.success"));
                HttpSession session = request.getSession();
                session.setAttribute(ATTRIBUTE_NAME_USER, user);
                session.setAttribute(ATTRIBUTE_MESSAGE,
                        "Вы успешно зарегистрированы!");
                getLogger().info(String.format(
                        "user \"%s\" is logged up from %s (%s:%s)",
                        user.getLogin(), request.getRemoteAddr(),
                        request.getRemoteHost(), request.getRemotePort()));
            }
        } catch (CommandException | ServiceException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
            getLogger().error(String.format("user unsuccessfully "
                            + "tried to sign up from %s (%s:%s)",
                    request.getRemoteAddr(), request.getRemoteHost(),
                    request.getRemotePort()));
        }
    }

    private Organisation createOrganisation(final HttpServletRequest request)
            throws CommandException {
        String unpParam = request.getParameter("unp");
        int unp = 0;
        if (unpParam != null) {
            try {
                unp = Integer.parseInt(unpParam);
            } catch (NumberFormatException e) {
                throw new CommandException("UNP can not be parsed into the "
                        + "integer number");
            }
        }
        String name = request.getParameter("organisationName");
        if (name == null || name.trim().equals("")) {
            throw new CommandException("Organisation name is empty");
        }
        String address = request.getParameter("organisationAddress");
        if (name == null || address.trim().equals("")) {
            throw new CommandException("Organisation address is empty");
        }
        String phoneParam = request.getParameter("organisationPhone");
        if (phoneParam == null || phoneParam.trim().equals("")
                || !phoneParam.startsWith("+")) {
            throw new CommandException("Organisation phone is empty");
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneParam.substring(1));
        } catch (NumberFormatException e) {
            throw new CommandException("Field can not be parsed into the "
                    + "organisation phone");
        }
        String email = request.getParameter("organisationEmail");
        if (email == null || email.trim().equals("")) {
            throw new CommandException("Organisation email is empty");
        }
        return OrganisationFactory.getSingleInstance().createOrganisation(
                0, unp, name, address, phoneNumber, email, true);
    }

    private User createUser(final HttpServletRequest request)
            throws CommandException {
        String login = request.getParameter("login");
        if (login == null || login.trim().equals("")) {
            throw new CommandException("Executor login is empty");
        }
        String name = request.getParameter("name");
        if (name == null || name.trim().equals("")) {
            throw new CommandException("Executor name is empty");
        }
        String surname = request.getParameter("surname");
        if (surname == null || surname.trim().equals("")) {
            throw new CommandException("Executor surname is empty");
        }
        String patronymic = request.getParameter("patronymic");
        String mail = request.getParameter("executorEmail");
        if (mail == null || mail.trim().equals("")) {
            throw new CommandException("Executor email is empty");
        }
        String phoneParam = request.getParameter("executorPhone");
        if (phoneParam == null || phoneParam.trim().equals("")
                || !phoneParam.startsWith("+")) {
            throw new CommandException("Executor phone is empty");
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneParam.substring(1));
        } catch (NumberFormatException e) {
            throw new CommandException("Field can not be parsed into the "
                    + "executor phone");
        }
        String password = request.getParameter("password");
        if (password == null || password.trim().equals("")) {
            throw new CommandException("Password is empty");
        }
        String passwordAgain = request.getParameter("passwordAgain");
        if (!password.equals(passwordAgain)) {
            throw new CommandException("Verification password does not match "
                    + "the original");
        }
        return UserFactory.getSingleInstance().createNewUser(login, name,
                surname, patronymic, phoneNumber, mail, ROLE_IND, password);
    }
}
