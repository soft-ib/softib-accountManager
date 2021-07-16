package com.softib.accountmanager.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.PocketCashCard;

/**
 * @author jsaoudi
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int transfert(Integer sourceId, Integer targetId, Float amount) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		int rowCountTarget = 0;
		int rowCountSource = 0;

		CriteriaUpdate updateSource = criteriaBuilder.createCriteriaUpdate(Account.class);
		Root acountSource = updateSource.from(Account.class);

		updateSource.set(acountSource.get("balance"), criteriaBuilder.diff(acountSource.get("balance"), amount));
		updateSource.where(criteriaBuilder.and(criteriaBuilder.equal(acountSource.get("accIdentifier"), sourceId),
				criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.diff(acountSource.get("balance"), amount), 0)));
		Query queryForSource = entityManager.createQuery(updateSource);
		rowCountSource = queryForSource.executeUpdate();

		if (rowCountSource > 0) {
			CriteriaUpdate updateupdateTarget = criteriaBuilder.createCriteriaUpdate(Account.class);
			Root acountTarget = updateupdateTarget.from(Account.class);
			updateupdateTarget.set(acountTarget.get("balance"),
					criteriaBuilder.sum(acountTarget.get("balance"), amount));
			updateupdateTarget.where(criteriaBuilder.equal(acountTarget.get("accIdentifier"), targetId));
			Query queryTarget = entityManager.createQuery(updateupdateTarget);
			rowCountTarget = queryTarget.executeUpdate();
		}
		return rowCountSource + rowCountTarget;

	}

	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int transfertFromAccountToCard(Integer sourceId, Integer targetId, Float amount, String cardType) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		int rowCountTarget = 0;
		int rowCountSource = 0;

		CriteriaUpdate updateSource = criteriaBuilder.createCriteriaUpdate(Account.class);
		Root acountSource = updateSource.from(Account.class);

		updateSource.set(acountSource.get("balance"), criteriaBuilder.diff(acountSource.get("balance"), amount));
		updateSource.where(criteriaBuilder.and(criteriaBuilder.equal(acountSource.get("accIdentifier"), sourceId),
				criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.diff(acountSource.get("balance"), amount), 0)));
		Query queryForSource = entityManager.createQuery(updateSource);
		rowCountSource = queryForSource.executeUpdate();

		if (rowCountSource > 0) {
			CriteriaUpdate updateupdateTarget = criteriaBuilder.createCriteriaUpdate(PocketCashCard.class);
			Root acountTarget = updateupdateTarget.from(PocketCashCard.class);
			updateupdateTarget.set(acountTarget.get("balance"),
					criteriaBuilder.sum(acountTarget.get("balance"), amount));
			updateupdateTarget.where(criteriaBuilder.equal(acountTarget.get("accIdentifier"), targetId));
			Query queryTarget = entityManager.createQuery(updateupdateTarget);
			rowCountTarget = queryTarget.executeUpdate();
		}
		return rowCountSource + rowCountTarget;

	}

	@Transactional
	@Override
	public int deposit(Integer targetId, Float amount) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		@SuppressWarnings("rawtypes")
		CriteriaUpdate updateSource = criteriaBuilder.createCriteriaUpdate(Account.class);
		Root acountSource = updateSource.from(Account.class);
		updateSource.set(acountSource.get("balance"), criteriaBuilder.sum(acountSource.get("balance"), amount));
		updateSource.where(criteriaBuilder.equal(acountSource.get("accIdentifier"), targetId));
		Query queryForSource = entityManager.createQuery(updateSource);
		return queryForSource.executeUpdate();
	}

	@Transactional
	@Override
	public int withdraw(Integer sourceID, Float amount) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate updateSource = criteriaBuilder.createCriteriaUpdate(Account.class);
		Root acountSource = updateSource.from(Account.class);

		updateSource.set(acountSource.get("balance"), criteriaBuilder.diff(acountSource.get("balance"), amount));
		updateSource.where(criteriaBuilder.and(criteriaBuilder.equal(acountSource.get("accIdentifier"), sourceID),
				criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.diff(acountSource.get("balance"), amount), 0)));
		Query queryForSource = entityManager.createQuery(updateSource);
		return queryForSource.executeUpdate();
	}

}
