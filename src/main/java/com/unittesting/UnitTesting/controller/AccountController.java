package com.unittesting.UnitTesting.controller;

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
    @GetMapping("/getDataByName/{name}")
    public List<Account> findAccountByName(@PathVariable String name){
        return service.getDataByName(name);
    }

    @PutMapping("/update")
    public Account updateAccountData(@RequestBody Account account){
        return service.updateData(account);
    }

    @DeleteMapping("/deleteData/{id}")
    public String deleteAccount(@PathVariable int id){
        return service.deleteData(id);
    }
}
