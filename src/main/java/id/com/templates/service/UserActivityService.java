package id.com.templates.service;

import java.util.Date;
import java.util.List;

import id.com.templates.enumz.Activity;
import id.com.templates.model.main.UserActivity;

/**
 * Created by edsarp on 2/19/17.
 */
public interface UserActivityService {

    void addActivity(String userId, Activity activity,String ipAdress,String description);

    UserActivity getLastActivity(String userId, Activity activity);
    UserActivity getLastLoginActivity(String userId, Activity activity);
    
    List<UserActivity> findAllActivityBetweenLogin(String userId, Date from, Date to);
}
