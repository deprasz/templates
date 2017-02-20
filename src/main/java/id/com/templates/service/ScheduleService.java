package id.com.templates.service;

import id.com.templates.enumz.Activity;
import id.com.templates.model.auth.User;
import id.com.templates.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by edsarp on 2/19/17.
 */
@Service
public class ScheduleService {
    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private static final DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ssss");

    @Autowired
    UserRepo userRepo;

    @Value("${duration.reset.loginfail}")
    private Integer resetLoginFail;

    @Scheduled(cron = "${cron.checking.loginfail}")
    public void ResetLoginFail()
    {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,-resetLoginFail);
        List<User> users=userRepo.findAllByLastLoginTimestameLessThan(cal.getTime(), Activity.LOGIN.getValue());
        for (User user:users){
            log.debug("Reset login file user");
            userRepo.updateFailedLoginByUserId(0,user.getUserId());
        }

    }
}
