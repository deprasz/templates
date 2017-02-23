package id.com.templates.controller.inquiry;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import id.com.templates.common.JRreportWindow;
import id.com.templates.enumz.Activity;
import id.com.templates.model.account.AccountUser;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;
import id.com.templates.service.UserActivityService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@org.springframework.stereotype.Component
@Scope("desktop")
public class InquiryBalancePanel extends SelectorComposer<Component>{
	@Wire Window wnd;
	@Wire Label lblAccount;
	@Wire Label lblAccountName;
	@Wire Label lblDate;
	@Wire Label lblBalance;
	@Wire Button btnPrint;
	@Wire Button btnDownload;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	@Autowired UserActivityService userActivityService;
	@Autowired HttpServletRequest request;
	
	String account;
	AccountUser user;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		account = Executions.getCurrent().getParameter("accnbr");
		user = mainService.findAccountUserById(authService.userDetails().getUserId(), account);
		String balance = mainService.findBalanceAccount(account).toString();
		String date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(new Date());
		lblAccount.setValue(account);
		lblAccountName.setValue(user.getAccountName());
		lblDate.setValue(date);
		lblBalance.setValue(balance);
		
		userActivityService.addActivity(authService.userDetails().getUserId(), 
				Activity.TRANSACTION, request.getRemoteAddr(), "Inquiry Balance " + account);
		
		btnPrint.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doPrint();
            }
        });
		
		btnDownload.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
            	doDownload();
            }
        });
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doPrint(){
		try {
			HashMap map = new HashMap();
			map.put("product", "Inquiry Balance " + user.getAccountName());
			map.put("accnbr", user.getAccountNumber());
			map.put("accname", user.getAccountName());
			map.put("balance", lblBalance.getValue());
			map.put("date", lblDate.getValue());
			String fileName = "/report/InquiryBalance.jasper";
            List<AccountUser> listParam = new ArrayList<AccountUser>();
            listParam.add(user);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listParam);
            new JRreportWindow(getSelf(), false, map, fileName, ds, "pdf", null, (String) map.get("product"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doDownload(){
		try {
			HashMap map = new HashMap();
			map.put("product", "Inquiry Balance " + user.getAccountName());
			map.put("accnbr", user.getAccountNumber());
			map.put("accname", user.getAccountName());
			map.put("balance", lblBalance.getValue());
			map.put("date", lblDate.getValue());
			String fileName = "/report/InquiryBalance.jasper";
            List<AccountUser> listParam = new ArrayList<AccountUser>();
            listParam.add(user);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listParam);
            AMedia media = new JRreportWindow(getSelf(), map, fileName, ds, "pdf", (String) map.get("product")).createReportDownload();
			if (media != null) {
				Filedownload.save(media);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
