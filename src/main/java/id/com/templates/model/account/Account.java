package id.com.templates.model.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mst_acc")
public class Account implements Serializable{

	private static final long serialVersionUID = 7063716663387942925L;

	@Id
	@Column(name="accnbr")
	private String accountNumber;

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

}
