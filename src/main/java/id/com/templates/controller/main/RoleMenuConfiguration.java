package id.com.templates.controller.main;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import id.com.templates.common.ComponentUtil;
import id.com.templates.model.main.Role;
import id.com.templates.model.menu.Menus;
import id.com.templates.model.menu.RoleMenu;
import id.com.templates.service.MainService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class RoleMenuConfiguration extends SelectorComposer<Component>{
	@Wire Borderlayout wnd;
	@Wire Panel panel;
	@Wire Combobox cmbRoleId;
	@Wire Tree tree;
	@Wire Button btnSave;
	@Wire Button btnDelete;
	@Wire Button btnReset;
	@Autowired MainService services;
	Boolean onLoad = false;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		cmbRoleId.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
        		doSearch();
            }
        });
		
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
		doLoadCombo();
		doReset();
		tree.clear();
		tree.getChildren().clear();
	}
	
	public void doLoadCombo() {
		cmbRoleId.getItems().clear();
		cmbRoleId.setSelectedIndex(-1);
		List<Role> listRole = services.findAllRole();
		for (Role role : listRole) {
			Comboitem item = new Comboitem();
			item.setLabel(role.getRoleId() + " - " + role.getRoleName());
			item.setValue(role.getRoleId());
			cmbRoleId.appendChild(item);
		}
	}
	
	public void doSave(){
		if (ComponentUtil.getValue(cmbRoleId) != null) {
			List<RoleMenu> listRole = services.findAllRoleMenuById((String) ComponentUtil.getValue(cmbRoleId));
			services.deleteAllRoleMenu(listRole);
			for (Object obj : tree.getItems()) {
				Treeitem item = (Treeitem) obj;
				if (item.isSelected()) {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRoleId((String) ComponentUtil.getValue(cmbRoleId));
					roleMenu.setMenuId((String)item.getValue());
					services.saveRoleMenu(roleMenu);
				}
			}
			ComponentUtil.success(panel, "Save Success");
		}
		doReset();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doSearch(){
		tree.clear();
		tree.getChildren().clear();
		tree.invalidate();
		Treechildren root = new Treechildren();
		if (ComponentUtil.getValue(cmbRoleId) != null) {
			List<Menus> listMenu = services.findAllMenusById((String) ComponentUtil.getValue(cmbRoleId));
			HashMap mapTree = new HashMap();
			for (Menus menu : listMenu) {
				Treeitem item = new Treeitem();
				item.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event e) throws Exception {
						doCheck((Treeitem)e.getTarget());
					}
				});
				Treerow rowTree = new Treerow();
				
				mapTree.put(menu.getMenuId(), item);
				item.setValue(menu.getMenuId());
				item.appendChild(rowTree);
				item.setAttribute("MENU_ID", menu.getMenuId());
				rowTree.appendChild(new Treecell(menu.getMenuId() + " - " + menu.getMenuName()));
				if(!menu.getPick().equals("0")){
					item.setSelected(true);
				}
				if (menu.getMenuParent() != null) {
					Treeitem itemParent = (Treeitem) mapTree.get(menu.getMenuParent());
					if (itemParent==null) continue;
					Treechildren parent = (Treechildren) itemParent.getAttribute("PARENT");
					if (parent == null) {
						parent = new Treechildren();
						itemParent.appendChild(parent);
						itemParent.setAttribute("PARENT", parent);
					}
					parent.appendChild(item);
				} else {
					root.appendChild(item);
				}
			}
		}
		tree.appendChild(root);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doCheck(Treeitem item){
		List childrens = new ArrayList();
		childrens.add(item.getChildren());
		for (int i = 0; i < childrens.size(); i++) {
			List children=(List)childrens.get(i);
			for (Object obj : (List)children) {
				if (obj instanceof Treeitem){
					((Treeitem)obj).setSelected(item.isSelected());
					childrens.add(((Treeitem)obj).getChildren());
				} else if (obj instanceof Treechildren){
					childrens.add(((Treechildren)obj).getChildren());
				}
			}
		}
		
		if (item.isSelected()){
			Component cmp = item.getParent();
			while(cmp != null){
				if (cmp instanceof Treeitem)
					((Treeitem)cmp).setSelected(true);
				cmp=cmp.getParent();
			}
		}
	}
	
	public void doReset(){
		ComponentUtil.clear(wnd);
		tree.clear();
		tree.getChildren().clear();
		cmbRoleId.setSelectedIndex(-1);
	}
}
