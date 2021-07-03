package com.softib.accountmanager.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softib.accountmanager.entities.CreditCard;
import com.softib.accountmanager.repositories.CreditCardRepository;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(CreditCardServiceImpl.class);

	@Override
	public CreditCard findById(int id) {
		logger.info("In method findByID");
		Optional<CreditCard> creditCard = creditCardRepository.findById(id);
		if (!creditCard.isPresent()) {
			logger.info("No result find for this ID : " + id);
			return null;
		}
		return creditCard.get();
	}

	@Override
	public Collection<CreditCard> find(Integer pageSize, Integer pageNumber, String criteria) {
		logger.info("In method getAllEmployes");
		return (List<CreditCard>) creditCardRepository.findAll();
	}

	@Override
	public void save(CreditCard creditCard) {

		logger.info("In method addOrUpdateEmploye");
		int accID = -1;
		try {
			creditCardRepository.save(creditCard);
			logger.info("Out of method addOrUpdateEmploye");
		} catch (IllegalArgumentException e) {
			logger.error("Out of method addOrUpdateEmploye with Errors : " + e);
		} finally {
			logger.info("Out of method addOrUpdateEmploye");

		}
	}

	@Override
	public void delete(CreditCard account) {
	}

}
