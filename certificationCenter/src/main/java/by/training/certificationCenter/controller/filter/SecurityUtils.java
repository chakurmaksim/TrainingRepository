package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.bean.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtils {
    /**
     * Method allows you to determine if the page is intended for all users.
     *
     * @param request current HttpServletRequest object
     * @return false if the page is intended for all users
     */
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);
        Set<Role> roles = SecurityConfig.getAllAppRoles();
        for (Role role : roles) {
            List<String> urlPatterns = SecurityConfig.
                    getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method allows to determine if the user has permission to view a specific
     * page and and perform certain actions on his behalf to receive or modify
     * data.
     *
     * @param request current HttpServletRequest object
     * @return true if the user has permission
     */
    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);
        Set<Role> allRoles = SecurityConfig.getAllAppRoles();
        for (Role role : allRoles) {
            if (!request.isUserInRole(role.getRoleName())) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.
                    getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
