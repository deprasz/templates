package id.com.templates.controller.inquiry;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import id.com.templates.common.ComponentUtil;
import id.com.templates.common.JRreportWindow;
import id.com.templates.enumz.Activity;
import id.com.templates.model.account.Transaction;
import id.com.templates.security.AuthService;
import id.com.templates.service.MainService;
import id.com.templates.service.UserActivityService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@org.springframework.stereotype.Component
@Scope("desktop")
public class MutationAccountPanel extends SelectorComposer<Component>{
	@Wire Window wnd;
	@Wire Listbox list;
	@Wire Label lblFirst;
	@Wire Label lblDebet;
	@Wire Label lblCredit;
	@Wire Label lblLast;
	@Wire Button btnPrint;
	@Wire Button btnDownload;
	
	@Autowired MainService mainService;
	@Autowired AuthService authService;
	@Autowired UserActivityService userActivityService;
	@Autowired HttpServletRequest request;
	
	String account;
	String type;
	String limit;
	String from;
	String end;
	List<Transaction> listTransaction;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		account = Executions.getCurrent().getParameter("accnbr");
		type = Executions.getCurrent().getParameter("type");
		if (type.equals("0")) {
			limit = Executions.getCurrent().getParameter("limits");
		}else{
			from = Executions.getCurrent().getParameter("from");
			end = Executions.getCurrent().getParameter("end");
		}
		
		userActivityService.addActivity(authService.userDetails().getUserId(), 
				Activity.TRANSACTION, request.getRemoteAddr(), "Mutation " + account);
		
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
		doReset();
	}

	private void doReset(){
		ComponentUtil.clear(wnd);
		doRefreshTable();
	}
	
	private void doRefreshTable(){
		list.getItems().clear();
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal debet = BigDecimal.ZERO;
		BigDecimal credit = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.ZERO;
		
		listTransaction = new ArrayList<>();
		if (type.equals("0")) {
			listTransaction = mainService.findAllTransactionByLimit(account,limit);
		}else{
			listTransaction = mainService.findAllTransactionByPeriode(account,from, end);
		}
		if (listTransaction != null && listTransaction.size() > 0){
			for(Transaction transaction : listTransaction){
				Listitem item = new Listitem();
				item.setAttribute("DATA", transaction);
				item.appendChild(new Listcell(transaction.getAccountNumber()));
				item.appendChild(new Listcell(transaction.getTransactionMessage()));
				if (transaction.getDebetCredit() == 0) {
					debet = debet.add(transaction.getTransactionAmount().negate());
					last = last.subtract(transaction.getTransactionAmount().negate());
					item.appendChild(new Listcell(transaction.getTransactionAmount().negate().toString()));
					item.appendChild(new Listcell(BigDecimal.ZERO.toString()));
				}else{
					credit = credit.add(transaction.getTransactionAmount());
					last = last.add(transaction.getTransactionAmount());
					item.appendChild(new Listcell(BigDecimal.ZERO.toString()));
					item.appendChild(new Listcell(transaction.getTransactionAmount().toString()));
				}
				list.appendChild(item);
			}
		}else{
			Transaction transaction = new Transaction();
			transaction.setAccountNumber(account);
			transaction.setTransactionMessage("-");
			transaction.setDebetCredit(0);
			transaction.setTransactionAmount(BigDecimal.ZERO);
			listTransaction.add(transaction);
			
			Listitem item = new Listitem();
			item.appendChild(new Listcell(account));
			item.appendChild(new Listcell("-"));
			item.appendChild(new Listcell("0.00"));
			item.appendChild(new Listcell("0.00"));
			list.appendChild(item);
		}
		lblCredit.setValue(credit.toString());
		lblDebet.setValue(debet.toString());
		lblFirst.setValue(first.toString());
		lblLast.setValue(last.toString());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doPrint(){
		try {
			HashMap map = new HashMap();
			map.put("product", "Mutasi Rekening " + account);
			map.put("first", lblFirst.getValue());
			map.put("debet", lblDebet.getValue());
			map.put("credit", lblCredit.getValue());
			map.put("last", lblLast.getValue());
			String fileName = "/report/MutationAccount.jasper";
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listTransaction);
            new JRreportWindow(getSelf(), false, map, fileName, ds, "pdf", null, (String) map.get("product"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doDownload(){
		try {
			HashMap map = new HashMap();
			map.put("product", "Mutasi Rekening " + account);
			map.put("first", lblFirst.getValue());
			map.put("debet", lblDebet.getValue());
			map.put("credit", lblCredit.getValue());
			map.put("last", lblLast.getValue());
			String fileName = "/report/MutationAccount.jasper";
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listTransaction);
            AMedia media = new JRreportWindow(getSelf(), map, fileName, ds, "pdf", (String) map.get("product")).createReportDownload();
			if (media != null) {
				Filedownload.save(media);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
