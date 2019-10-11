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

    @Override
    public boolean isUserInRole(String roleName) {
        if (userRole == null) {
            return this.realRequest.isUserInRole(roleName);
        }
        return userRole.getRoleName().equals(roleName);
    }

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
