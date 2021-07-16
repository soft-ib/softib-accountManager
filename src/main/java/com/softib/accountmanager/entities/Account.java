package com.softib.accountmanager.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softib.accountmanager.security.MyUserDetailsService;

@Table(name = "Account")
@javax.persistence.Entity(name = "Account")
public class Account implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 3858470153577336304L;

	@Id
	private Integer accIdentifier;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private Float balance;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private int accountNumber;

	@Basic(fetch = FetchType.EAGER, optional = false)
	private String iban;

	@OneToMany(targetEntity = CreditCard.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
	@JsonManagedReference
	private Set<CreditCard> ceditCard = new java.util.HashSet<>();

	@OneToMany(targetEntity = PocketCashCard.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
	@JsonManagedReference
	private Set<PocketCashCard> pocketCashCard = new java.util.HashSet<>();

	// TECHNICAL

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

	@Basic(fetch = FetchType.EAGER, optional = true)

	private String updatorId = null;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accIdentifier == null) ? 0 : accIdentifier.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return accIdentifier + "," + balance + "," + accountNumber + "," + iban + "," + "-CreditCard-" + ceditCard + ","
				+ "*PocketCashCard *" + pocketCashCard + "," + creationDate + "," + creatorId + "," + updateDate + ","
				+ updatorId;

	}
//
//	// THIS METHOD NEED TO BE ADDED TO INTERFACE IMMPLEMENTED BY ALL ENTITIES AND
//	// THE INTLIGENCE IS THAT IT WILL CONVERT ONLY THE COMPOSITIONS SO LATER THE
//	// ARCHIVE ARCHIVE ONLY COMPOSITION AND DELETE THEM WITHOUT ISSUES
//	public String convertToString() {
//		return accIdentifier + "," + balance + "," + accountNumber + "," + "*CreditCard *" + ceditCard + ","
//				+ "*PocketCashCard *" + pocketCashCard + "," + creationDate + "," + creatorId + "," + updateDate + ","
//				+ updatorId;
//	}

	public Account(Integer accIdentifier, Float balance, int accountNumber, String iban, Set<CreditCard> ceditCard,
			Set<PocketCashCard> pocketCashCard, Date creationDate, String creatorId, Date updateDate,
			String updatorId) {
		super();
		this.accIdentifier = accIdentifier;
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.iban = iban;
		this.ceditCard = ceditCard;
		this.pocketCashCard = pocketCashCard;
		this.creationDate = creationDate;
		this.creatorId = creatorId;
		this.updateDate = updateDate;
		this.updatorId = updatorId;
	}

	// Default Constructor
	public Account() {
		super();
	}

	public Account(Integer accIdentifier) {
		super();
		this.accIdentifier = accIdentifier;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public void setAccIdentifier(Integer accIdentifier) {
		this.accIdentifier = accIdentifier;
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

	public Integer getAccIdentifier() {
		return accIdentifier;
	}

	public Set<CreditCard> getCeditCard() {
		return ceditCard;
	}

	public void setCeditCard(Set<CreditCard> ceditCard) {
		this.ceditCard = ceditCard;

	}

	public void addCeditCard(CreditCard ceditCard) {
		ceditCard.setAccount(this);
		this.ceditCard.add(ceditCard);
	}
	// CALL BACKS

	public Set<PocketCashCard> getPocketCashCard() {
		return pocketCashCard;
	}

	public void setPocketCashCard(Set<PocketCashCard> pocketCashCard) {
		this.pocketCashCard = pocketCashCard;
	}

	public void addPocketCash(PocketCashCard pocketCashCard) {
		this.pocketCashCard.add(pocketCashCard);
		pocketCashCard.setAccount(this);
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
	
	

}
