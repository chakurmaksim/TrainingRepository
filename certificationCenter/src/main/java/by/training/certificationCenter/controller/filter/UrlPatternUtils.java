package by.training.certificationCenter.controller.filter;

import javax.servlet.http.HttpServletRequest;

public class UrlPatternUtils {
    public static String getUrlPattern(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        int beginAction = contextPath.length();
        int endAction = uri.lastIndexOf('.');
        String urlPattern;
        if (endAction >= 0) {
            urlPattern = uri.substring(beginAction, endAction);
        } else {
            urlPattern = uri.substring(beginAction) + "index";
        }
        return urlPattern;
    }
}
