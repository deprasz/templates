package id.com.templates.viewmodel;

import java.util.Collection;
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
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.A;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

import id.com.templates.model.menu.Menu;
import id.com.templates.security.AuthService;

@org.springframework.stereotype.Component
@Scope("desktop")
public class MenuModelViewer extends SelectorComposer<Component>{

	/**
	 *
	 */
	private static final long serialVersionUID = 8419083494173532952L;
	@Autowired AuthService authService;
	@Wire Navbar sidebar;
	@Wire Include incMenu;
	@Wire A aCollapse;
	@Wire Textbox txtSearch;

	public HashMap<String, Navitem> mapNavitem;
	public HashMap<String, Nav> mapNav;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		aCollapse.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) throws Exception {
				if (sidebar.isCollapsed()) {
					sidebar.setCollapsed(false);
				}else{
					sidebar.setCollapsed(true);
				}
			}
		});

		txtSearch.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e) throws Exception {
				onFilterMenu(txtSearch.getValue());
			}
		});
		doLoadMenu();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doLoadMenu(){
		sidebar.getChildren().clear();
		mapNav = new HashMap<String, Nav>();
		mapNavitem = new HashMap<String, Navitem>();
		EventListener selectItem = new EventListener() {
			public void onEvent(Event e) throws Exception {
				onSelectItem((Navitem)e.getTarget());
			}
		};

		List<Menu> items = authService.userDetails().getItems();
		for (Menu item : items) {
			if (item.getUrl() != null) {
				Navitem navItem = new Navitem();
				navItem.setAttribute("id", item.getMenuId());
				navItem.setAttribute("name", item.getMenuName());
				navItem.setAttribute("parent", item.getMenuParent());
				navItem.setAttribute("url", item.getUrl());
				navItem.setLabel(item.getMenuName());
				navItem.addEventListener(Events.ON_CLICK, selectItem);
				mapNavitem.put(item.getMenuId(), navItem);
			}else{
				Nav nav = new Nav();
				nav.setAttribute("id", item.getMenuId());
				nav.setAttribute("name", item.getMenuName());
				nav.setAttribute("parent", item.getMenuParent());
				nav.setAttribute("url", item.getUrl());
				nav.setIconSclass("z-icon-th-list");
				nav.setLabel(item.getMenuName());
				mapNav.put(item.getMenuId(), nav);
			}
		}

		for (Menu item : items) {
			if (item.getUrl() != null) {
				Navitem navItem = mapNavitem.get(item.getMenuId());
				if (item.getMenuParent() != null) {
					Nav navParent = mapNav.get(item.getMenuParent());
					if (navParent == null) {
						continue;
					}else{
						navParent.appendChild(navItem);
					}
				}else{
					sidebar.appendChild(navItem);
				}
			}else{
				Nav nav = mapNav.get(item.getMenuId());
				if (item.getMenuParent() != null) {
					Nav navParent = mapNav.get(item.getMenuParent());
					if (navParent == null) {
						continue;
					}else{
						nav.setIconSclass("fa fa-minus-square-o");
						navParent.appendChild(nav);
					}
				}else{
					sidebar.appendChild(nav);
				}
			}
		}

		Navitem logout = new Navitem();
		logout.setIconSclass("fa fa-sign-out");
		logout.setHref("/logout");
		logout.setLabel("Logout");
		sidebar.appendChild(logout);
	}

	public void onFilterMenu(String name){
		Collection<Navitem> values = mapNavitem.values();
		if(name != null && !name.isEmpty()){
			sidebar.getChildren().clear();
			for (Navitem item : values) {
				if(((String)item.getAttribute("name")).toUpperCase().contains(name.toUpperCase())){
					if(item.getAttribute("url") != null) sidebar.appendChild(item);
				}
			}
		}else{
			doLoadMenu();
		}
	}

	public void onSelectItem(Navitem item) {
		if (item != null) {
			Include include = (Include) incMenu;
			include.setSrc((String) item.getAttribute("url"));
			include.invalidate();
		}
	}
}
