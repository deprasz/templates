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
import id.com.templates.model.auth.User;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class ForgotPassword extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Textbox txtUserId;
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
			doUpdate();
		}
	}
	
	private void doUpdate(){
		try {
			User user = mainService.findUserById((String) ComponentUtil.getValue(txtUserId));
			if (user != null) {
				if (user.getResetKey().equals(ComponentUtil.getValue(txtSecret))) {
					user.setPassword(Cryptograph.bEncrypt(mainService.createPassword()));
					user.setFailedLogin(0);
					user.setLimitFailed(6);
					user.setUpdateBy((String) ComponentUtil.getValue(txtUserId));
					user.setUpdateDate(new Date());
					mainService.saveUser(user);
					ComponentUtil.success(panel, "Reset Password Success, Please Check Your Email.");
				}else{
					ComponentUtil.failed(panel, "Failed Secret Word");
				}
			}else{
				ComponentUtil.failed(panel, "Account Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Reset Password");
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
		}else if (ComponentUtil.getValue(txtSecret) == null || ComponentUtil.getValue(txtSecret).equals("")) {
			throw new WrongValueException(txtSecret, "Please insert secret word");
		}
		return true;
	}
}
