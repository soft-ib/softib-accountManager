package com.softib.accountmanager.controllers;

import java.util.Collection;

import com.softib.accountmanager.entities.CreditCard;

public interface CreditCardController {

	public CreditCard findById(int id);

	public Collection<CreditCard> find(Integer pageSize, Integer pageNumber, String criteria);

	public void save(CreditCard account);

	public void delete(CreditCard account);
}
