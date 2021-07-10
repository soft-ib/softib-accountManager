package com.softib.accountmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softib.accountmanager.repositories.PocketCashCardRepository;

@Service
public class PocketCashServiceImpl implements PocketCashService {

	@Autowired
	private PocketCashCardRepository pocketCashRepository;

	@Override
	public boolean retrive(String card_number, String password, Float amount) {
		//ADD RETRIEVE METHOD IN THE REPO USING QUERI API
		return true;
	}

}
