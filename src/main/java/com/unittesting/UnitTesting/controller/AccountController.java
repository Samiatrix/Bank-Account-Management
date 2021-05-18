package com.unittesting.UnitTesting.controller;

import com.unittesting.UnitTesting.dao.AccountRepository;
import com.unittesting.UnitTesting.model.Account;
import com.unittesting.UnitTesting.model.Response;
import com.unittesting.UnitTesting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class AccountController {
    @Autowired
    private AccountService service;
    private AccountRepository repo;

    @PostMapping("/createAccount")
    public Response insertAccount(@RequestBody Account account){
        service.saveAccount(account);
        return new Response(account.getAccId()+" inserted", Boolean.TRUE);
    }

    @GetMapping("/getAccountDetails")
    public List<Account> findAllAccounts(){
        return service.getAccountDetails();
    }

    @GetMapping("/getAccountDetailsById/{id}")
    public Account findAccountById(@PathVariable int id){
        return service.getAccountDetailsById(id);
    }

    @PutMapping("/updateAccount")
    public Account updateAccountData(@RequestBody Account account){
        return service.updateAccountDetails(account);
    }

    @DeleteMapping("/deleteAccountById/{id}")
    public void deleteAccount(@PathVariable int id){
        service.deleteAccountById(id);
    }
}
