package com.softib.accountmanager.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AccountServiceImpl.class);

	@Override
	public Account findById(int id) {
		logger.info("In method findByID");
		Optional<Account> employee = accountRepository.findById(id);
		if (!employee.isPresent()) {
			logger.info("No result find for this ID : " + id);
			return null;
		}
		return employee.get();
	}

	@Override
	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria) {
		logger.info("In method getAllEmployes");
		return (List<Account>) accountRepository.findAll();
	}

	@Override
	public void save(Account account) {

		logger.info("In method addOrUpdateEmploye");
		int accID = -1;
		try {
			accountRepository.save(account);
			logger.info("Out of method addOrUpdateEmploye");
		} catch (IllegalArgumentException e) {
			logger.error("Out of method addOrUpdateEmploye with Errors : " + e);
		} finally {
			logger.info("Out of method addOrUpdateEmploye");

		}
	}

	@Override
	public void delete(Account account) { }

}