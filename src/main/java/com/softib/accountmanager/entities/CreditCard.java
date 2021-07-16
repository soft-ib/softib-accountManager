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
import com.softib.accountmanager.security.MyUserDetailsService;

@Table(name = "credit_card")
@javax.persistence.Entity(name = "CreditCard")
public class CreditCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9024324742054605356L;

	public Integer getCardIdentifier() {
		return cardIdentifier;
	}

	/**
	 * card_identifier
	 */

	@Id
	private Integer cardIdentifier;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private String cardType;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private String cardNumber;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private int cvv;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private Date expDate;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Account account;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creationDate = null;

	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creatorId = null;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date updateDate = null;

	@Basic(fetch = FetchType.EAGER, optional = false)

	private String updatorId = null;

	// TECHNICAL
	public CreditCard() {
		super();
	}

	public CreditCard(Integer cardIdentifier, String cardType, String cardNumber, int cvv, Date expDate,
			boolean isActive, Account account, Date creationDate, String creatorId, Date updateDate, String updatorId) {
		super();
		this.cardIdentifier = cardIdentifier;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expDate = expDate;
		this.isActive = isActive;
		this.account = account;
		this.creationDate = creationDate;
		this.creatorId = creatorId;
		this.updateDate = updateDate;
		this.updatorId = updatorId;
	}

	@Override
	public String toString() {
		return ";" + cardIdentifier + "," + cardType + "," + cardNumber + "," + cvv + "," + expDate + "," + isActive
				+ "," + account.getAccIdentifier() + "," + creationDate + "," + creatorId + "," + updateDate + ","
				+ updatorId;
	}

//	public String convertToString() {
//		return cardIdentifier + "," + cardType + "," + cardNumber + "," + cvv + "," + expDate + "," + isActive + ","
//				+ account + "," + creationDate + "," + creatorId + "," + updateDate + "," + updatorId;
//	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatorId() {
		return updatorId;
	}

	public void setUpdatorId(String updatorId) {
		this.updatorId = updatorId;
	}

	// CALL BACKS
	@PrePersist
	public void prePersist() {
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		String user = MyUserDetailsService.getCurrentUser();
		this.setCreatorId(user);
		this.setCreationDate(time);
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
}
