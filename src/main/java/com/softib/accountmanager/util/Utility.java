package com.softib.accountmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.CreditCard;

public class Utility {

	public static String generateActivationKey() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}

	public static Account createAccount(Integer id) {
//		Account acc = new Account();
//		acc.setAcc_identifier(id);
//		acc.setAccountNumber(Math.getExponent(Math.random()));
//		acc.setBalance(1000F);
//		acc.setIban(Math.random() + "");
//
//		CreditCard creditcard = new CreditCard();
//		creditcard.setActive(true);
//		creditcard.setCardId(Math.getExponent(Math.random()));
//		creditcard.setCardNumber(Math.getExponent(Math.random()) + "");
//		creditcard.setCardType("tyype 1");
//		creditcard.setCvv(Math.getExponent(Math.random()));
//		try {
//			creditcard.setExpDate(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-2021"));
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		CreditCard creditcard2 = new CreditCard();
//		creditcard2.setActive(true);
//		creditcard2.setCardId(Math.getExponent(Math.random()));
//		creditcard2.setCardNumber(Math.getExponent(Math.random()) + "");
//		creditcard2.setCardType("tyype 1");
//		creditcard2.setCvv(Math.getExponent(Math.random()));
//		try {
//			creditcard2.setExpDate(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-2021"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//
//		acc.addCeditCard(creditcard);
//		acc.addCeditCard(creditcard2);
//
//		return acc;
		return null;
	}

}
