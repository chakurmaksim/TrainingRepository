package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
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
import java.util.ResourceBundle;

public class RegistrationCommand extends Command {
    /**
     * Key that is required to set the message to the request attribute.
     */
    private static final String ATTRIBUTE_MESSAGE = "message";
    /**
     * Key that is required to set the error message to the request attribute.
     */
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    /**
     * The key that is required to get the user instance from the
     * session attribute.
     */
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    /**
     * A user with only this role number can register himself and his
     * organization independently.
     */
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
        HttpSession session = request.getSession();
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        try {
            User user = createUser(request, bundle);
            user.setOrganisation(createOrganisation(request, bundle));
            UserService service = ServiceFactory.getInstance().getUserService();
            if (service.registration(user)) {
                setRedirect(true);
                setPathName(PathConfiguration.getProperty("path.page.success"));
                session.setAttribute(ATTRIBUTE_NAME_USER, user);
                session.setAttribute(ATTRIBUTE_MESSAGE, bundle.getString(
                        "message.user.registration.success"));
                getLogger().info(String.format(
                        "user \"%s\" is logged up from %s (%s:%s)",
                        user.getLogin(), request.getRemoteAddr(),
                        request.getRemoteHost(), request.getRemotePort()));
            }
        } catch (CommandException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
        } catch (ServiceException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    bundle.getString(e.getMessage()));
            getLogger().error(String.format("user unsuccessfully "
                            + "tried to sign up from %s (%s:%s)",
                    request.getRemoteAddr(), request.getRemoteHost(),
                    request.getRemotePort()));
        }
    }

    private Organisation createOrganisation(
            final HttpServletRequest request, final ResourceBundle bundle)
            throws CommandException {
        String unpParam = request.getParameter("unp");
        int unp = 0;
        if (unpParam != null) {
            try {
                unp = Integer.parseInt(unpParam);
            } catch (NumberFormatException e) {
                throw new CommandException(bundle.getString(
                        "message.organisation.unp.numberFormat"));
            }
        }
        String name = request.getParameter("organisationName");
        if (name == null || name.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.organisation.name.empty"));
        }
        String address = request.getParameter("organisationAddress");
        if (name == null || address.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.organisation.address.empty"));
        }
        String phoneParam = request.getParameter("organisationPhone");
        if (phoneParam == null || phoneParam.trim().equals("")
                || !phoneParam.startsWith("+")) {
            throw new CommandException(bundle.getString(
                    "message.organisation.phone.empty"));
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneParam.substring(1));
        } catch (NumberFormatException e) {
            throw new CommandException(bundle.getString(
                    "message.organisation.phone.numberFormat"));
        }
        String email = request.getParameter("organisationEmail");
        if (email == null || email.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.organisation.email.empty"));
        }
        return OrganisationFactory.getSingleInstance().
                createNewClientOrganisation(unp, name, address,
                        phoneNumber, email);
    }

    private User createUser(final HttpServletRequest request,
                            final ResourceBundle bundle)
            throws CommandException {
        String login = request.getParameter("login");
        if (login == null || login.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.user.login.empty"));
        }
        String name = request.getParameter("name");
        if (name == null || name.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.user.name.empty"));
        }
        String surname = request.getParameter("surname");
        if (surname == null || surname.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.user.surname.empty"));
        }
        String patronymic = request.getParameter("patronymic");
        String mail = request.getParameter("executorEmail");
        if (mail == null || mail.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.user.email.empty"));
        }
        String phoneParam = request.getParameter("executorPhone");
        if (phoneParam == null || phoneParam.trim().equals("")
                || !phoneParam.startsWith("+")) {
            throw new CommandException(bundle.getString(
                    "message.user.phone.empty"));
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneParam.substring(1));
        } catch (NumberFormatException e) {
            throw new CommandException(bundle.getString(
                    "message.user.phone.numberFormat"));
        }
        String password = request.getParameter("password");
        if (password == null || password.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.user.password.empty"));
        }
        String passwordAgain = request.getParameter("passwordAgain");
        if (!password.equals(passwordAgain)) {
            throw new CommandException(bundle.getString(
                    "message.user.password.verification"));
        }
        return UserFactory.getSingleInstance().createNewUser(login, name,
                surname, patronymic, phoneNumber, mail, ROLE_IND, password);
    }
}
