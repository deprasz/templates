package id.com.templates.controller.account;

import javax.servlet.http.HttpServletRequest;

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
import id.com.templates.enumz.Activity;
import id.com.templates.model.account.Account;
import id.com.templates.model.account.AccountUser;
import id.com.templates.model.auth.User;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;
import id.com.templates.service.UserActivityService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class RegisterAccountNumber extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Textbox txtUserId;
	@Wire Textbox txtUsername;
	@Wire Textbox txtAccnbr;
	@Wire Textbox txtAccname;
	@Wire Button btnCheckUser;
	@Wire Button btnCheckAcc;
	@Wire Button btnSave;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	@Autowired UserActivityService userActivityService;
	@Autowired HttpServletRequest request;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		btnCheckUser.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doCheckUser();
            }
        });
		
		btnCheckAcc.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
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
	
	private void doCheckUser(){
		if (doValidationUser()) {
			User user = mainService.findUserById((String) ComponentUtil.getValue(txtUserId));
			if (user != null) {
				txtUsername.setValue(user.getUserName());
				txtUserId.setDisabled(true);
				txtAccnbr.setDisabled(false);
				btnCheckAcc.setVisible(true);
				btnCheckUser.setVisible(false);
			}else{
				ComponentUtil.failed(panel, "Account Not Found");
			}
		}
	}
	
	private void doCheckAccount(){
		if (doValidationAccount()) {
			Account account = mainService.findAccountById((String) ComponentUtil.getValue(txtAccnbr));
			if (account != null) {
				txtAccname.setValue(account.getAccountName());
				txtAccnbr.setDisabled(true);
				btnCheckAcc.setVisible(false);
				btnSave.setDisabled(false);
			}else{
				ComponentUtil.failed(panel, "Account Number Not Found");
			}
		}
	}
	
	private void doUpdate(){
		try {
			if (doValidation()) {
				AccountUser accountUser = mainService.findAccountUserById
						((String)ComponentUtil.getValue(txtUserId), (String) ComponentUtil.getValue(txtAccnbr));
				if (accountUser == null) {
					accountUser = new AccountUser();
					accountUser.setAccountName((String) ComponentUtil.getValue(txtAccname));
					accountUser.setAccountNumber((String) ComponentUtil.getValue(txtAccnbr));
					accountUser.setUserId((String) ComponentUtil.getValue(txtUserId));
					mainService.saveAccountUser(accountUser);
					userActivityService.addActivity(authService.userDetails().getUserId(), 
							Activity.NON_TRANSACTION, request.getRemoteAddr(), "Register Account Number " + ComponentUtil.getValue(txtAccnbr));
					ComponentUtil.success(panel, "Register Account Number Success");
				}else{
					ComponentUtil.failed(panel, "Account Number Has Registered");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Register Account Number");
		}
		doReset();
	}
	
	private void doReset(){
		ComponentUtil.clear(wnd);
		txtUserId.setDisabled(false);
		txtAccnbr.setDisabled(true);
		btnCheckAcc.setVisible(false);
		btnCheckUser.setVisible(true);
		btnSave.setDisabled(true);
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(txtUsername) == null || ComponentUtil.getValue(txtUsername).equals("")) {
			throw new WrongValueException(txtUsername, "Please inquiry user id");
		}else if (ComponentUtil.getValue(txtAccname) == null || ComponentUtil.getValue(txtAccname).equals("")) {
			throw new WrongValueException(txtAccname, "Please inquiry account name");
		}
		return true;
	}
	
	private boolean doValidationAccount(){
		if (ComponentUtil.getValue(txtAccnbr) == null || ComponentUtil.getValue(txtAccnbr).equals("")) {
			throw new WrongValueException(txtAccnbr, "Please insert account number");
		}
		return true;
	}
	
	private boolean doValidationUser(){
		if (ComponentUtil.getValue(txtUserId) == null || ComponentUtil.getValue(txtUserId).equals("")) {
			throw new WrongValueException(txtUserId, "Please insert user id");
		}
		return true;
	}
}
