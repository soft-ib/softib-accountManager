package com.softib.accountmanager.controllers;

import java.util.Collection;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softib.accountmanager.entities.CreditCard;
import com.softib.accountmanager.services.CreditCardService;

@RestController
@Path("/credit")
public class CreditCardRestController implements CreditCardController {
	@Autowired
	CreditCardService creditCardService;

	@GetMapping(value = "/getCreditCardById")
	@Override
	public CreditCard findById(@PathVariable("id") int id) {
		return creditCardService.findById(id);
	}

	@GetMapping(value = "/getAllCreditCards")
	@Override
	public Collection<CreditCard> find(Integer pageSize, Integer pageNumber, String criteria) {
		return creditCardService.find(pageSize, pageNumber, criteria);
	}

	@PostMapping("/addCard")
	@Override
	public void save(@RequestBody CreditCard creditCard) {
		creditCardService.save(creditCard);
	}

	@Override
	public void delete(CreditCard account) {
		// TODO Auto-generated method stub

	}

}
