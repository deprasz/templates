package id.com.templates.controller.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import id.com.templates.enumz.Activity;
import id.com.templates.model.main.UserActivity;
import id.com.templates.service.UserActivityService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class Logout extends SelectorComposer<Component>{
	private static final Logger log = LoggerFactory.getLogger(Logout.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

	@Wire Label lblLastLogin;
	@Wire Label lblLastLogout;
	@Wire Listbox list;
	@Autowired private UserActivityService userActivityService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		String userId = Executions.getCurrent().getParameter("userId");
		try {
			UserActivity userActivityLogin= userActivityService.getLastActivity(userId, Activity.LOGIN);
			UserActivity userActivityLogout= userActivityService.getLastActivity(userId, Activity.LOGOUT);
			lblLastLogin.setValue(userActivityLogin.getTimestamp() == null ? "none" : sdf.format(userActivityLogin.getTimestamp()));
			lblLastLogout.setValue(userActivityLogout.getTimestamp() == null ? "none" : sdf.format(userActivityLogout.getTimestamp()));
			doRefreshTable(userId, userActivityLogin.getTimestamp(), userActivityLogout.getTimestamp() == null ? new Date() : userActivityLogout.getTimestamp());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}


	public void doRefreshTable(String userId, Date from, Date to){
		list.getItems().clear();
		List<UserActivity> listActivity = userActivityService.findAllActivityBetweenLogin(userId, from, to);
		if (listActivity != null && listActivity.size() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			for(UserActivity activity : listActivity){
				Listitem item = new Listitem();
				item.appendChild(new Listcell(activity.getDescription()));
				item.appendChild(new Listcell(sdf.format(activity.getTimestamp())));
				list.appendChild(item);
			}
		}
	}
}
