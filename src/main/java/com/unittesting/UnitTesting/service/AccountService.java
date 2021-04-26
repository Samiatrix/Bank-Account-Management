package com.unittesting.UnitTesting.service;

import com.unittesting.UnitTesting.dao.AccountDAO;
import com.unittesting.UnitTesting.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountDAO repository;

    public Account saveData(Account account){
        return repository.save(account);
    }

    public List<Account> getData(){
        return repository.findAll();
    }

    public Account getDataById(int id){
        return repository.findById(id).orElse(null);
    }

    public Account getDataByName(String name){
        return repository.findByName(name);
    }

    public String deleteData(int id){
        repository.deleteById(id);
        return "Data deleted with the id: "+id;
    }

    public Account updateData(Account account){
        Account existingData = repository.findById(account.getAccId()).orElse(null);
//        System.out.println(existingData);
        existingData.setName(account.getName());
        existingData.setType(account.getType());
        existingData.setBalance(account.getBalance());
        return repository.save(existingData);
    }
}
