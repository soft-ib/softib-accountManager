package com.softib.accountmanager.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softib.accountmanager.dto.Request;
import com.softib.accountmanager.dto.TransactionType;
import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.Archive;
import com.softib.accountmanager.services.AccountService;
import com.softib.accountmanager.util.Utility;

@RestController
public class AccountRestController implements AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	ConversionService conversionService;

	@GetMapping(value = "/getAccount/{id}")
	public Account findById(@PathVariable("id") int id) {
		return accountService.findById(id);
	}

	@PostMapping("/addAccount")
	public void save(@RequestBody Account account) {
		accountService.save(account);
	}

	@PostMapping("/updateById")
	@Override
	public void updateAccountById(@RequestBody Account account) {
		accountService.update(account);
	}

	@GetMapping(value = "/getAllAccounts")
	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria) {
		return accountService.find(pageSize, pageNumber, criteria);
	}

	@GetMapping(value = "/getAllArchive")
	public Collection<Archive> getAllArchive() {
		return accountService.findAllArchive();
	}

	@GetMapping(value = "/getArchiveByType")
	public Collection<Archive> getArchiveByType(@RequestParam String type) {
		return accountService.findArchiveByType(type);
	}

	@PostMapping("/archive/{id}")
	public void archive(@PathVariable("id") int id) {
		accountService.archive(id);
	}

	@PostMapping("/restore/{id}")
	public void restore(@PathVariable("id") int id) {
		accountService.restore(id);
	}

	public void delete(Account account) {
		accountService.delete(account);
	}

	@PostMapping("/updateAccountBalanceById")
	@Override
	public void updateAccountBalance(@RequestBody Request request) {

		if (TransactionType.WITHDRAW.equals(request.getTransactionType())) {
			accountService.retrieve(request.getSourceAccountId(), request.getAmount());
		} else if (TransactionType.DEPOSIT.equals(request.getTransactionType())) {
			accountService.deposit(request.getTargetAccountId(), request.getAmount());
		} else if (TransactionType.TRANSFERT.equals(request.getTransactionType())) {
			accountService.transfert(request.getSourceAccountId(), request.getTargetAccountId(), request.getAmount());
		}

	}

	@GetMapping(value = "/runTest")
	public Integer runTest() {
		int testTotal = 0;

		// TEST SAVE/FIND ACCOUNT TEST 1
		Account acc = Utility.createAccount(121);
		accountService.save(acc);
		Account account = accountService.findById(121);
		testTotal = account != null ? testTotal + 1 : testTotal;
		//

		// TEST FIND/UPDATE ACCOUNT TEST 2
		account = account != null ? account : Utility.createAccount(121);
		account.setUpdatorId("TEST USER");
		accountService.save(account);
		Account accountToBeTested = accountService.findById(121);
		testTotal = "TEST USER".equals(accountToBeTested.getUpdatorId()) ? testTotal + 1 : testTotal;
		//

		// TEST ARCHIVE/FIND BY ID ARCHIVE ACCOUNT TEST 3
		accountService.archive(121);
		Archive arch = accountService.findArchiveById(121);
		testTotal = arch != null ? testTotal + 1 : testTotal;
		//

		// TEST CHECK ARCHIVED ENTITY IS DELETED FROM ITS TABLE TEST 4
		Account accountToBeTested4 = accountService.findById(121);
		testTotal = accountToBeTested4 == null ? testTotal + 1 : testTotal;
		//

		// TEST CHECK RESTORE TEST 5
		accountService.restore(121);
		Account accountToBeTested5 = accountService.findById(121);
		testTotal = accountToBeTested5 != null ? testTotal + 1 : testTotal;
		//

		// TEST CHECK WITHDRAW WORK BALANCE TEST 6
		Account acc5 = Utility.createAccount(555);
		acc5.setBalance(1000F);
		accountService.save(acc5);
		accountService.retrieve(555, 1000F);
		Account accountToBeTested6 = accountService.findById(555);

		testTotal = accountToBeTested6.getBalance() == 0F ? testTotal + 1 : testTotal;
		//

		// TEST CHECK WITHDRAW OVER BALANCE TEST 7
		Account acc1 = Utility.createAccount(666);
		acc1.setBalance(1000F);
		accountService.save(acc1);
		accountService.retrieve(666, 2000F);
		Account accountToBeTested7 = accountService.findById(666);

		testTotal = accountToBeTested7.getBalance() == 1000F ? testTotal + 1 : testTotal;
		//

		// TEST CHECK DEPOSIT BALANCE TEST 8
		Account depositACC = Utility.createAccount(888);
		depositACC.setBalance(1000F);
		accountService.save(depositACC);
		accountService.deposit(888, 2000F);
		Account accountToBeTested8 = accountService.findById(888);

		testTotal = accountToBeTested8.getBalance() == 3000F ? testTotal + 1 : testTotal;
		//

		// TEST CHECK TRANSFERT TEST 9
		Account ACC566 = Utility.createAccount(566);
		Account ACC561 = Utility.createAccount(561);

		ACC566.setBalance(1000F);
		ACC561.setBalance(0F);
		accountService.save(ACC566);
		accountService.save(ACC561);

		accountService.transfert(566, 561, 1000F);

		Account accountToBeTested9 = accountService.findById(566);
		Account accountToBeTested10 = accountService.findById(561);

		testTotal = (accountToBeTested9.getBalance() == 0F && accountToBeTested10.getBalance() == 1000F) ? testTotal + 1
				: testTotal;
		//

		return testTotal;
	}

}
