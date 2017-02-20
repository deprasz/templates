package id.com.templates.configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListenerInit implements HttpSessionListener{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.info("Session Created");
		event.getSession().setMaxInactiveInterval(1000);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		logger.info("Session Destroyed");
	}

}
