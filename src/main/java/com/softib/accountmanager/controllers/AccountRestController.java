package com.softib.accountmanager.controllers;

import java.util.Collection;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.Archive;
import com.softib.accountmanager.services.AccountService;

@RestController
@Path("/account")
public class AccountRestController implements AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	ConversionService conversionService;

	@GetMapping(value = "/getAccount/{id}")
	public Account findById(@PathVariable("id") int id) {
		return accountService.findById(id);
	}

	@GetMapping(value = "/getAllAccounts")
	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria) {
		return accountService.find(pageSize, pageNumber, criteria);
	}

	@GetMapping(value = "/convert")
	public Account convert() {
		Account account = accountService.findById(12);
		return conversionService.convert(account.toString(), Account.class);
	}

	@GetMapping(value = "/getAllArchive")
	public Collection<Archive> getAllArchive() {
		return accountService.findAllArchive();
	}

	@GetMapping(value = "/getArchiveByType")
	public Collection<Archive> getArchiveByType(@RequestParam String type) {
		return accountService.findArchiveByType(type);
	}

	@PostMapping("/addAccount")
	public void save(@RequestBody Account account) {
		accountService.save(account);

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

}
