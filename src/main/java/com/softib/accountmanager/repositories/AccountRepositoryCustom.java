package com.softib.accountmanager.repositories;

public interface AccountRepositoryCustom {

	public int transfert(Integer sourceId, Integer targetId, Float amount);

	public int deposit(Integer targetId, Float amount);

	public int withdraw(Integer sourceID, Float amount);

	public int transfertFromAccountToCard(Integer sourceId, Integer targetId, Float amount, String cardType);

}
