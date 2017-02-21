package id.com.templates.service.impl;

import id.com.templates.enumz.Activity;
import id.com.templates.model.main.UserActivity;
import id.com.templates.repository.UserActitvityRepo;
import id.com.templates.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by edsarp on 2/19/17.
 */
@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActitvityRepo userActitvityRepo;

    @Override
    public void addActivity(String userId, Activity activity, String ipAdress, String description) {
        UserActivity userActivity=new UserActivity();
        userActivity.setUserId(userId);
        userActivity.setTimestamp(new Date());
        userActivity.setActivity(activity.getValue());
        userActivity.setIpAdress(ipAdress);
        userActivity.setDescription(description);
        userActitvityRepo.save(userActivity);
    }

    @Override
    public UserActivity getLastActivity(String userId, Activity activity) {
        return userActitvityRepo.findOneLastActivityByUserIdAndActiviyKey(userId,activity.getValue());
    }

	@Override
	public UserActivity getLastLoginActivity(String userId, Activity activity) {
		return userActitvityRepo.findOneSecondRowActivityByUserIdAndActiviyKey(userId, activity.getValue());
	}

	@Override
	public List<UserActivity> findAllActivityBetweenLogin(String userId, Date from, Date to) {
		return userActitvityRepo.findAllByUserIdAndTimestampBetween(userId, from, to);
	}
}
