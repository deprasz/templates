package id.com.templates.controller.inquiry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import id.com.templates.common.ComponentUtil;
import id.com.templates.model.account.AccountUser;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class InquiryBalance extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Listbox list;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		doReset();
	}

	private void doReset(){
		ComponentUtil.clear(wnd);
		doRefreshTable(mainService.findAllAccountUserById(authService.userDetails().getUserId()));
	}
	
	private void doRefreshTable(List<AccountUser> listAccount){
		list.getItems().clear();
		if (listAccount != null && listAccount.size() > 0){
			for(final AccountUser account : listAccount){
				Listitem item = new Listitem();
				item.setAttribute("DATA", account);
				item.appendChild(new Listcell(account.getAccountNumber()));
				item.appendChild(new Listcell(account.getAccountName()));
				
				Button button = new Button();
				button.setLabel("Inquiry Balance");
				button.setClass("btn btn-primary btn-rounded");
				button.setIconSclass("fa fa-search");
				button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		            public void onEvent(Event event) throws Exception {
		            	doChangeMenu(account);
		            }
		        });
				Listcell listcell = new Listcell();
				listcell.appendChild(button);
				item.appendChild(listcell);
				
				list.appendChild(item);
			}
		}
	}
	
	private void doChangeMenu(AccountUser account){
		Include inc = (Include) this.getSelf().getParent();
		inc.setSrc("/page/inquiry/InquiryBalancePanel.zul?accnbr=" + account.getAccountNumber());
		inc.invalidate();
	}
}
