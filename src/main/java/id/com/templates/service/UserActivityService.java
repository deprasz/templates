package id.com.templates.service;

import id.com.templates.enumz.Activity;
import id.com.templates.model.main.UserActivity;

/**
 * Created by edsarp on 2/19/17.
 */
public interface UserActivityService {

    void addActivity(String userId, Activity activity,String ipAdress,String description);

    UserActivity getLastActivity(String userId, Activity activity);


}
