package id.com.templates.controller.main;

import java.text.SimpleDateFormat;

import id.com.templates.enumz.Activity;
import id.com.templates.model.main.UserActivity;
import id.com.templates.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import id.com.templates.model.UserDetail;
import id.com.templates.security.AuthService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class Form extends SelectorComposer<Component>{
	@Wire Label lblUsername;
	@Wire Label lblLastLogin;
	@Wire Label lblLastLogout;
	@Autowired AuthService authService;
	@Autowired
	private UserActivityService userActivityService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		UserDetail user = authService.userDetails();
		lblUsername.setValue(user.getUsername());
		UserActivity userActivityLogin = userActivityService.getLastLoginActivity(user.getUserId(), Activity.LOGIN);
		UserActivity userActivityLogout = userActivityService.getLastActivity(user.getUserId(), Activity.LOGOUT);
		if (userActivityLogin != null && userActivityLogin.getTimestamp() != null) {
			lblLastLogin.setValue(sdf.format(userActivityLogin.getTimestamp()));
		}else{
			lblLastLogin.setValue("none");
		}
		if (userActivityLogout != null && userActivityLogout.getTimestamp() != null) {
			lblLastLogout.setValue(sdf.format(userActivityLogout.getTimestamp()));
		}else{
			lblLastLogout.setValue("none");
		}
	}
}
