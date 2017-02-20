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
import id.com.templates.model.main.Role;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class RoleConfiguration extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Textbox txtRoleId;
	@Wire Panel panel;
	@Wire Textbox txtRoleName;
	@Wire Listbox list;
	@Wire Button btnSave;
	@Wire Button btnDelete;
	@Wire Button btnReset;
	
	@Autowired MainService mainService;
	
	Boolean onLoad = false;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		txtRoleId.addEventListener(Events.ON_OK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doSearch();
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
			Role role = (Role)item.getAttribute("DATA");
			ComponentUtil.setValue(txtRoleId, role.getRoleId());
			ComponentUtil.setValue(txtRoleName, role.getRoleName());
			onLoad = true;
			txtRoleId.setDisabled(true);
			btnDelete.setDisabled(false);
		}
	}
	
	private void doSearch(){
		doRefreshTable(mainService.findAllRoleById((String) ComponentUtil.getValue(txtRoleId)));
	}
	
	private void doInsert(){
		try {
			Role role = mainService.findRoleById((String) ComponentUtil.getValue(txtRoleId));
			if (role == null) {
				role = new Role();
				role.setRoleId((String) ComponentUtil.getValue(txtRoleId));
				role.setRoleName((String) ComponentUtil.getValue(txtRoleName));
				mainService.saveRole(role);
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
			Role role = (Role) item.getAttribute("DATA");
			role.setRoleName((String) ComponentUtil.getValue(txtRoleName));
			mainService.saveRole(role);
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
			Role role = (Role) item.getAttribute("DATA");
			mainService.deleteRole(role);
			ComponentUtil.success(panel, "Delete Success");
		} catch (Exception e) {
			e.printStackTrace();
			ComponentUtil.failed(panel, "Failed Delete");
		}
		doReset();
	}
	
	private void doReset(){
		ComponentUtil.clear(wnd);
		doRefreshTable(mainService.findAllRole());
		onLoad = false;
		txtRoleId.setDisabled(false);
		btnDelete.setDisabled(true);
		txtRoleId.setFocus(true);
	}
	
	private void doRefreshTable(List<Role> listRole){
		list.getItems().clear();
		if (listRole != null && listRole.size() > 0){
			for(Role role : listRole){
				Listitem item = new Listitem();
				item.setAttribute("DATA", role);
				item.appendChild(new Listcell(role.getRoleId()));
				item.appendChild(new Listcell(role.getRoleName()));
				list.appendChild(item);
			}
		}
	}
	
	private boolean doValidation(){
		if (ComponentUtil.getValue(txtRoleId) == null || ComponentUtil.getValue(txtRoleId).equals("")) {
			throw new WrongValueException(txtRoleId, "Please insert role id");
		}else if (ComponentUtil.getValue(txtRoleName) == null || ComponentUtil.getValue(txtRoleName).equals("")) {
			throw new WrongValueException(txtRoleName, "Please insert role name");
		}
		return true;
	}
}
