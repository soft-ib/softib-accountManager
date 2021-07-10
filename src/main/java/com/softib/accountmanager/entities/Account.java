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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softib.accountmanager.security.MyUserDetailsService;

@Table(name = "Account")
@javax.persistence.Entity(name = "Account")
public class Account implements Serializable {
	public Integer getAcc_identifier() {
		return acc_identifier;
	}

	/**
	* 
	*/
	private static final long serialVersionUID = 3858470153577336304L;

	@Id
	private Integer acc_identifier;

	public void setAcc_identifier(Integer acc_identifier) {
		this.acc_identifier = acc_identifier;
	}

	@javax.persistence.Column(name = "balance", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private Float balance;

	@javax.persistence.Column(name = "accoun_number", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private int accountNumber;

	@javax.persistence.Column(name = "iban", unique = true, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private String Iban;

	@OneToMany(targetEntity = CreditCard.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
	@JsonManagedReference
	private Set<CreditCard> ceditCard = new java.util.HashSet<>();

	@OneToMany(targetEntity = PocketCashCard.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
	@JsonManagedReference
	private Set<PocketCashCard> pocketCashCard = new java.util.HashSet<>();

	// TECHNICAL

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@javax.persistence.Column(name = "creation_date_", unique = false, nullable = false, insertable = true, updatable = false)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creation_date_ = null;

	@javax.persistence.Column(name = "creator_user_id_", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creator_user_id_ = null;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@javax.persistence.Column(name = "update_date_", unique = false, nullable = false, insertable = true, updatable = true)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date update_date_ = null;

	@javax.persistence.Column(name = "updator_user_id_", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = true)

	private String updator_user_id_ = null;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acc_identifier == null) ? 0 : acc_identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (acc_identifier == null) {
			if (other.acc_identifier != null)
				return false;
		} else if (!acc_identifier.equals(other.acc_identifier))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return acc_identifier + "," + balance + "," + accountNumber + "," + Iban + "," + "-CreditCard-" + ceditCard
				+ "," + "*PocketCashCard *" + pocketCashCard + "," + creation_date_ + "," + creator_user_id_ + ","
				+ update_date_ + "," + updator_user_id_;

	}

	// THIS METHOD NEED TO BE ADDED TO INTERFACE IMMPLEMENTED BY ALL ENTITIES AND
	// THE INTLIGENCE IS THAT IT WILL CONVERT ONLY THE COMPOSITIONS SO LATER THE
	// ARCHIVE ARCHIVE ONLY COMPOSITION AND DELETE THEM WITHOUT ISSUES
	public String convertToString() {
		return acc_identifier + "," + balance + "," + accountNumber + "," + "*CreditCard *" + ceditCard + ","
				+ "*PocketCashCard *" + pocketCashCard + "," + creation_date_ + "," + creator_user_id_ + ","
				+ update_date_ + "," + updator_user_id_;
	}

	public Account(Integer acc_identifier, Float balance, Integer accountNumber, String iban, Set<CreditCard> ceditCard,
			Set<PocketCashCard> pocketCashCard, Date creation_date_, String creator_user_id_, Date update_date_,
			String updator_user_id_) {
		super();
		this.acc_identifier = acc_identifier;
		this.balance = balance;
		this.accountNumber = accountNumber;
		Iban = iban;
		this.ceditCard = ceditCard;
		this.pocketCashCard = pocketCashCard;
		this.creation_date_ = creation_date_;
		this.creator_user_id_ = creator_user_id_;
		this.update_date_ = update_date_;
		this.updator_user_id_ = updator_user_id_;
	}

	public Account(Float balance, String creator_userid) {
		super();
		this.balance = balance;

		this.creator_user_id_ = creator_userid;
	}

	// Default Constructor
	public Account() {
		super();
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Account(Integer acc_identifier) {
		super();
		this.acc_identifier = acc_identifier;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIban() {
		return Iban;
	}

	public void setIban(String iban) {
		Iban = iban;
	}

	public java.util.Date getCreationDate() {
		return creation_date_;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creation_date_ = creationDate;
	}

	public String getCreatorUserId() {
		return creator_user_id_;
	}

	public void setCreatorUserId(String creatorUserId) {
		this.creator_user_id_ = creatorUserId;
	}

	public java.util.Date getUpdateDate() {
		return update_date_;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.update_date_ = updateDate;
	}

	public String getUpdatorUserId() {
		return updator_user_id_;
	}

	public void setUpdatorUserId(String updatorUserId) {
		this.updator_user_id_ = updatorUserId;
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
		this.setCreatorUserId(user);
		this.setUpdatorUserId(user);
		this.setUpdateDate(time);
	}

}
