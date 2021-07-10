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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.softib.accountmanager.security.MyUserDetailsService;

@Table(name = "credit_card")
@javax.persistence.Entity(name = "CreditCard")
public class CreditCard implements Serializable {

	public CreditCard(Integer card_identifier, String card_type, String card_number, int cvv, Date exp_date,
			boolean isactive, Account account, Date creation_date_, String creator_user_id_, Date update_date_,
			String updator_user_id_) {
		super();
		this.card_identifier = card_identifier;
		this.card_type = card_type;
		this.card_number = card_number;
		this.cvv = cvv;
		this.exp_date = exp_date;
		this.isactive = isactive;
		this.account = account;
		this.creation_date_ = creation_date_;
		this.creator_user_id_ = creator_user_id_;
		this.update_date_ = update_date_;
		this.updator_user_id_ = updator_user_id_;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9024324742054605356L;
	/**
	* 
	*/

	@Id
	private Integer card_identifier;

	@javax.persistence.Column(name = "card_type", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private String card_type;

	@javax.persistence.Column(name = "card_number", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private String card_number;

	@javax.persistence.Column(name = "cvv", unique = true, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private int cvv;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@javax.persistence.Column(name = "exp_date", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private Date exp_date;

	@javax.persistence.Column(name = "isactive", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private boolean isactive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Account account;

	// TECHNICAL
	public CreditCard() {
		super();
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@javax.persistence.Column(name = "creation_date_", unique = false, nullable = false, insertable = true, updatable = false)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creation_date_ = null;

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

	@javax.persistence.Column(name = "creator_user_id_", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creator_user_id_ = null;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	@javax.persistence.Column(name = "update_date_", unique = false, nullable = false, insertable = true, updatable = true)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date update_date_ = null;

	@javax.persistence.Column(name = "updator_user_id_", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private String updator_user_id_ = null;

	public String getCardType() {
		return card_type;
	}

	public void setCardType(String cardType) {
		this.card_type = cardType;
	}

	public String getCardNumber() {
		return card_number;
	}

	public void setCardNumber(String cardNumber) {
		this.card_number = cardNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public Date getExpDate() {
		return exp_date;
	}

	public void setExpDate(Date expDate) {
		this.exp_date = expDate;
	}

	public boolean isActive() {
		return isactive;
	}

	public void setActive(boolean isActive) {
		this.isactive = isActive;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return ";" + card_identifier + "," + card_type + "," + card_number + "," + cvv + "," + exp_date + "," + isactive
				+ "," + account.getAcc_identifier() + "," + creation_date_ + "," + creator_user_id_ + "," + update_date_
				+ "," + updator_user_id_;
	}

	public String convertToString() {
		return card_identifier + "," + card_type + "," + card_number + "," + cvv + "," + exp_date + "," + isactive + ","
				+ account + "," + creation_date_ + "," + creator_user_id_ + "," + update_date_ + "," + updator_user_id_;
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
