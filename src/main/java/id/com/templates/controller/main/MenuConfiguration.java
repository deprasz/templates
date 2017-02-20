package id.com.templates.controller.main;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import id.com.templates.common.ComponentUtil;
import id.com.templates.model.menu.Menu;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class MenuConfiguration extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Textbox txtMenuId;
	@Wire Textbox txtMenuName;
	@Wire Textbox txtMenuParent;
	@Wire Textbox txtUrl;
	@Wire Listbox list;
	@Wire Button btnSave;
	@Wire Button btnDelete;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	
	Boolean onLoad = false;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		txtMenuId.addEventListener(Events.ON_OK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doSearch();
            }
        });
		
		txtMenuParent.addEventListener(Events.ON_OK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		isParent();
            }
        });
		
		list.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doEdit();
            }
        });
		
		btnSave.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doSave();
            }
        });
		
		btnDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doDelete();
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
			if(onLoad){
				doUpdate();
			}else{
				doInsert();
			}
		}
	}
	
	private void doEdit(){
		Listitem item = list.getSelectedItem();
		if (item != null){
			Menu menu = (Menu)item.getAttribute("DATA");
			ComponentUtil.setValue(txtMenuId, menu.getMenuId());
			ComponentUtil.setValue(txtMenuName, menu.getMenuName());
			ComponentUtil.setValue(txtMenuParent, menu.getMenuParent());
			ComponentUtil.setValue(txtUrl, menu.getUrl());
			
			onLoad = true;
			txtMenuId.setDisabled(true);
			btnDelete.setDisabled(false);
		}
	}
	
	private void doSearch(){
		doRefreshTable(mainService.findAllMenuById((String) ComponentUtil.getValue(txtMenuId)));
	}
	
	private void doInsert(){
		try {
			Menu menu = mainService.findMenuById((String) ComponentUtil.getValue(txtMenuId));
			if (menu == null) {
				menu = new Menu();
				menu.setMenuId((String) ComponentUtil.getValue(txtMenuId));
				menu.setMenuName((String) ComponentUtil.getValue(txtMenuName));
				menu.setMenuParent((String) ComponentUtil.getValue(txtMenuParent));
				menu.setUrl((String) ComponentUtil.getValue(txtUrl));
				mainService.saveMenu(menu);
				ComponentUtil.success(panel, "Save Success");
			}else{
				ComponentUtil.failed(panel, "Already Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Save");
		}
		doReset();
	}
	
	private void doUpdate(){
		try {
			Listitem item = list.getSelectedItem();
			Menu menu = (Menu)item.getAttribute("DATA");
			menu.setMenuName((String) ComponentUtil.getValue(txtMenuName));
			menu.setMenuParent((String) ComponentUtil.getValue(txtMenuParent));
			menu.setUrl((String) ComponentUtil.getValue(txtUrl));
			mainService.saveMenu(menu);
			ComponentUtil.success(panel, "Update Success");
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Update");
		}
		doReset();
	}
	
	private void doDelete(){
		try {
			Listitem item = list.getSelectedItem();
			Menu menu = (Menu)item.getAttribute("DATA");
			mainService.deleteMenu(menu);
			ComponentUtil.success(panel, "Delete Success");
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Delete");
		}
		doReset();
	}
	
	private void doReset(){
		ComponentUtil.clear(wnd);
		doRefreshTable(mainService.findAllMenu());
		onLoad = false;
		txtMenuId.setDisabled(false);
		btnDelete.setDisabled(true);
		txtMenuId.setFocus(true);
	}
	
	private void doRefreshTable(List<Menu> listMenu){
		list.getItems().clear();
		if (listMenu != null && listMenu.size() > 0){
			for(Menu menu : listMenu){
				Listitem item = new Listitem();
				item.setAttribute("DATA", menu);
				item.appendChild(new Listcell(menu.getMenuId()));
				item.appendChild(new Listcell(menu.getMenuName()));
				list.appendChild(item);
			}
		}
	}
	
	private boolean isParent(){
		Menu parent = mainService.findMenuById((String) ComponentUtil.getValue(txtMenuParent));
		if (parent != null) {
			if (parent.getUrl() != null) {
				throw new WrongValueException(txtMenuParent, "Is not parent");
			}else{
				return true;
			}
		}else{
			throw new WrongValueException(txtMenuParent, "Parent not found");
		}
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(txtMenuId) == null || ComponentUtil.getValue(txtMenuId).equals("")) {
			throw new WrongValueException(txtMenuId, "Please insert menu id");
		}else if (ComponentUtil.getValue(txtMenuName) == null || ComponentUtil.getValue(txtMenuName).equals("")) {
			throw new WrongValueException(txtMenuName, "Please insert menu name");
		}
		return true;
	}
}
