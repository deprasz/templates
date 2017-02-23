package id.com.templates.model.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import id.com.templates.model.BaseEntity;

@Entity
@Table(name="mst_trx")
public class Transaction extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -159897769809253925L;

	@Column(name="accnbr")
	private String accountNumber;
	@Column(name="txid")
	private String transactionId;
	@Column(name="txdate")
	private Date transactionDate;
	@Column(name="dbcr")
	private Integer debetCredit;
	@Column(name="txmsg")
	private String transactionMessage;
	@Column(name="txamt")
	private BigDecimal transactionAmount;
	@Column(name="tmstamp")
	private Date timestamp;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getDebetCredit() {
		return debetCredit;
	}
	public void setDebetCredit(Integer debetCredit) {
		this.debetCredit = debetCredit;
	}
	public String getTransactionMessage() {
		return transactionMessage;
	}
	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
