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
        Account newAccount = repository.save(account);
        System.out.println(newAccount);
        return newAccount;
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
        Account account = null;
        account = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id "+id+" not found"));
        return account;
    }

    public void deleteAccountById(int id){
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id "+id+" not found"));
        repository.deleteById(id);
    }

    public Account updateAccountDetails(Account account){
        Account existingAccount = repository.findById(account.getAccId()).orElseThrow(() -> new ResourceNotFoundException("Account with id "+account.getAccId()+" not found"));
        existingAccount.setName(account.getName());
        existingAccount.setType(account.getType());
        existingAccount.setBalance(account.getBalance());
        Account updatedAccount = repository.save(existingAccount);
        return updatedAccount;
    }
}
