package com.softib.accountmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.CreditCard;
import com.softib.accountmanager.entities.PocketCashCard;
import com.softib.accountmanager.exception.ProcessException;
import com.softib.accountmanager.services.CreditCardServiceImpl;

@Component("StringToAccountConverter")
public class StringToAccountConverter implements Converter<String, Account> {
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(CreditCardServiceImpl.class);

	@Autowired
	ConversionService conversionService;

	@Override
	public Account convert(String from) {
		String localFrom = from;
		String one = localFrom.substring(localFrom.indexOf("-CreditCard-[;"), localFrom.indexOf("]") + 1) + ",";
		localFrom = localFrom.replace(one, "");
		String two = localFrom.substring(localFrom.indexOf("*PocketCashCard *["), localFrom.indexOf("]") + 1) + ",";
		localFrom = localFrom.replace(two, "");

		String[] data = localFrom.split(",");
		Set<CreditCard> ceditCard = new HashSet<>();
		Set<PocketCashCard> pocketCashCard = new HashSet<>();

		Account acc = null;
		try {
			acc = new Account(conversionService.convert(data[0], Integer.class),
					conversionService.convert(data[1], Float.class), conversionService.convert(data[2], Integer.class),
					conversionService.convert(data[3], String.class), ceditCard, pocketCashCard,
					new SimpleDateFormat("dd-MM-yyyy").parse(data[4]), conversionService.convert(data[5], String.class),
					new SimpleDateFormat("dd-MM-yyyy").parse(data[6]),
					conversionService.convert(data[7], String.class));
			initCompositions(from, acc);

		} catch (ParseException e) {
			throw new ProcessException("while converting string to Account:", e);
		}
		return acc;
	}

	public CreditCard convertCreditCard(String from) {
		String[] data = from.split(",");
		Account acc = new Account(conversionService.convert(data[6], Integer.class));

		CreditCard creditCard = null;
		try {
			creditCard = new CreditCard(conversionService.convert(data[0], Integer.class),
					conversionService.convert(data[1], String.class), conversionService.convert(data[2], String.class),
					conversionService.convert(data[3], int.class), new SimpleDateFormat("dd-MM-yyyy").parse(data[4]),
					conversionService.convert(data[5], boolean.class), acc,
					new SimpleDateFormat("dd-MM-yyyy").parse(data[7]), conversionService.convert(data[8], String.class),
					new SimpleDateFormat("dd-MM-yyyy").parse(data[9]),
					conversionService.convert(data[10], String.class));
		} catch (ParseException e) {
			throw new ProcessException("while converting string to Credit Card:", e);
		}
		return creditCard;
	}

	public void initCompositions(String from, Account acc) {
		Set<PocketCashCard> pocketCashCard = new HashSet<>();
		from = from.substring(from.indexOf("-CreditCard-[;"));
		from = from.substring(0, from.lastIndexOf("]") + 1);
		from = from.replace("-CreditCard-[;", "");
		from = from.replace("]", "");
		from = from.replace("*PocketCashCard *[", "");
		from = from.replace("]", "");

		String[] creditCardsStringArray = from.split(";");
		for (String creditCardAsString : creditCardsStringArray) {
			if (creditCardAsString.startsWith(",")) {
				creditCardAsString = creditCardAsString.substring(0);
			}
			CreditCard creditCard = convertCreditCard(creditCardAsString);
			acc.addCeditCard(creditCard);
		}
	}
}