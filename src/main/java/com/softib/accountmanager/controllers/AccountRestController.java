package com.softib.accountmanager.controllers;

import java.util.Collection;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.services.AccountService;

@RestController
@Path("/account")
public class AccountRestController implements AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping(value = "/getAccount/{id}")
	public Account findById(@PathVariable("id") int id) {
		return accountService.findById(id);
	}

	@GetMapping(value = "/getAllAccounts")
	public Collection<Account> find(Integer pageSize, Integer pageNumber, String criteria) {
		return accountService.find(pageSize, pageNumber, criteria);
	}

	@PostMapping("/addAccount")
	public void save(@RequestBody Account account) {
		accountService.save(account);

	}

	public void delete(Account account) {
		// TODO Auto-generated method stub

	}

}
