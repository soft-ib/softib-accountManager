package com.softib.accountmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softib.accountmanager.entities.PocketCashCard;

@Repository
public interface PocketCashCardRepository extends JpaRepository<PocketCashCard, Integer> {

}
