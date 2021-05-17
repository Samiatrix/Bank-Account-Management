package com.unittesting.UnitTesting.controller;

import com.unittesting.UnitTesting.dao.AccountRepository;
import com.unittesting.UnitTesting.model.Account;
import com.unittesting.UnitTesting.model.Response;
import com.unittesting.UnitTesting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
public class AccountController {
    @Autowired
    private AccountService service;
    private AccountRepository repo;

    @PostMapping("/insertData")
    public Response insertAccount(@RequestBody Account account){
        service.saveData(account);
        return new Response(account.getAccId()+" inserted", Boolean.TRUE);
    }

    @GetMapping("/getData")
    public List<Account> findAllAccounts(){
        return service.getData();
    }

    @GetMapping("/getDataById/{id}")
    public Account findAccountById(@PathVariable int id){
        return service.getDataById(id);
    }

    @PutMapping("/update/{id}")
    public Account updateAccountData(@RequestBody Account account){
        return service.updateData(account);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteAccount(@PathVariable int id){
        service.deleteAccountById(id);
    }
}
