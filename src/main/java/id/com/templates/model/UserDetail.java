package id.com.templates.model;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import id.com.templates.model.menu.Menu;

public class UserDetail implements UserDetails{

	private static final long serialVersionUID = 9048890110716808031L;

	String password;

    String activeRole;

    String userId;

    String userName;

	boolean activated;

	boolean locked;

    List<NamingGrantedAuthority> authorities;

    List<Menu> items;

    Date lastLogin;

    Date loginTime;

    Date lastLogout;


	@Override
	public String getUsername() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<NamingGrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof UserDetail) {
			return userId.equals(((UserDetail)o).userId);
		}
		return false;
	}

	@Override
	public int hashCode(){
		return userId.hashCode();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setActiveRole(String activeRole) {
		this.activeRole = activeRole;
	}

	public String getActiveRole() {
		return activeRole;
	}

	public void setAuthorities(List<NamingGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Menu> getItems() {
		return items;
	}

	public void setItems(List<Menu> items) {
		this.items = items;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLastLogout() {
		return lastLogout;
	}

	public void setLastLogout(Date lastLogout) {
		this.lastLogout = lastLogout;
	}
}
