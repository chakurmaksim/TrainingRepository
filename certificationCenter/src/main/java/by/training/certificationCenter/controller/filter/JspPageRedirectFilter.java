package by.training.certificationCenter.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JspPageRedirectFilter implements Filter {
    /**
     * Path to home page.
     */
    private static String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("index_path");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String uriPattern = UrlPatternUtils.getUrlPattern(httpRequest);
            if (!uriPattern.equals("/jsp/error")) {
                httpResponse.sendRedirect(
                        httpRequest.getContextPath() + indexPath);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
