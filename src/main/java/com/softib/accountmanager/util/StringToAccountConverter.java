package com.softib.accountmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.CreditCard;
import com.softib.accountmanager.entities.PocketCashCard;

@Component("StringToAccountConverter")
public class StringToAccountConverter implements Converter<String, Account> {

	@Autowired
	ConversionService conversionService;

//acc_identifier + "," + balance + "," + accountNumber + "," + "*CreditCard*" + ceditCard + ","
//+ "*PocketCashCard*" + pocketCashCard + "," + creation_date_ + "," + creator_user_id_ + ","
//+ update_date_ + "," + updator_user_id_;

	// public Account(Integer acc_identifier, Float balance, Integer accountNumber,
	// String iban, Set<CreditCard> ceditCard,
	// Set<PocketCashCard> pocketCashCard, Date creation_date_,
	// String creator_user_id_, Date update_date_,

	// String updator_user_id_) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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