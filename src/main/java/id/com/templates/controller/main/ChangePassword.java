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
import id.com.templates.common.Cryptograph;
import id.com.templates.common.Regex;
import id.com.templates.model.auth.User;
import id.com.templates.model.main.Activity;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class ChangePassword extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Textbox txtOldPassword;
	@Wire Textbox txtNewPassword;
	@Wire Textbox txtConfirmation;
	@Wire Button btnSave;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		btnSave.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doSave();
            }
        });
		
		btnReset.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doReset();
            }
        });
		
		doReset();
	}
	
	private void doSave(){
		if (doValidation()) {
			doUpdate();
		}
	}
	
	private void doUpdate(){
		try {
			User user = mainService.findUserById(authService.userDetails().getUserId());
			if (user != null) {
				if (Cryptograph.bEncryptMatch(txtOldPassword.getValue(), user.getPassword())) {
					user.setPassword(Cryptograph.bEncrypt(txtNewPassword.getValue()));
					user.setUpdateBy(authService.userDetails().getUserId());
					user.setUpdateDate(new Date());
					mainService.saveUser(user);
					mainService.saveActivity(createActivity());
					ComponentUtil.success(panel, "Change password success");
				}else{
					ComponentUtil.failed(panel, "Old password failed");
				}
			}else{
				ComponentUtil.failed(panel, "Account Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Change Password");
		}
		doReset();
	}

	private Activity createActivity(){
		Activity activity = new Activity();
		activity.setLogin(authService.userDetails().getLoginTime());
		activity.setStatus(0);
		activity.setTimes(new Date());
		activity.setUserId(authService.userDetails().getUserId());
		activity.setActivity("Change Password");
		return activity;
	}
	
	private void doReset(){
		ComponentUtil.clear(wnd);
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(txtOldPassword) == null || ComponentUtil.getValue(txtOldPassword).equals("")) {
			throw new WrongValueException(txtOldPassword, "Please insert old password");
		}else if (ComponentUtil.getValue(txtNewPassword) == null || ComponentUtil.getValue(txtNewPassword).equals("")) {
			throw new WrongValueException(txtNewPassword, "Please insert new password");
		}else if (ComponentUtil.getValue(txtConfirmation) == null || ComponentUtil.getValue(txtConfirmation).equals("")) {
			throw new WrongValueException(txtConfirmation, "Please insert confirmation new password");
		}else if (!Regex.regexPassword((String) ComponentUtil.getValue(txtNewPassword))) {
			throw new WrongValueException(txtNewPassword, "Should be a combination of numbers and letters (upper and lower) at least 8");
		}else if (!ComponentUtil.getValue(txtConfirmation).equals(ComponentUtil.getValue(txtNewPassword))) {
			throw new WrongValueException(txtConfirmation, "Not valid confirmation new password");
		}
		return true;
	}
}
