package id.com.templates.security;

import id.com.templates.service.MainService;
import id.com.templates.service.UserActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class AplicationEventListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger log = LoggerFactory.getLogger(AplicationEventListener.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MainService mainService;

    @Autowired
    private UserActivityService userActivityService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (applicationEvent instanceof HttpSessionDestroyedEvent) {
            log.debug("Session is DestroyedEvent:");
        }
    }

}
