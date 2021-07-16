package com.softib.accountmanager.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.Archive;
import com.softib.accountmanager.exception.AccountOperationException;
import com.softib.accountmanager.exception.EntityNotFoundException;
import com.softib.accountmanager.exception.ProcessException;
import com.softib.accountmanager.repositories.AccountRepository;
import com.softib.accountmanager.repositories.AccountRepositoryCustom;
import com.softib.accountmanager.repositories.ArchiveRepository;

@Service
public class AccountServiceImpl extends ArchiveServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountRepositoryCustom accountRepositoryCustom;

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
			return null;
		}
		return employee.get();
	}

	@Override
	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria) {
		return accountRepository.findAll();
	}

	@Override
	public void save(Account account) {

		logger.info("In method save account");
		try {
			accountRepository.save(account);
		} catch (IllegalArgumentException e) {
			throw new ProcessException("saving Account with id" + account.getAccIdentifier(), e);
		}
	}

	@Override
	public void update(Account account) {

		logger.info("In method update account");
		try {
			Account accountDB = findById(account.getAccIdentifier());
			if (accountDB == null) {
				throw new EntityNotFoundException(Account.class.getName(), account.getAccIdentifier());

			}

			account.setCreationDate(accountDB.getCreationDate());
			account.setCreatorId(accountDB.getCreatorId());
			accountRepository.save(account);
			accountRepository.flush();
		} catch (IllegalArgumentException e) {
			throw new ProcessException("saving Account with id" + account.getAccIdentifier(), e);
		}
	}

	@Override
	public void delete(Account account) {
		logger.info("In method delete account");
		try {
			Optional<Account> accountDB = accountRepository.findById(account.getAccIdentifier());
			if (!accountDB.isPresent()) {
				throw new EntityNotFoundException("Account", account.getAccIdentifier());
			}
			accountRepository.delete(accountDB.get());
		} catch (IllegalArgumentException e) {
			throw new ProcessException("Deleting account with id : " + account.getAccIdentifier(), e);
		}
	}

	@Transactional
	@Override
	public void archive(Integer id) {
		Optional<Account> accountDB = accountRepository.findById(id);
		if (!accountDB.isPresent()) {
			throw new EntityNotFoundException("Account", id);
		}
		String content = accountDB.get().toString();
		String type = accountDB.get().getClass().getName();
		Archive arch = new Archive();
		arch.setType(type);
		arch.setContent(content);
		arch.setContentId(accountDB.get().getAccIdentifier());
		archiveRepository.save(arch);
		accountRepository.deleteById(id);

	}

	@Override
	public void restore(Integer id) {
		Optional<Archive> archiveDB = archiveRepository.findByContentId(id);
		if (!archiveDB.isPresent()) {
			throw new EntityNotFoundException("Archive", id);
		}
		Account acc = conversionService.convert(archiveDB.get().getContent(), Account.class);
		accountRepository.save(acc);

	}

	@Override
	public void retrieve(Integer id, Float amount) {
		int i = this.accountRepositoryCustom.withdraw(id, amount);
		if (i > 0) {
			logger.info("Withdraw completed ");

		} else {
			insufficientFundMessage(id, null);

		}

	}

	@Override
	public void deposit(Integer id, Float amount) {
		int i = this.accountRepositoryCustom.deposit(id, amount);
		if (i > 0) {
			logger.info("Deposit completed ");
		} else {
			String source = id.toString();
			String replaceDPartsource = source.substring(4);
			source = source.replace(replaceDPartsource, "xxxx");
			throw new AccountOperationException("Deposite to account" + source);
		}

	}

	@Override
	public synchronized void transfert(Integer sourceID, Integer targetID, Float amount) {
		int i = this.accountRepositoryCustom.transfert(sourceID, targetID, amount);
		if (i > 0) {
			logger.error("Transfert Approuved ");
		} else {
			insufficientFundMessage(sourceID, targetID);
		}

	}

	private void insufficientFundMessage(Integer sourceID, Integer targetID) {
		String source = sourceID.toString();
		String target = targetID != null ? targetID.toString() : null;
		String replaceDPartsource = source.substring(4);
		String replaceDParttarget = target != null ? target.substring(4) : null;
		source = source.replace(replaceDPartsource, "xxxx");
		target = replaceDParttarget != null ? target.replace(replaceDParttarget, "xxxx") : null;

		if (target == null) {
			throw new AccountOperationException(
					"Withdraw from account " + source + " has failed, funds are insufficient ");
		}
		throw new AccountOperationException(
				"Transfert from " + source + " to " + target + " has failed, funds are insufficient ");
	}

}
