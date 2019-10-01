package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.bean.Role;
import by.training.certificationCenter.bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String urlPattern = (String) httpRequest.getAttribute("urlPattern");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (urlPattern != null && urlPattern.equals("/login")) {
                chain.doFilter(request, response);
                return;
            }
            HttpSession session = httpRequest.getSession(false);
            User authorizedUser = null;
            HttpServletRequest wrapRequest = httpRequest;
            if (session != null) {
                authorizedUser = (User) session.getAttribute("authorizedUser");
                if (authorizedUser != null) {
                    String userLogin = authorizedUser.getLogin();
                    Role userRole = authorizedUser.getRole();
                    wrapRequest = new UserRoleRequestWrapper(httpRequest, userLogin, userRole);
                }
            }
            if (SecurityUtils.isSecurityPage(httpRequest)) {
                if (authorizedUser == null) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
                    return;
                }
                boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
                if (!hasPermission) {
                    RequestDispatcher dispatcher = request.getServletContext().
                            getRequestDispatcher("/jsp/accessDeniedView.jsp");
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
