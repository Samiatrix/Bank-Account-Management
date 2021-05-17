package com.unittesting.UnitTesting.service;

import com.unittesting.UnitTesting.Exception.ResourceNotFoundException;
import com.unittesting.UnitTesting.dao.AccountRepository;
import com.unittesting.UnitTesting.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public Account saveAccount(Account account){
        return repository.save(account);
    }

    public List<Account> getAccountDetails(){
        List<Account> allAccounts = null;
        try {
            allAccounts = repository.findAll();
        }
        catch(Exception e){
            throw new ResourceNotFoundException("No Data Found");
        }
        return allAccounts;
    }

    public Account getAccountDetailsById(int id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id "+id+" not found"));
    }

    public void deleteAccountById(int id){
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id "+id+" not found"));
        repository.deleteById(id);
    }

    public Account updateAccountDetails(Account account){
        Account existingUser = repository.findById(account.getAccId()).orElseThrow(() -> new ResourceNotFoundException("Account with id "+account.getAccId()+" not found"));
        existingUser.setName(account.getName());
        existingUser.setType(account.getType());
        existingUser.setBalance(account.getBalance());
        return repository.save(existingUser);
    }
}
