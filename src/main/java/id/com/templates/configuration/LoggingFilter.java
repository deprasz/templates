package id.com.templates.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;

import id.com.templates.model.UserDetail;

public class LoggingFilter implements Filter {
    private static final String USER_NAME = "username";
    private static final String DEFAULT_USER = "system";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = (Authentication) ((HttpServletRequest) request).getUserPrincipal();
        UserDetail userDetails = null;
        if(authentication != null){
            userDetails = (UserDetail) authentication.getPrincipal();
        }
        try {
            if(userDetails==null){
                MDC.put(USER_NAME,DEFAULT_USER);
            }else{
                MDC.put(USER_NAME,userDetails.getUserId());
            }
            chain.doFilter(request,response);
        }finally {
            MDC.put(USER_NAME,DEFAULT_USER);
        }
    }

    @Override
    public void destroy() {}
}