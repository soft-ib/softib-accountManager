package com.softib.accountmanager.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.Archive;
import com.softib.accountmanager.entities.PocketCashCard;
import com.softib.accountmanager.enumimration.VersType;
import com.softib.accountmanager.exception.AccountOperationException;
import com.softib.accountmanager.exception.EntityNotFoundException;
import com.softib.accountmanager.exception.ProcessException;
import com.softib.accountmanager.repositories.AccountRepository;
import com.softib.accountmanager.repositories.AccountRepositoryCustom;
import com.softib.accountmanager.repositories.ArchiveRepository;
import com.softib.accountmanager.repositories.PocketCashCardRepository;

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

	@Autowired
	PocketCashCardRepository pocketCashCardRepository;

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

	@Override
	public void updateCardsBalance(String cardType, Integer sourceID, Integer targetID, Float amount) {
		accountRepositoryCustom.transfertFromAccountToCard(sourceID, targetID, amount, cardType);

	}

	@PostConstruct
	private void init() {

		Runnable helloRunnable = new Runnable() {
			public void run() {

				try {
					SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
					Date time = sdformat.parse(new Date().toString());
					List<PocketCashCard> pocketCashTodayVers = pocketCashCardRepository.findAll().stream()
							.filter(e -> e.isIsperiodic() && !(time.equals(e.getBalanceUpdateDate())))
							.collect(Collectors.toList());

					pocketCashTodayVers.stream()
							.filter(d -> VersType.HEBDO.equals(d.getVersType()) && d.getVersDay() == time.getDay())
							.forEach(m -> accountRepositoryCustom.transfertFromAccountToCard(
									m.getAccount().getAccIdentifier(), m.getCardIdentifier(), m.getAmount(),
									PocketCashCard.class.getName()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 10, TimeUnit.SECONDS);

	}

}
