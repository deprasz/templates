package id.com.templates.controller.inquiry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Radiogroup;

import id.com.templates.common.ComponentUtil;
import id.com.templates.model.account.AccountUser;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class MutationAccount extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Combobox cmbAccount;
	@Wire Combobox cmbLast;
	@Wire Datebox dtFrom;
	@Wire Datebox dtTo;
	@Wire Radiogroup rdFind;
	@Wire Button btnFind;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		rdFind.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doChangeRadio();
            }
        });
		
		btnFind.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doChangeMenu();
            }
        });
		
		btnReset.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doReset();
            }
        });
		doLoadAccount();
		doReset();
	}

	private void doLoadAccount(){
		cmbAccount.getItems().clear();
		List<AccountUser> listAccount = mainService.findAllAccountUserById(authService.userDetails().getUserId());
		if (listAccount != null && !listAccount.isEmpty()) {
			for (AccountUser account : listAccount) {
				Comboitem item = new Comboitem();
				item.setLabel(account.getAccountNumber() + " - " + account.getAccountName());
				item.setValue(account.getAccountNumber());
				cmbAccount.appendChild(item);
			}
		}
	}
	
	private void doReset(){
		ComponentUtil.clear(wnd);
		Date date = new Date();
		dtFrom.setValue(date);
		dtTo.setValue(date);
		ComponentUtil.setValue(rdFind, 0);
		doChangeRadio();
	}
	
	public void doChangeRadio(){
		if (ComponentUtil.getValue(rdFind).toString().equals("0")) {
			dtFrom.setDisabled(true);
			dtTo.setDisabled(true);
			cmbLast.setDisabled(false);
		}else{
			dtFrom.setDisabled(false);
			dtTo.setDisabled(false);
			cmbLast.setDisabled(true);
		}
	}

	private void doChangeMenu(){
		if (doValidation()) {
			String url = "/page/inquiry/MutationAccountPanel.zul?accnbr=";
			if (ComponentUtil.getValue(rdFind).toString().equals("1")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				url = url + ComponentUtil.getValue(cmbAccount) + "&from=" + 
					sdf.format(dtFrom.getValue()) + "&end=" + sdf.format(dtTo.getValue()) + "&type=1";
			}else{
				url = url + ComponentUtil.getValue(cmbAccount) + "&limits=" + ComponentUtil.getValue(cmbLast) + "&type=0";
			}
			Include inc = (Include) this.getSelf().getParent();
			inc.setSrc(url);
			inc.invalidate();
		}
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(cmbAccount) == null) {
			throw new WrongValueException(cmbAccount, "Pelect Select Account Number");
		}else{
			if (ComponentUtil.getValue(rdFind).toString().equals("0")) {
				if (ComponentUtil.getValue(cmbLast) == null) {
					throw new WrongValueException(cmbLast, "Please Select One");
				}
			}else{
				if (ComponentUtil.getValue(dtFrom) == null) {
					throw new WrongValueException(dtFrom, "Insert Date");
				}else if (ComponentUtil.getValue(dtTo) == null) {
					throw new WrongValueException(dtTo, "Insert Date");
				}else{
					if ((dtFrom.getValue().getTime() - dtTo.getValue().getTime()) / (24 * 3600 * 1000) > 180) {
						throw new WrongValueException(dtTo, "Date Not Valid");
					}
				}
			}
		}
		return true;
	}
}
