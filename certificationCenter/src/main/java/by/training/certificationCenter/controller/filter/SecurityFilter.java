package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.configuration.PathConfiguration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter implements Filter {
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    private static final String ATTRIBUTE_NAME_URL_PATTERN = "urlPattern";
    /**
     * The Set contains URL-patterns for the execution of commands which
     * do not require user authorization.
     */
    private static Set<String> freeUrlPatterns = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        freeUrlPatterns.add("/login");
        freeUrlPatterns.add("/registration");
    }

    /**
     * The filter determines whether user authorization is required to access
     * a specific resource. If authorization is not required, passes the
     * execution to the next filter, otherwise it determines whether this
     * user has access right.
     *
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest &&
                response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String urlPattern = (String) httpRequest.getAttribute(
                    ATTRIBUTE_NAME_URL_PATTERN);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (urlPattern != null && freeUrlPatterns.contains(urlPattern)) {
                chain.doFilter(request, response);
                return;
            }
            HttpSession session = httpRequest.getSession(false);
            User authorizedUser = null;
            HttpServletRequest wrapRequest = httpRequest;
            if (session != null) {
                authorizedUser = (User) session.getAttribute(
                        ATTRIBUTE_NAME_USER);
                if (authorizedUser != null) {
                    String userLogin = authorizedUser.getLogin();
                    Role userRole = authorizedUser.getRole();
                    wrapRequest = new UserRoleRequestWrapper(httpRequest,
                            userLogin, userRole);
                }
            }
            if (SecurityUtils.isSecurityPage(httpRequest)) {
                if (authorizedUser == null) {
                    String loginPage = PathConfiguration.
                            getProperty("path.page.login");
                    httpResponse.sendRedirect(
                            httpRequest.getContextPath() + loginPage);
                    return;
                }
                boolean hasPermission = SecurityUtils.
                        hasPermission(wrapRequest);
                if (!hasPermission) {
                    RequestDispatcher dispatcher = request.getServletContext().
                            getRequestDispatcher(PathConfiguration.
                                    getProperty("path.page.denied"));
                    dispatcher.forward(request, response);
                    return;
                }
            }
            chain.doFilter(wrapRequest, response);
        }
    }

    @Override
    public void destroy() {

    }
}
