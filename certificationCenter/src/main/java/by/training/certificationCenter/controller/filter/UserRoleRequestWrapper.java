package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.bean.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    private String userLogin;
    private Role userRole;
    private HttpServletRequest realRequest;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public UserRoleRequestWrapper(final HttpServletRequest request,
                                  final String newUserLogin, final Role role) {
        super(request);
        this.realRequest = request;
        this.userLogin = newUserLogin;
        this.userRole = role;
    }

    /**
     * Method compares the current user role with the role which is required
     * to get the access to the certain page.
     *
     * @param roleName role name
     * @return true if both of the names are equals.
     */
    @Override
    public boolean isUserInRole(String roleName) {
        if (userRole != null) {
            return userRole.getRoleName().equals(roleName);
        }
        return false;
    }

    /**
     * Method is to get user login.
     *
     * @return login
     */
    @Override
    public Principal getUserPrincipal() {
        if (this.userLogin == null) {
            return realRequest.getUserPrincipal();
        }
        return new Principal() {
            @Override
            public String getName() {
                return userLogin;
            }
        };
    }
}
