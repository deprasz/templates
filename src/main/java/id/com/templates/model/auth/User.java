package id.com.templates.model.auth;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import id.com.templates.model.AbstractAuditingEntity;

@Entity
@Table(name="sys_user")
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractAuditingEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = -1809090182439279014L;
	@Id
	@Column(name="user_id")
	private String userId;
	@Column(name="username")
	private String userName;
	@Column(name="password")
	private String password;
	@Column(name="activated")
	private boolean activated;
	@Column(name="role")
	private String roleId;
	@Column(name="login_failed")
	private int failedLogin;
	@Column(name="limit_failed")
	private int limitFailed;
	@Column(name="locked")
	private boolean locked;
	@Column(name="email")
	private String email;
	@Column(name="lang_key")
	private String language;
	@Column(name="activation_key")
	private String activationKey;
	@Column(name="reset_key")
	private String resetKey;
	@Column(name="reset_date")
	private Date resetDate;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public int getFailedLogin() {
		return failedLogin;
	}
	public void setFailedLogin(int failedLogin) {
		this.failedLogin = failedLogin;
	}
	public int getLimitFailed() {
		return limitFailed;
	}
	public void setLimitFailed(int limitFailed) {
		this.limitFailed = limitFailed;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getActivationKey() {
		return activationKey;
	}
	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}
	public String getResetKey() {
		return resetKey;
	}
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}
	public Date getResetDate() {
		return resetDate;
	}
	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}
}
