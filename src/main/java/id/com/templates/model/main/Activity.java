package id.com.templates.model.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import id.com.templates.model.BaseEntity;

@Entity
@Table(name="activity")
public class Activity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4089061561186133681L;
	@Column(name="user_id")
	private String userId;
	@Column(name="login")
	private Date login;
	@Column(name="logout")
	private Date logout;
	@Column(name="activity")
	private String activity;
	@Column(name="times")
	private Date times;
	@Column(name="status")
	private Integer status;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getLogin() {
		return login;
	}
	public void setLogin(Date login) {
		this.login = login;
	}
	public Date getLogout() {
		return logout;
	}
	public void setLogout(Date logout) {
		this.logout = logout;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Date getTimes() {
		return times;
	}
	public void setTimes(Date times) {
		this.times = times;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
