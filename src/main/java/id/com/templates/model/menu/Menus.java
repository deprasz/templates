package id.com.templates.model.menu;

import javax.persistence.Column;
import javax.persistence.Entity;

import id.com.templates.model.BaseEntity;

@Entity
public class Menus extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8782021401330853580L;
	@Column(name="menu_id")
	private String menuId;
	@Column(name="parent")
	private String menuParent;
	@Column(name="menu_name")
	private String menuName;
	@Column(name="role_id")
	private String roleId;
	@Column(name="url")
	private String url;
	@Column(name="pick")
	private String pick;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPick() {
		return pick;
	}

	public void setPick(String pick) {
		this.pick = pick;
	}
}
