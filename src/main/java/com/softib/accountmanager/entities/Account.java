package com.softib.accountmanager.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer acc_identifier;

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

	// TECHNICAL

	@javax.persistence.Column(name = "creation_date_", unique = false, nullable = false, insertable = true, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creation_date_ = null;

	@javax.persistence.Column(name = "creator_user_id_", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creator_user_id_ = null;

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

	@javax.persistence.Column(name = "update_date_", unique = false, nullable = false, insertable = true, updatable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date update_date_ = null;

	@javax.persistence.Column(name = "updator_user_id_", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = true)

	private String updator_user_id_ = null;

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
		this.ceditCard.add(ceditCard);
		ceditCard.setAccount(this);
	}
	// CALL BACKS

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
