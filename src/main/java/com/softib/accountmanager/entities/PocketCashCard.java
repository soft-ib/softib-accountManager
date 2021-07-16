package com.softib.accountmanager.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.softib.accountmanager.enumimration.VersType;
import com.softib.accountmanager.security.MyUserDetailsService;

@Table(name = "pocket_cash_card")
@javax.persistence.Entity(name = "PocketCashCard")
public class PocketCashCard implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -890990400972187441L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cardIdentifier;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private String cardNumber;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private Date expDate;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private boolean isActive;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private boolean isperiodic;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private int versDay;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private VersType versType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Account account;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date updateDate = null;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date balanceUpdateDate = null;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private Float balance;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private Float amount;
	// TECHNICAL

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creationDate = null;

	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creatorId = null;

	@Basic(fetch = FetchType.EAGER, optional = true)

	private String updatorId = null;

	// END TECHNICAL

	// CALL BACKS
	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	@PrePersist
	public void prePersist() {
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		String user = MyUserDetailsService.getCurrentUser();
		this.setCreationDate(time);
		this.setCreatorId(user);
		this.setUpdatorId(user);
		this.setUpdateDate(time);
	}

	@PreUpdate
	public void preUpdate() {
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		String user = MyUserDetailsService.getCurrentUser();
		this.setUpdatorId(user);
		this.setUpdateDate(time);
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isIsperiodic() {
		return isperiodic;
	}

	public void setIsperiodic(boolean isperiodic) {
		this.isperiodic = isperiodic;
	}

	public int getVersDay() {
		return versDay;
	}

	public void setVersDay(int versDay) {
		this.versDay = versDay;
	}

	public VersType getVersType() {
		return versType;
	}

	public void setVersType(VersType versType) {
		this.versType = versType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getUpdatorId() {
		return updatorId;
	}

	public void setUpdatorId(String updatorId) {
		this.updatorId = updatorId;
	}

	public Integer getCardIdentifier() {
		return cardIdentifier;
	}

	public java.util.Date getBalanceUpdateDate() {
		return balanceUpdateDate;
	}

	public void setBalanceUpdateDate(java.util.Date balanceUpdateDate) {
		this.balanceUpdateDate = balanceUpdateDate;
	}

}
