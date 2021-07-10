package com.softib.accountmanager.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.Archive;
import com.softib.accountmanager.repositories.AccountRepository;
import com.softib.accountmanager.repositories.ArchiveRepository;

@Service
public class AccountServiceImpl extends ArchiveServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ArchiveRepository archiveRepository;

	@Autowired
	ConversionService conversionService;

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
	public void delete(Account account) {
		logger.info("In method deleteEmployeById");
		try {
			Optional<Account> accountDB = accountRepository.findById(account.getAcc_identifier());

			// Desaffecter l'employe de tous les departements
			// c'est le bout master qui permet de mettre a jour
			// la table d'association
			logger.info("Out of method deleteEmployeById");
			if (!accountDB.isPresent()) {
				logger.error("ID does not Exist");
				throw new IllegalArgumentException("ID does not Exist");
			}

			accountRepository.delete(accountDB.get());
		} catch (IllegalArgumentException e) {
			logger.error("Out of method deleteEmployeById with Errors : " + e);
		} finally {
			logger.info("Out of method deleteEmployeById");

		}
	}

	@Override
	public void archive(Integer id) {
		Optional<Account> accountDB = accountRepository.findById(id);
		if (accountDB.isPresent()) {
			String content = accountDB.get().toString();
			String type = accountDB.get().getClass().getName();
			Archive arch = new Archive();
			arch.setType(type);
			arch.setContent(content);
			archiveRepository.save(arch);
			accountRepository.deleteById(id);
		}

	}

	@Override
	public void restore(Integer id) {
		Optional<Archive> archiveDB = archiveRepository.findById(id);
		if (archiveDB.isPresent()) {
			Account acc = conversionService.convert(archiveDB.get().getContent(), Account.class);
			accountRepository.save(acc);
		}

	}
}
