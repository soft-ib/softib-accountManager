package com.softib.accountmanager.services;

import java.util.Collection;

import com.softib.accountmanager.entities.Account;

public interface AccountService extends ArchiveService {

	public Account findById(int id);

	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria);

	public void save(Account account);

	public void delete(Account account);

	public void retrieve(Integer id, Float amount);

	public void deposit(Integer id, Float amount);

	public void transfert(Integer sourceID, Integer targetID, Float amount);

	public void update(Account account);

}
