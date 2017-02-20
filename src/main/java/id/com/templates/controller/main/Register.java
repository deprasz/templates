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
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class Register extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Textbox txtUserId;
	@Wire Textbox txtName;
	@Wire Textbox txtEmail;
	@Wire Textbox txtActKey;
	@Wire Textbox txtSecret;
	@Wire Button btnSave;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	
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
			doInsert();
		}
	}
	
	private void doInsert(){
		try {
			User user = mainService.findUserById((String) ComponentUtil.getValue(txtUserId));
			if (user == null) {
				user = new User();
				user.setUserId((String) ComponentUtil.getValue(txtUserId));
				user.setUserName((String) ComponentUtil.getValue(txtName));
				user.setPassword(Cryptograph.bEncrypt(mainService.createPassword()));
				user.setActivated(false);
				user.setEmail((String) ComponentUtil.getValue(txtEmail));
				user.setActivationKey((String) ComponentUtil.getValue(txtActKey));
				user.setResetKey((String) ComponentUtil.getValue(txtSecret));
				user.setFailedLogin(0);
				user.setLimitFailed(6);
				user.setLanguage("0");
				user.setRoleId("00");
				user.setCreateBy((String) ComponentUtil.getValue(txtUserId));
				user.setCreateDate(new Date());
				mainService.saveUser(user);
				ComponentUtil.success(panel, "Create Account Success, Please Check Your Email.");
			}else{
				ComponentUtil.failed(panel, "Already Account");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Create Account");
		}
		doReset();
	}

	private void doReset(){
		ComponentUtil.clear(wnd);
		txtUserId.setDisabled(false);
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(txtUserId) == null || ComponentUtil.getValue(txtUserId).equals("")) {
			throw new WrongValueException(txtUserId, "Please insert user id");
		}else if (ComponentUtil.getValue(txtName) == null || ComponentUtil.getValue(txtName).equals("")) {
			throw new WrongValueException(txtName, "Please insert name");
		}else if (ComponentUtil.getValue(txtEmail) == null || ComponentUtil.getValue(txtEmail).equals("")) {
			throw new WrongValueException(txtEmail, "Please insert email");
		}else if (ComponentUtil.getValue(txtActKey) == null || ComponentUtil.getValue(txtActKey).equals("")) {
			throw new WrongValueException(txtActKey, "Please insert activation key");
		}else if (ComponentUtil.getValue(txtSecret) == null || ComponentUtil.getValue(txtSecret).equals("")) {
			throw new WrongValueException(txtSecret, "Please insert secret word");
		}else if (!Regex.regexUsername((String) ComponentUtil.getValue(txtUserId))) {
			throw new WrongValueException(txtUserId, "Should be a combination of numbers and letters at least 5");
		}else if (!Regex.regexEmail((String) ComponentUtil.getValue(txtEmail))) {
			throw new WrongValueException(txtEmail, "Please insert email correctly");
		}else if (!Regex.regexPassword((String) ComponentUtil.getValue(txtActKey))) {
			throw new WrongValueException(txtActKey, "Should be a combination of numbers and letters (upper and lower) at least 8");
		}else if (!Regex.regexPassword((String) ComponentUtil.getValue(txtSecret))) {
			throw new WrongValueException(txtSecret, "Should be a combination of numbers and letters (upper and lower) at least 8");
		}
		return true;
	}
}
