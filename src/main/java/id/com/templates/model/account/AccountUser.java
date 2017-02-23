package id.com.templates.model.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import id.com.templates.model.pk.AccountUserPK;

@Entity
@Table(name="account_user")
@IdClass(AccountUserPK.class)
public class AccountUser implements Serializable{

	private static final long serialVersionUID = 7063716663387942925L;

	@Id
	@Column(name="accnbr")
	private String accountNumber;
	
	@Id
	@Column(name="user_id")
	private String userId;

	@Column(name="accname")
	private String accountName;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
