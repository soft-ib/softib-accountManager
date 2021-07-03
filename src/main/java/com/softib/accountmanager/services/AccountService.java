package com.softib.accountmanager.services;

import java.util.Collection;

import com.softib.accountmanager.entities.Account;

public interface AccountService {

	public Account findById(int id);

	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria);

	public void save(Account account);
	
	public void delete(Account account);

}
