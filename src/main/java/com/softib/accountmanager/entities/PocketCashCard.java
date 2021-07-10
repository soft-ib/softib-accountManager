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
	private Integer card_identifier;

	@javax.persistence.Column(name = "card_number", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private String card_number;

	@javax.persistence.Column(name = "exp_date", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private Date exp_date;

	@javax.persistence.Column(name = "isactive", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private boolean isactive;

	@javax.persistence.Column(name = "isperiodic", unique = false, nullable = true, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private boolean isperiodic;

	@javax.persistence.Column(name = "vers_day", unique = false, nullable = true, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private int vers_day;

	@javax.persistence.Column(name = "vers_day_permounth", unique = false, nullable = true, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private int vers_day_permounth;

	@javax.persistence.Column(name = "vers_type", unique = false, nullable = true, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private VersType vers_type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Account account;

	public boolean isIsperiodic() {
		return isperiodic;
	}

	public void setIsperiodic(boolean isperiodic) {
		this.isperiodic = isperiodic;
	}

	public int getVers_day() {
		return vers_day;
	}

	public void setVers_day(int vers_day) {
		this.vers_day = vers_day;
	}

	public int getVers_day_permounth() {
		return vers_day_permounth;
	}

	public void setVers_day_permounth(int vers_day_permounth) {
		this.vers_day_permounth = vers_day_permounth;
	}

	public VersType getVers_type() {
		return vers_type;
	}

	public void setVers_type(VersType vers_type) {
		this.vers_type = vers_type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@javax.persistence.Column(name = "balance", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private Float balance;

	// TECHNICAL

	@javax.persistence.Column(name = "creation_date_", unique = false, nullable = false, insertable = true, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creation_date_ = null;

	@javax.persistence.Column(name = "creator_user_id_", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creator_user_id_ = null;

	@javax.persistence.Column(name = "updator_user_id_", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = true)

	private String updator_user_id_ = null;

	// END TECHNICAL

	public Integer getCard_identifier() {
		return card_identifier;
	}

	public java.util.Date getCreation_date_() {
		return creation_date_;
	}

	public void setCreation_date_(java.util.Date creation_date_) {
		this.creation_date_ = creation_date_;
	}

	public String getCreator_user_id_() {
		return creator_user_id_;
	}

	public void setCreator_user_id_(String creator_user_id_) {
		this.creator_user_id_ = creator_user_id_;
	}

	public void setCard_identifier(Integer card_identifier) {
		this.card_identifier = card_identifier;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public Date getExp_date() {
		return exp_date;
	}

	public void setExp_date(Date exp_date) {
		this.exp_date = exp_date;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getUpdator_user_id_() {
		return updator_user_id_;
	}

	public void setUpdator_user_id_(String updator_user_id_) {
		this.updator_user_id_ = updator_user_id_;
	}
	// CALL BACKS

	@PrePersist
	public void prePersist() {
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		String user = MyUserDetailsService.getCurrentUser();
		this.setCreation_date_(time);
		this.setCreator_user_id_(user);
		this.setUpdator_user_id_(user);
	}

}
