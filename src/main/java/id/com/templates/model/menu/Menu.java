package id.com.templates.model.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import id.com.templates.model.BaseEntity;

@Entity
@Table(name="menu")
public class Menu extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3139909122329934551L;
	@Column(name="menu_id")
	private String menuId;
	@Column(name="menu_name")
	private String menuName;
	@Column(name="parent")
	private String menuParent;
	@Column(name="url")
	private String url;
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuParent() {
		return menuParent;
	}
	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
