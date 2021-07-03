package com.softib.accountmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softib.accountmanager.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
