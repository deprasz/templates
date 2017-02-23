package id.com.templates.model.pk;

import java.io.Serializable;

public class AccountUserPK implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 3477380552414656864L;
	String userId;
	String accountNumber;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
