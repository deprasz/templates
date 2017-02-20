package id.com.templates.controller.main;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import id.com.templates.common.ComponentUtil;
import id.com.templates.model.auth.User;
import id.com.templates.model.main.Activity;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class ActivatedAccount extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Textbox txtUserId;
	@Wire Textbox txtActKey;
	@Wire Textbox txtName;
	@Wire Button btnCheck;
	@Wire Button btnSave;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	User user;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		btnCheck.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doCheckAccount();
            }
        });
		
		btnSave.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doUpdate();
            }
        });
		
		btnReset.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doReset();
            }
        });
		
		doReset();
	}
	
	private void doCheckAccount(){
		if (doValidation()) {
			user = mainService.findUserById((String) ComponentUtil.getValue(txtUserId));
			if (user != null) {
				if (!user.getActivationKey().equals(ComponentUtil.getValue(txtActKey))) {
					ComponentUtil.failed(panel, "Failed Activation Key");
				}else if (user.isActivated()) {
					ComponentUtil.failed(panel, "Account Already Active");
				}else{
					btnSave.setDisabled(false);
					txtName.setValue(user.getUserName());
				}
			}else{
				ComponentUtil.failed(panel, "Account Not Found");
			}
		}
	}
	
	private void doUpdate(){
		try {
			if (user != null) {
				user.setActivated(true);
				user.setUpdateBy(authService.userDetails().getUserId());
				user.setUpdateDate(new Date());
				mainService.saveUser(user);
				mainService.saveActivity(createActivity());
				ComponentUtil.success(panel, "Account Active");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Activated Account");
		}
		doReset();
	}

	private Activity createActivity(){
		Activity activity = new Activity();
		activity.setLogin(authService.userDetails().getLoginTime());
		activity.setStatus(0);
		activity.setTimes(new Date());
		activity.setUserId(authService.userDetails().getUserId());
		activity.setActivity("Activated Account : " + txtUserId.getValue());
		return activity;
	}
	
	private void doReset(){
		ComponentUtil.clear(wnd);
		txtUserId.setDisabled(false);
		btnSave.setDisabled(true);
		user = null;
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(txtUserId) == null || ComponentUtil.getValue(txtUserId).equals("")) {
			throw new WrongValueException(txtUserId, "Please insert user id");
		}else if (ComponentUtil.getValue(txtActKey) == null || ComponentUtil.getValue(txtActKey).equals("")) {
			throw new WrongValueException(txtActKey, "Please insert secret word");
		}
		return true;
	}
}
