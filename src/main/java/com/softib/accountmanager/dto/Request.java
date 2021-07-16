package com.softib.accountmanager.dto;

import java.io.Serializable;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9188606994059976584L;

	private TransactionType transactionType;
	private Integer sourceAccountId;
	private Integer targetAccountId;
	private String actor;
	private Float amount;

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public void setSourceAccountId(Integer sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public void setTargetAccountId(Integer targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public Integer getSourceAccountId() {
		return sourceAccountId;
	}

	public Integer getTargetAccountId() {
		return targetAccountId;
	}

	public Float getAmount() {
		return amount;
	}

	public String getActor() {
		return actor;
	}

}
