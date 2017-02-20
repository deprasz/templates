package id.com.templates.model.pk;

import java.io.Serializable;

public class RoleMenuPK implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 3477380552414656864L;
	String roleId;
	String menuId;

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
