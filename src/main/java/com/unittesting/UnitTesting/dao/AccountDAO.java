package com.unittesting.UnitTesting.dao;

import com.unittesting.UnitTesting.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountDAO extends JpaRepository<Account, Integer> {
    Account findByName(String name);
}

