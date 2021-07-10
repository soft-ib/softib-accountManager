package com.softib.accountmanager.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.softib.accountmanager.security.MyUserDetailsService;

@Table(name = "Archive")
@javax.persistence.Entity(name = "Archive")
public class Archive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7574839062643235282L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@javax.persistence.Column(name = "type", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)
	private String type;

	@Lob
	@javax.persistence.Column(name = "content", unique = false, nullable = false, insertable = true, updatable = false, length = 512)
	@Basic(fetch = FetchType.LAZY)
	private String content;

	// TECHNICAL

	@javax.persistence.Column(name = "creation_date_", unique = false, nullable = false, insertable = true, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date creation_date_ = null;

	@javax.persistence.Column(name = "creator_user_id_", unique = false, nullable = false, insertable = true, updatable = false, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private String creator_user_id_ = null;

	@javax.persistence.Column(name = "update_date_", unique = false, nullable = false, insertable = true, updatable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Basic(fetch = FetchType.EAGER, optional = false)

	private java.util.Date update_date_ = null;

	@javax.persistence.Column(name = "updator_user_id_", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	@Basic(fetch = FetchType.EAGER, optional = true)

	private String updator_user_id_ = null;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
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

	public java.util.Date getUpdate_date_() {
		return update_date_;
	}

	public void setUpdate_date_(java.util.Date update_date_) {
		this.update_date_ = update_date_;
	}

	public String getUpdator_user_id_() {
		return updator_user_id_;
	}

	public void setUpdator_user_id_(String updator_user_id_) {
		this.updator_user_id_ = updator_user_id_;
	}

	@PrePersist
	public void prePersist() {
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		String user = MyUserDetailsService.getCurrentUser();
		this.setCreation_date_(time);
		this.setCreator_user_id_(user);
		this.setUpdator_user_id_(user);
		this.setUpdate_date_(time);
	}

}
