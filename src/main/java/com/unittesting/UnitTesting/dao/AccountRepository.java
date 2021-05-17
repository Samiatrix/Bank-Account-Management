package com.unittesting.UnitTesting.dao;

import com.unittesting.UnitTesting.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    
}

