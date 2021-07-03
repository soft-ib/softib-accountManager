package com.softib.accountmanager.controllers;

import java.util.Collection;

import com.softib.accountmanager.entities.Account;

/**
 * @author jsaoudi
 *
 */
public interface AccountController {
	
	public Account findById(int id);

	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria);

	public void save(Account account);

	public void delete(Account account);
}
