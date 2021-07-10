//package com.softib.accountmanager.util;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//import com.softib.accountmanager.entities.Account;
//import com.softib.accountmanager.entities.CreditCard;
//
//@Component("StringToCreditCard")
//public class StringToCreditCard implements Converter<String, CreditCard> {
//
//	@Autowired
//	ConversionService conversionService;
//
//	@Override
//	public CreditCard convert(String from) {
//		String[] data = from.split(",");
//		Account acc = new Account(conversionService.convert(data[6], Integer.class));
//
//		CreditCard creditCard = new CreditCard(conversionService.convert(data[0], Integer.class),
//				conversionService.convert(data[1], String.class), conversionService.convert(data[2], String.class),
//				conversionService.convert(data[3], int.class), conversionService.convert(data[4], Date.class),
//				conversionService.convert(data[5], boolean.class), acc, conversionService.convert(data[7], Date.class),
//				conversionService.convert(data[8], String.class), conversionService.convert(data[9], Date.class),
//				conversionService.convert(data[10], String.class));
//		return creditCard;
//	}
//
//}