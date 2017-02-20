package id.com.templates.model.menu;

import id.com.templates.model.pk.RoleMenuPK;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="role_menu")
@IdClass(RoleMenuPK.class)
public class RoleMenu implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -285855639207725507L;
	@Id
	@Column(name="role_id")
	private String roleId;
	@Id
	@Column(name="menu_id")
	private String menuId;

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
