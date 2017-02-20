package id.com.templates.security;

import id.com.templates.enumz.Activity;
import id.com.templates.model.UserDetail;
import id.com.templates.model.auth.User;
import id.com.templates.model.main.UserActivity;
import id.com.templates.service.MainService;
import id.com.templates.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by edsarp on 2/18/17.
 */
@Component
public class AuthenticationEventListener implements AuthenticationEventPublisher,LogoutSuccessHandler {

    private static final DateFormat df=new SimpleDateFormat("yyyy-MMMM-dd HH:mm:ssss");

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MainService mainService;

    @Autowired
    private UserActivityService userActivityService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetail userDetail= (UserDetail) authentication.getPrincipal();
        userActivityService.addActivity(userDetail.getUserId(), Activity.LOGIN,request.getRemoteAddr(),"Login successfully");
        mainService.updateFailAttempts(0,authentication.getName());

    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {

        User user = mainService.findUserById(authentication.getName());
        if(user!=null){
            UserActivity userActivity=userActivityService.getLastActivity(authentication.getName(),Activity.LOGIN);
            if(!user.isActivated()){
             throw  new BadCredentialsException("Your user account was not activated");
            }
            if(user.isLocked()){
                throw new BadCredentialsException("Your user account is locked!");
            }
            userActivityService.addActivity(authentication.getName(), Activity.LOGIN,request.getRemoteAddr(),e.getMessage());
            int failedLogin=user.getFailedLogin()+1;
            mainService.updateFailAttempts(failedLogin,authentication.getName());
            if(failedLogin >=user.getLimitFailed()){
                mainService.lockedUserLogin(authentication.getName());
                throw new LockedException("Your user account is locked! <br>Last Attempted on : "+df.format(userActivity.getTimestamp()));

            }
        }
        throw new BadCredentialsException("Invalid User Name and Password");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        UserDetail userDetail= (UserDetail) authentication.getPrincipal();
        userActivityService.addActivity(userDetail.getUserId(), Activity.LOGOUT,request.getRemoteAddr(),"Logout successfully");
        mainService.updateFailAttempts(0,authentication.getName());
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/logout/successfully?userId="+userDetail.getUserId());
    }
}
